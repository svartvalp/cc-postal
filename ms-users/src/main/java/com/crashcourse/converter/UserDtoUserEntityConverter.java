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
        if (source.getAddress() != null) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setId(source.getAddress().getId());
            addressEntity.setLongitude(source.getAddress().getLongitude());
            addressEntity.setLatitude(source.getAddress().getLatitude());
            addressEntity.setAddress(source.getAddress().getAddress());
            userEntity.setAddress(addressEntity);
        }
        return userEntity;
    }
}

