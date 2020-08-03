package com.crashcourse.controller;

import com.crashcourse.dto.LoginDto;
import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    private UserDto getUserById(@PathVariable Integer id) throws Exception {
        return this.userService.getUserById(id);
    }

    @DeleteMapping("/user/{id}")
    private UserDto deleteUser(@PathVariable Integer id) throws Exception {
        return this.userService.deleteUserById(id);
    }

    @PostMapping("/register")
    private UserDto registerUser(@RequestBody UserDto userDto) throws AlreadyExistException {
        return this.userService.registerUser(userDto);
    }

    @PutMapping("/user")
    private UserDto updateUser(@RequestBody UserDto userDto) throws Exception {
        return this.userService.updateUser(userDto);
    }

    @PostMapping("/login")
    private LoginDto loginUser(@RequestBody UserDto userDto) {
        return this.userService.login(userDto);
    }
}
