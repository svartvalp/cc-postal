package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.DepartureDto;
import com.crashcourse.ms_navigation.dto.UserListDto;
import com.crashcourse.ms_navigation.entity.Departure;

public interface DepartureService {
    void countArrivingDateAndSend(DepartureDto departureDto);

    Departure saveDeparture(DepartureDto departureDto);

    void findNearestUserFromUsersAndSend(UserListDto userListDto);
}
