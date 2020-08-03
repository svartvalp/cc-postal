package com.crashcourse.service;

import com.crashcourse.db.entity.UserEntity;
import com.crashcourse.db.repository.UserRepository;
import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadConvertException;
import com.crashcourse.exception.NoSuchEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ConversionService conversionService;
    private final UserRepository userRepository;

    @Transactional
    public UserDto getUserById(Integer id) throws NoSuchEntityException {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.orElseThrow(NoSuchEntityException::new);
        return conversionService.convert(userEntity.get(), UserDto.class);
    }

    @Transactional
    public UserDto deleteUserById(Integer id) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.orElseThrow(NoSuchEntityException::new);
        userRepository.deleteById(id);
        return conversionService.convert(userEntity, UserDto.class);
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) throws AlreadyExistException, BadConvertException {
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            throw new AlreadyExistException();
        }
        UserEntity userEntity = conversionService.convert(userDto, UserEntity.class);
        if (userEntity == null) {
            throw new BadConvertException();
        }
        userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
        return conversionService.convert(userRepository.save(userEntity), UserDto.class);
    }

    @Transactional
    public ResponseEntity<?> login(UserDto userDto) {
        Optional<UserEntity> userEntity = userRepository.findByLogin(userDto.getLogin());
        if (userEntity.isPresent() && BCrypt.checkpw(userDto.getPassword(), userEntity.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) throws NoSuchEntityException, AlreadyExistException, BadConvertException {
        userRepository.findById(userDto.getId()).orElseThrow(NoSuchEntityException::new);
        Optional<UserEntity> userEntity = userRepository.findByLogin(userDto.getLogin());
        if (userEntity.isPresent() && !userEntity.get().getId().equals(userDto.getId())) {
            throw new AlreadyExistException();
        } else {
            UserEntity toSafe = conversionService.convert(userDto, UserEntity.class);
            if (toSafe == null) {
                throw new BadConvertException();
            }
            return conversionService.convert(userRepository.save(toSafe), UserDto.class);
        }
    }
}
