package com.example.quality_management_service.service;

import com.example.quality_management_service.dto.UserDto;
import com.example.quality_management_service.mapper.UserMapper;
import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.model.User;
import com.example.quality_management_service.repository.RoleRepository;
import com.example.quality_management_service.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDto createUser(UserDto dto) {
        Role role = null;
        if (dto.role() != null && dto.role().id() != null) {
            role = roleRepository.findById(dto.role().id())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        }
        String hashedPassword = passwordEncoder.encode(dto.password());
        User user = UserMapper.toEntity(dto, role, hashedPassword);

        return UserMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto updateUser(Integer id, UserDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findById(dto.role().id())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        existingUser.setUsername(dto.username());
        existingUser.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(dto.password()));
        }

        existingUser.setRole(role);

        return UserMapper.toDto(userRepository.save(existingUser));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
