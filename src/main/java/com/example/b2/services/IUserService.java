package com.example.b2.services;

import com.example.b2.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    void save(User user);
}
