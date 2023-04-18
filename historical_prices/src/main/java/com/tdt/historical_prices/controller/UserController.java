package com.tdt.historical_prices.controller;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.config.NumberConstants;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.form.UserCreateRequest;
import com.tdt.historical_prices.form.UserUpdateRequest;
import com.tdt.historical_prices.service.UserService;
import com.tdt.historical_prices.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService service;

    private final ValidationService validationService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMetaData> createUser(@RequestBody UserCreateRequest request) {
        log.info("Start create new user with username = {}, role = {}",
                request.getUserName(), request.getRole());

        List<MetaDTO> metaList = validationService.validationInputUserCreateRequest(request);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.createUser(request);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping
    public ResponseEntity<ResponseMetaData> getAllUser(
            @RequestParam(value = "page_no", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_NO) Integer pageNo,
            @RequestParam(value = "page_size", defaultValue = NumberConstants.DEFAULT_PAGEABLE_PAGE_SIZE) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        ResponseMetaData responseMetaData = service.findAllUser(pageable);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMetaData> getUserById(@PathVariable(value = "id") int id) {
        log.info("Start get user by id = {}", id);
        ResponseMetaData responseMetaData = service.getUserById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/user_name/{user_name}")
    public ResponseEntity<ResponseMetaData> getUserByUserName(@PathVariable(value = "user_name") String userName) {
        log.info("Start get user by username = {}", userName);

        List<MetaDTO> metaList = validationService.validationInputUserName(userName);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.getUserByUserName(userName);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ResponseMetaData> getUserByRole(@PathVariable(value = "role") String role) {
        log.info("Start get user by role = {}", role);

        List<MetaDTO> metaList = validationService.validationInputRole(role);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.getUserByRole(role);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMetaData> updateUserById(@PathVariable(value = "id") int id,
                                                           @RequestBody UserUpdateRequest request) {
        log.info("Start update user by id = {}", id);

        List<MetaDTO> metaList = validationService.validationInputUserUpdateRequest(request);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.updateUserById(id, request);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PutMapping("/update/user_name/{user_name}")
    public ResponseEntity<ResponseMetaData> updateUserByUserName(@PathVariable(value = "user_name") String userName,
                                                                 @RequestBody UserUpdateRequest request) {
        log.info("Start update user by username = {}", userName);

        List<MetaDTO> metaList = validationService.validationInputUserUpdateByUserNameRequest(userName, request);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.updateUserByUserName(userName, request);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMetaData> deleteUserById(@PathVariable(value = "id") int id) {
        log.info("Start delete user by id = {}", id);
        ResponseMetaData responseMetaData = service.deleteUserById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/user_name/{user_name}")
    public ResponseEntity<ResponseMetaData> deleteUserByUserName(@PathVariable(value = "user_name") String userName) {
        log.info("Start delete user by username = {}", userName);

        List<MetaDTO> metaList = validationService.validationInputUserName(userName);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        ResponseMetaData responseMetaData = service.deleteUserByUserName(userName);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMetaData> deleteUserByUserName() {
        ResponseMetaData responseMetaData = service.deleteAllUser();
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }
}
