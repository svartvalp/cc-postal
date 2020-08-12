package com.crashcourse.msdeparture.scheduler;

import com.crashcourse.msdeparture.dto.DepartureDto;
import com.crashcourse.msdeparture.entity.Departure;
import com.crashcourse.msdeparture.repository.DepartureRepository;
import com.crashcourse.msdeparture.service.DepartureMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DepartureScheduler {
    private final DepartureRepository departureRepository;
    private final DepartureMessageSender departureMessageSender;
    private final ModelMapper modelMapper;

    @Value("${kafka.topic.departure.compute}")
    private String departureRequest;

    @Scheduled(cron = "${schedule.departure.cron}")
    public void setAddresseeAndDate() {
        List<Departure> departureList = departureRepository.findAllByArrivingDate(null);
        log.debug("found {} departures without arriving date", departureList.size());
        try {
            departureList.forEach(departure -> {
                DepartureDto departureDto = modelMapper.map(departure, DepartureDto.class);
                departureMessageSender.sendToTopic(departureDto, departureRequest);
            });
        } catch(Exception e) {
            log.error("Can not send schedule request: {}", e.getMessage());
        }

    }
}
