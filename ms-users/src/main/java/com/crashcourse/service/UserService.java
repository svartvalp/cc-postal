package com.crashcourse.service;

import com.crashcourse.dto.LoginDto;
import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.NoSuchEntityException;

public interface UserService {
    UserDto getUserById(Integer id) throws NoSuchEntityException;

    UserDto deleteUserById(Integer id) throws Exception;

    UserDto registerUser(UserDto userDto) throws AlreadyExistException;

    LoginDto login(UserDto userDto);

    UserDto updateUser(UserDto userDto) throws Exception;
}
