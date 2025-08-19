package com.example.quality_management_service.auth.service;

import com.example.quality_management_service.auth.dto.Token;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    public List<Token> login(String username, String password) {
        List<Token> tokens = new ArrayList<>();
        return null;
    }
}
