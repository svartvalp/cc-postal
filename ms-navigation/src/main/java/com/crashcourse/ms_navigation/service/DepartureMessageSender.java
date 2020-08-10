package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.DepartureDto;

public interface DepartureMessageSender {
    void sendToTopic(DepartureDto departureDto, String topic);
}
