package com.crashcourse.controller;

import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadConvertException;
import com.crashcourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    private UserDto getUserById(@PathVariable Integer id) throws Exception {
        return userService.getUserById(id);
    }

    @DeleteMapping("/user/{id}")
    private UserDto deleteUser(@PathVariable Integer id) throws Exception {
        return userService.deleteUserById(id);
    }

    @PostMapping("/register")
    private UserDto registerUser(@RequestBody UserDto userDto) throws AlreadyExistException, BadConvertException {
        return userService.registerUser(userDto);
    }

    @PutMapping("/user")
    private UserDto updateUser(@RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userDto);
    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }
}
