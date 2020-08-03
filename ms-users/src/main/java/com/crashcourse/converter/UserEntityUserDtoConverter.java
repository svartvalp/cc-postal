package com.crashcourse.converter;

import com.crashcourse.db.entity.UserEntity;
import com.crashcourse.dto.UserDto;
import org.springframework.core.convert.converter.Converter;

public class UserEntityUserDtoConverter implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(UserEntity source) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(source.getFirstName());
        userDto.setLastName(source.getLastName());
        userDto.setMiddleName(source.getMiddleName());
        userDto.setLogin(source.getLogin());
        userDto.setPassportNumber(source.getPassportNumber());
        userDto.setPassword(source.getPassword());
        userDto.setId(source.getId());
        return userDto;
    }
}
