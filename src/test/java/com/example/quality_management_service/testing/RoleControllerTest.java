package com.example.quality_management_service.testing;

import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.repository.RoleRepository;
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
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        roleRepository.deleteAll();
    }

    @Test
    void testCreateRole() throws Exception {
        String json = "{" +
                "\"roleName\": \"ADMIN\"," +
                "\"description\": \"Administrator role\"}";
        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName", is("ADMIN")))
                .andExpect(jsonPath("$.description", is("Administrator role")));
    }

    @Test
    void testGetAllRoles() throws Exception {
        roleRepository.save(new Role("ADMIN", "Administrator role"));
        roleRepository.save(new Role("USER", "User role"));
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetRoleById() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        mockMvc.perform(get("/api/roles/" + role.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName", is("ADMIN")));
    }

    @Test
    void testUpdateRole() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        String json = "{" +
                "\"roleName\": \"UPDATED\"," +
                "\"description\": \"Updated role\"}";
        mockMvc.perform(put("/api/roles/" + role.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName", is("UPDATED")))
                .andExpect(jsonPath("$.description", is("Updated role")));
    }

    @Test
    void testDeleteRole() throws Exception {
        Role role = roleRepository.save(new Role("ADMIN", "Administrator role"));
        mockMvc.perform(delete("/api/roles/" + role.getId()))
                .andExpect(status().isNoContent());
    }
}

