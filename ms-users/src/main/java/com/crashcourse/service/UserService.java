package com.crashcourse.service;

import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadConvertException;
import com.crashcourse.exception.NoSuchEntityException;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto getUserById(Integer id) throws NoSuchEntityException;

    UserDto deleteUserById(Integer id) throws Exception;

    UserDto registerUser(UserDto userDto) throws AlreadyExistException, BadConvertException;

    ResponseEntity<?> login(UserDto userDto);

    UserDto updateUser(UserDto userDto) throws Exception;
}
