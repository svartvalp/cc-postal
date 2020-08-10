package com.crashcourse.msdeparture.service;

import com.crashcourse.msdeparture.dto.DepartureDto;

public interface DepartureMessageSender {
    void sendToTopic(DepartureDto departureDto, String topic);
}
