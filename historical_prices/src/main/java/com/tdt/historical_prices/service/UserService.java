package com.tdt.historical_prices.service;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.entity.Role;
import com.tdt.historical_prices.entity.User;
import com.tdt.historical_prices.form.UserCreateRequest;
import com.tdt.historical_prices.form.UserUpdateRequest;
import com.tdt.historical_prices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public ResponseMetaData createUser(UserCreateRequest form) {
        User user = new User(form);
        user.setPassword(encryptPassword(form.getPassword()));
        repository.save(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData findAllUser(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), users);
    }

    public ResponseMetaData getUserById(int id) {
        User user = repository.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData getUserByUserName(String userName) {
        User user = repository.findByUserName(userName);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData getUserByRole(String role) {
        User user = repository.findByRole(Role.valueOf(role));
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData updateUserById(int id, UserUpdateRequest form) {
        User user = repository.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }

        user.setUserName(form.getUserName());
        user.setPassword(encryptPassword(form.getPassword()));
        repository.save(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData updateUserByUserName(String userName, UserUpdateRequest form) {
        User user = repository.findByUserName(userName);
        if (user == null) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }

        if (userName != form.getUserName()) {
            user.setUserName(form.getUserName());
        } else if (form.getRole() != null) {
            user.setRole(Role.valueOf(form.getRole()));
        }

        user.setPassword(encryptPassword(form.getPassword()));
        repository.save(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), user);
    }

    public ResponseMetaData deleteUserById(int id) {
        User user = repository.findById(id);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        repository.delete(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData deleteUserByUserName(String userName) {
        User user = repository.findByUserName(userName);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        repository.delete(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public ResponseMetaData deleteAllUser() {
        List<User> user = repository.findAll();
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), null);
        }
        repository.deleteAll(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), null);
    }

    public String encryptPassword(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }
}
