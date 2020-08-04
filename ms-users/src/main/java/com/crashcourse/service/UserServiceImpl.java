package com.crashcourse.service;

import com.crashcourse.db.entity.UserEntity;
import com.crashcourse.db.repository.UserRepository;
import com.crashcourse.dto.UserDto;
import com.crashcourse.exception.AlreadyExistException;
import com.crashcourse.exception.BadConvertException;
import com.crashcourse.exception.BadRequestException;
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
    private final MessageService messageService;

    @Transactional
    public UserDto getUserById(Integer id) throws NoSuchEntityException {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.orElseThrow(() -> new NoSuchEntityException(messageService.getMessage("no.such.entity.msg")));
        return conversionService.convert(userEntity.get(), UserDto.class);
    }

    @Transactional
    public UserDto deleteUserById(Integer id) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        userEntity.orElseThrow(() -> new NoSuchEntityException(messageService.getMessage("no.such.entity.msg")));
        userRepository.deleteById(id);
        return conversionService.convert(userEntity, UserDto.class);
    }

    @Transactional
    public UserDto registerUser(UserDto userDto) throws AlreadyExistException, BadConvertException, BadRequestException {
        if (userDto == null) {
            throw new BadRequestException(messageService.getMessage("bad.request.msg"));
        }
        if (userRepository.findByLogin(userDto.getLogin()).isPresent()) {
            throw new AlreadyExistException(messageService.getMessage("already.exists.msg"));
        }
        UserEntity userEntity = conversionService.convert(userDto, UserEntity.class);
        if (userEntity == null) {
            throw new BadConvertException(messageService.getMessage("bad.convert.msg"));
        }
        userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
        return conversionService.convert(userRepository.save(userEntity), UserDto.class);
    }

    @Transactional
    public ResponseEntity<?> authorization(UserDto userDto) {
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<UserEntity> userEntity = userRepository.findByLogin(userDto.getLogin());
        if (userEntity.isPresent() && BCrypt.checkpw(userDto.getPassword(), userEntity.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) throws NoSuchEntityException, AlreadyExistException, BadConvertException, BadRequestException {
        if (userDto == null) {
            throw new BadRequestException(messageService.getMessage("bad.request.msg"));
        }
        userRepository.findById(userDto.getId())
                .orElseThrow(() -> new NoSuchEntityException(messageService.getMessage("no.such.entity.msg")));
        Optional<UserEntity> userEntity = userRepository.findByLogin(userDto.getLogin());
        if (userEntity.isPresent() && !userEntity.get().getId().equals(userDto.getId())) {
            throw new AlreadyExistException(messageService.getMessage("already.exists.msg"));
        } else {
            UserEntity toSafe = conversionService.convert(userDto, UserEntity.class);
            if (toSafe == null) {
                throw new BadConvertException(messageService.getMessage("bad.convert.msg"));
            }
            return conversionService.convert(userRepository.save(toSafe), UserDto.class);
        }
    }
}
