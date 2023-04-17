package com.tdt.historical_prices.entity;

import com.tdt.historical_prices.form.UserCreateForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_info")
public class User {
    @Id
    @Column(name = "login_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role", length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "access_token")
    private String token;

    @Column(name = "expired_time")
    private Timestamp expiredTime;

    public User(UserCreateForm form) {
        this.userName = form.getUserName();
        this.role = form.getRole();
    }
}
