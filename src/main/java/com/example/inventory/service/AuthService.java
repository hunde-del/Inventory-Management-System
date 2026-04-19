package com.example.inventory.service;

import com.example.inventory.dto.RegisterRequest;
import com.example.inventory.entity.User;

public interface AuthService {
    User register(RegisterRequest request);
}
