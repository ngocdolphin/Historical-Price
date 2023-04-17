package com.tdt.historical_prices.repository;

import com.tdt.historical_prices.entity.Role;
import com.tdt.historical_prices.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);
    User findById(int id);
    User findByUserName(String userName);
    User findByRole(Role role);
    User findByToken(String token);
}
