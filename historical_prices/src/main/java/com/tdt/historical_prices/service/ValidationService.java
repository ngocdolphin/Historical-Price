package com.tdt.historical_prices.service;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.entity.Role;
import com.tdt.historical_prices.form.RequestBodyUser;
import com.tdt.historical_prices.form.UserCreateRequest;
import com.tdt.historical_prices.form.UserUpdateRequest;
import com.tdt.historical_prices.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ValidationService {
    public List<MetaDTO> validationInputRequestBodyUser(RequestBodyUser requestBodyUser) {
        List<MetaDTO> metaList = new ArrayList<>();

        if (StringUtils.isBlank(requestBodyUser.getUserName())) {
            log.warn("Param username is null or empty");
            metaList.add(new MetaDTO(CommonConstant.MetaData.USERNAME_MISSING));
        }

        if (StringUtils.isBlank(requestBodyUser.getPassword()) || !PasswordUtils.isPassword(requestBodyUser.getPassword())) {
            log.warn("Param password is invalid");
            metaList.add(new MetaDTO(CommonConstant.MetaData.PASSWORD_INVALID));
        }
        return metaList;
    }

    public List<MetaDTO> validationInputUserCreateRequest(UserCreateRequest request) {
        List<MetaDTO> metaList = new ArrayList<>();

        if (StringUtils.isBlank(request.getUserName())) {
            log.warn("Param username is null or empty");
            metaList.add(new MetaDTO(CommonConstant.MetaData.USERNAME_MISSING));
        }

        if (StringUtils.isBlank(request.getPassword()) || !PasswordUtils.isPassword(request.getPassword())) {
            log.warn("Param password is invalid");
            metaList.add(new MetaDTO(CommonConstant.MetaData.PASSWORD_INVALID));
        }

        if (!Arrays.asList(Role.ADMIN.getValue(), Role.EMPLOYEE.getValue(), Role.MANAGER.getValue())
                .contains(request.getRole())) {
            log.warn("Param role is missing");
            metaList.add(new MetaDTO(CommonConstant.MetaData.ROLE_MISSING));
        }
        return metaList;
    }

    public List<MetaDTO> validationInputUserUpdateRequest(UserUpdateRequest request) {
        List<MetaDTO> metaList = new ArrayList<>();

        if (StringUtils.isBlank(request.getUserName())) {
            log.warn("Param username is null or empty");
            metaList.add(new MetaDTO(CommonConstant.MetaData.USERNAME_MISSING));
        }

        if (StringUtils.isBlank(request.getPassword()) || !PasswordUtils.isPassword(request.getPassword())) {
            log.warn("Param password is invalid");
            metaList.add(new MetaDTO(CommonConstant.MetaData.PASSWORD_INVALID));
        }

        if (!Arrays.asList(Role.ADMIN.getValue(), Role.EMPLOYEE.getValue(), Role.MANAGER.getValue(), StringUtils.EMPTY)
                .contains(request.getRole())) {
            log.warn("Param role is missing");
            metaList.add(new MetaDTO(CommonConstant.MetaData.ROLE_MISSING));
        }
        return metaList;
    }

    public List<MetaDTO> validationInputUserUpdateByUserNameRequest(String userName, UserUpdateRequest request) {
        List<MetaDTO> metaList = validationInputUserName(userName);

        if (StringUtils.isBlank(request.getUserName())) {
            log.warn("Param username is null or empty");
            metaList.add(new MetaDTO(CommonConstant.MetaData.USERNAME_MISSING));
        }

        if (StringUtils.isBlank(request.getPassword()) || !PasswordUtils.isPassword(request.getPassword())) {
            log.warn("Param password is invalid");
            metaList.add(new MetaDTO(CommonConstant.MetaData.PASSWORD_INVALID));
        }

        if (!Arrays.asList(Role.ADMIN.getValue(), Role.EMPLOYEE.getValue(), Role.MANAGER.getValue(), StringUtils.EMPTY)
                .contains(request.getRole())) {
            log.warn("Param role is missing");
            metaList.add(new MetaDTO(CommonConstant.MetaData.ROLE_MISSING));
        }
        return metaList;
    }

    public List<MetaDTO> validationInputRole(String role) {
        List<MetaDTO> metaList = new ArrayList<>();

        if (!Arrays.asList(Role.ADMIN.getValue(), Role.EMPLOYEE.getValue(), Role.MANAGER.getValue()).contains(role)) {
            log.warn("Param role is missing");
            metaList.add(new MetaDTO(CommonConstant.MetaData.ROLE_MISSING));
        }
        return metaList;
    }

    public List<MetaDTO> validationInputUserName(String userName) {
        List<MetaDTO> metaList = new ArrayList<>();

        if (userName.equals(":user_name")) {
            log.warn("Param username is null or empty");
            metaList.add(new MetaDTO(CommonConstant.MetaData.USERNAME_MISSING));
        }
        return metaList;
    }
}
