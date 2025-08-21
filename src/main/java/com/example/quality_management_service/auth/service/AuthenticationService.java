package com.example.quality_management_service.auth.service;

import com.example.quality_management_service.auth.dto.Token;
import com.example.quality_management_service.auth.util.JwtUtil;
import com.example.quality_management_service.management.dto.UserDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    String validationUrl = "http://localhost:8080/api/users/validate";
    public AuthenticationService(RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }


    public Token login(String username, String password) {
        //List<Token> tokens = new ArrayList<>();
        //restTemplate.postForObject()
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> request = new HashMap<>();
        request.put("username", username);
        request.put("password", password);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);
        System.out.println("mapped");
        System.out.println("here!");
        UserDto user = restTemplate.postForObject(
                validationUrl,
                requestEntity,
                UserDto.class
        );
        System.out.println("response sent and received");
        System.out.println(user);
        if (user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        System.out.println("creating tokens!");
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : "";
        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), roleName);
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), roleName);

        return new Token(accessToken, refreshToken);
        //return null;
    }
}
