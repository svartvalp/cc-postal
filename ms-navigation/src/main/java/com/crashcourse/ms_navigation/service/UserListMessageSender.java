package com.crashcourse.ms_navigation.service;

import com.crashcourse.ms_navigation.dto.UserListDto;

public interface UserListMessageSender {
    void sendToTopic(UserListDto userListDto, String topic);
}
