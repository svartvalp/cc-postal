package com.crashcourse.service;

import com.crashcourse.db.entity.UserEntity;
import com.crashcourse.db.repository.AddressRepository;
import com.crashcourse.db.repository.UserRepository;
import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadRequestException;
import com.crashcourse.exception.NoSuchEntityException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private ConversionService conversionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageService messageService;
    @Mock
    private AddressRepository addressRepository;


    @org.junit.jupiter.api.Test
    void getUserById() throws NoSuchEntityException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setFirstName("mikhail");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));
        Mockito.when(conversionService.convert(userEntity, UserDto.class)).thenReturn(userDto);
        Mockito.when(messageService.getMessage(Mockito.anyString())).thenReturn("message");
        Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(conversionService, userRepository, messageService, addressRepository);
        assertThrows(NoSuchEntityException.class, () -> userService.getUserById(2));
        assertEquals("mikhail", userService.getUserById(1).getFirstName());
    }

    @org.junit.jupiter.api.Test
    void registerUser() {
        UserDto dto = new UserDto();
        Mockito.when(userRepository.findByLogin("login")).thenReturn(Optional.of(new UserEntity()));

        UserServiceImpl userService = new UserServiceImpl(conversionService, userRepository, messageService, addressRepository);

        assertThrows(BadRequestException.class, () -> userService.registerUser(null));
        assertThrows(BadRequestException.class, () -> userService.registerUser(dto));
        dto.setLogin("login");
        dto.setPassword("pass");
        assertThrows(AlreadyExistException.class, () -> userService.registerUser(dto));
    }

    @org.junit.jupiter.api.Test
    void updateUser() {
        UserService userService = new UserServiceImpl(conversionService, userRepository, messageService, addressRepository);
        UserDto dto = new UserDto();
        assertThrows(BadRequestException.class, () -> userService.updateUser(null));
        assertThrows(NoSuchEntityException.class, () -> userService.updateUser(dto));
    }

    @org.junit.jupiter.api.Test
    void getCurrentUser() {
        UserService userService = new UserServiceImpl(conversionService, userRepository, messageService, addressRepository);
        assertThrows(NoSuchEntityException.class, () -> userService.getCurrentUser(null));
    }

    @org.junit.jupiter.api.Test
    void getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        users.add(new UserEntity());
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(conversionService.convert(Mockito.any(UserEntity.class), Mockito.eq(UserDto.class))).thenReturn(new UserDto());
        UserService userService = new UserServiceImpl(conversionService, userRepository, messageService, addressRepository);
        assertEquals(2, userService.getAllUsers().size());
    }
}