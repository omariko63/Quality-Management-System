package com.example.quality_management_service.service;

import com.example.quality_management_service.dto.UserDto;
import com.example.quality_management_service.mapper.UserMapper;
import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.model.User;
import com.example.quality_management_service.repository.RoleRepository;
import com.example.quality_management_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDto createUser(UserDto dto) {
        Role role = null;
        if (dto.getRole() != null && dto.getRole().getId() != null) {
            role = roleRepository.findById(dto.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        }
        User user = userMapper.toEntity(dto);
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto updateUser(Integer id, UserDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(dto.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        existingUser.setRole(role);

        return userMapper.toDto(userRepository.save(existingUser));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
