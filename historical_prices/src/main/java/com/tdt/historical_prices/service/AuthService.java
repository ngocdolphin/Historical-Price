package com.tdt.historical_prices.service;

import com.tdt.historical_prices.config.CommonConstant;
import com.tdt.historical_prices.dto.MetaDTO;
import com.tdt.historical_prices.dto.ResponseMetaData;
import com.tdt.historical_prices.entity.Role;
import com.tdt.historical_prices.entity.User;
import com.tdt.historical_prices.form.RequestBodyUser;
import com.tdt.historical_prices.jwt.JwtUtils;
import com.tdt.historical_prices.repository.UserRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    @Value("${app.jwtExpirationMs}")
    private int JWT_EXPIRATION_MS;

    public ResponseMetaData registerUser(RequestBodyUser requestBodyUser) {
        String hash = userService.encryptPassword(requestBodyUser.getPassword());
        User user = new User(requestBodyUser.getUserName(), hash);
        user.setRole(Role.EMPLOYEE);
        repository.save(user);
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.PENDING_ACCOUNT), user.toString());
    }

    public ResponseMetaData loginUser(RequestBodyUser requestBodyUser) {
        User user = repository.findByUserName(requestBodyUser.getUserName());
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), "User not exists");
        }

        String hash = userService.encryptPassword(requestBodyUser.getPassword());
        if (!user.getPassword().equals(hash)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), "Incorrect password");
        }

        String token = jwtUtils.generateToken(requestBodyUser.getUserName());
        user.setToken(token);
        user.setExpiredTime(new Timestamp((Instant.now().toEpochMilli() + JWT_EXPIRATION_MS)));
        repository.save(user);

        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), "Token: " + token);
    }

    public ResponseMetaData validateToken(String token) {
        Instant now = Instant.now();
        if (StringUtil.isNullOrEmpty(token)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.INVALID_TOKEN), "Token null");
        }
        User user = repository.findByToken(token);
        if (Objects.isNull(user)) {
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.INVALID_TOKEN), "User not found");
        }

        if (now.toEpochMilli() > user.getExpiredTime().toInstant().toEpochMilli()) {
            user.setExpiredTime(null);
            user.setToken(null);
            repository.save(user);
            return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.BAD_REQUEST), "Token Expired");
        }
        return new ResponseMetaData(new MetaDTO(CommonConstant.MetaData.SUCCESS), "Token: " + token);

    }
}
