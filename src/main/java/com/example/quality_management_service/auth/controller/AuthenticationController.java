package com.example.quality_management_service.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthenticationController {
    @PostMapping("/login")
    public String login(@RequestBody String username, @RequestBody String password){
        return null;
    }
}
