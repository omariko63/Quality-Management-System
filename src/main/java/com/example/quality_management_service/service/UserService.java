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
import java.util.NoSuchElementException;
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
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));
        }

        User user = userMapper.toEntity(dto);
        user.setRole(role);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Integer id, UserDto dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Role role = roleRepository.findById(dto.getRole().getId())
                .orElseThrow(() -> new NoSuchElementException("Role not found"));

        if (dto.getUsername() != null) {
            existingUser.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            existingUser.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        existingUser.setRole(role);

        User updated = userRepository.save(existingUser);
        return userMapper.toDto(updated);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }
}
