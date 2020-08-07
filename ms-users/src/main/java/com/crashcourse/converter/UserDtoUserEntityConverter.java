package com.crashcourse.converter;

import com.crashcourse.db.entity.AddressEntity;
import com.crashcourse.db.entity.UserEntity;
import com.crashcourse.dto.UserDto;
import org.springframework.core.convert.converter.Converter;

public class UserDtoUserEntityConverter implements Converter<UserDto, UserEntity> {
    @Override
    public UserEntity convert(UserDto source) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(source.getFirstName());
        userEntity.setLastName(source.getLastName());
        userEntity.setMiddleName(source.getMiddleName());
        userEntity.setLogin(source.getLogin());
        userEntity.setPassportNumber(source.getPassportNumber());
        userEntity.setPassword(source.getPassword());
        userEntity.setId(source.getId());
        userEntity.setAddress(new AddressEntity());
        if (source.getAddress() != null) {
            userEntity.getAddress().setAddress(source.getAddress().getAddress());
            userEntity.getAddress().setId(source.getAddress().getId());
            userEntity.getAddress().setLatitude(source.getAddress().getLatitude());
            userEntity.getAddress().setLongitude(source.getAddress().getLongitude());
        }
        return userEntity;
    }
}

