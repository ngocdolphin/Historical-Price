package com.tdt.historical_prices.controller;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.form.RequestBodyUser;
import com.tdt.historical_prices.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tdt.historical_prices.service.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {
    private final AuthService authService;

    private final JwtUtils jwtUtils;

    private final ValidationService validationService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMetaData> signup(@RequestBody RequestBodyUser requestBodyUser) {
        log.info("Start signing up with user = {}", requestBodyUser.getUserName());

        List<MetaDTO> metaList = validationService.validationInputRequestBodyUser(requestBodyUser);
        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
        
        ResponseMetaData ResponseDTO = authService.registerUser(requestBodyUser);
        return ResponseEntity.status(CommonConstant.MetaData.PENDING_ACCOUNT.getMetaCode())
                .body(ResponseDTO);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMetaData> signin(@RequestBody RequestBodyUser requestBodyUser) {
        log.info("Start signing in with user = {}", requestBodyUser.getUserName());
        List<MetaDTO> metaList = validationService.validationInputRequestBodyUser(requestBodyUser);

        ResponseMetaData loginResponseDTO = new ResponseMetaData();
        if (!CollectionUtils.isEmpty(metaList)) {
            loginResponseDTO.setMeta(metaList);
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }

        loginResponseDTO = authService.loginUser(requestBodyUser);
        if (!CommonConstant.MetaData.SUCCESS.getMetaCode().equals(((MetaDTO) loginResponseDTO.getMeta()).getCode())) {
            return ResponseEntity.status(CommonConstant.MetaData.BAD_REQUEST.getMetaCode()).body(loginResponseDTO);
        }
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode()).body(loginResponseDTO);
    }

    @PostMapping("/validate")
    public ResponseEntity<ResponseMetaData> validateJwt(HttpServletRequest request) {
        String token = jwtUtils.getTokenFromRequest(request);
        ResponseMetaData ResponseDTO = authService.validateToken(token);
        return ResponseEntity.status(CommonConstant.MetaData.SUCCESS.getMetaCode())
                .body(ResponseDTO);
    }
}
