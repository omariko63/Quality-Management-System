package com.example.quality_management_service.testing;

import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.model.User;
import com.example.quality_management_service.repository.RoleRepository;
import com.example.quality_management_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testCreateUser() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        String json = "{" +
                "\"username\": \"testuser\"," +
                "\"email\": \"testuser@gmail.com\"," +
                "\"password\": \"password123\"," +
                "\"role\": {\"id\": " + role.getId() + "}" +
                "}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("testuser")))
                .andExpect(jsonPath("$.email", is("testuser@gmail.com")))
                .andExpect(jsonPath("$.role.id", is(role.getId())));
    }

    @Test
    void testGetAllUsers() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPasswordHash("password1");
        user1.setRole(role);
        userRepository.save(user1);
        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@gmail.com");
        user2.setPasswordHash("password2");
        user2.setRole(role);
        userRepository.save(user2);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetUserById() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");
        user.setPasswordHash("password1");
        user.setRole(role);
        user = userRepository.save(user);
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user1")));
    }

    @Test
    void testUpdateUser() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");
        user.setPasswordHash("password1");
        user.setRole(role);
        user = userRepository.save(user);
        String json = "{" +
                "\"username\": \"updateduser\"," +
                "\"email\": \"updateduser@gmail.com\"," +
                "\"password\": \"newpassword\"," +
                "\"role\": {\"id\": " + role.getId() + "}" +
                "}";
        mockMvc.perform(put("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updateduser")))
                .andExpect(jsonPath("$.email", is("updateduser@gmail.com")));
    }

    @Test
    void testDeleteUser() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        User user = new User();
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");
        user.setPasswordHash("password1");
        user.setRole(role);
        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());
    }
}
