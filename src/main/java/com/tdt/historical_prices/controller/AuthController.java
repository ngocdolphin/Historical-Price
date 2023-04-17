package com.tdt.historical_prices.controller;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.form.RequestBodyUser;
import com.tdt.historical_prices.jwt.JwtUtils;
import com.tdt.historical_prices.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/user")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseMetaData> signin(@RequestBody RequestBodyUser requestBodyUser) {
        log.info("Signing in with user: {}", requestBodyUser.getUserName());
        ResponseMetaData loginResponseDTO = authService.loginUser(requestBodyUser);
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
