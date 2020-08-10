package com.crashcourse.service;

import com.crashcourse.dto.UserListDto;

public interface UserKafkaService {
    void sendUserData(UserListDto dto, String topic);
}
