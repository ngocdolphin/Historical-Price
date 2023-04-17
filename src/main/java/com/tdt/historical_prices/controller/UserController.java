package com.tdt.historical_prices.controller;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.config.NumberConstants;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.entity.Role;
import com.tdt.historical_prices.form.UserCreateForm;
import com.tdt.historical_prices.form.UserUpdateForm;
import com.tdt.historical_prices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<ResponseMetaData> createUser(@RequestBody UserCreateForm form) {
        ResponseMetaData responseMetaData = service.createUser(form);
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
        ResponseMetaData responseMetaData = service.getUserById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/user-name")
    public ResponseEntity<ResponseMetaData> getUserByUserName(@RequestParam(value = "user-name") String userName) {
        ResponseMetaData responseMetaData = service.getUserByUserName(userName);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ResponseMetaData> getUserByRole(@PathVariable(value = "role") Role role) {
        ResponseMetaData responseMetaData = service.getUserByRole(role);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMetaData> updateUserById(@PathVariable(value = "id") int id, @RequestBody UserUpdateForm form) {
        ResponseMetaData responseMetaData = service.updateUserById(id, form);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @PutMapping("/update/user_name/{user_name}")
    public ResponseEntity<ResponseMetaData> updateUserByUserName(@PathVariable(value = "user_name") String userName, @RequestBody UserUpdateForm form) {
        ResponseMetaData responseMetaData = service.updateUserByUserName(userName, form);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMetaData> deleteUserById(@PathVariable(value = "id") int id) {
        ResponseMetaData responseMetaData = service.deleteUserById(id);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete/user_name/{user_name}")
    public ResponseEntity<ResponseMetaData> deleteUserByUserName(@PathVariable(value = "user_name") String userName) {
        ResponseMetaData responseMetaData = service.deleteUserByUserName(userName);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMetaData> deleteUserByUserName() {
        ResponseMetaData responseMetaData = service.deleteAllUser();
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(responseMetaData);
    }
}
