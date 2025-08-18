package com.example.quality_management_service.testing;

import com.example.quality_management_service.dto.PermissionDTO;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.repository.PermissionRepository;
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
class PermissionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setup() {
        permissionRepository.deleteAll();
    }

    @Test
    void testCreatePermission() throws Exception {
        String json = "{" +
                "\"name\": \"READ\"," +
                "\"description\": \"Read permission\"}";
        mockMvc.perform(post("/api/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("READ")))
                .andExpect(jsonPath("$.description", is("Read permission")));
    }

    @Test
    void testGetAllPermissions() throws Exception {
        permissionRepository.save(new Permission("READ", "Read permission"));
        permissionRepository.save(new Permission("WRITE", "Write permission"));
        mockMvc.perform(get("/api/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetPermissionById() throws Exception {
        Permission permission = permissionRepository.save(new Permission("READ", "Read permission"));
        mockMvc.perform(get("/api/permissions/" + permission.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("READ")));
    }

    @Test
    void testUpdatePermission() throws Exception {
        Permission permission = permissionRepository.save(new Permission("READ", "Read permission"));
        String json = "{" +
                "\"name\": \"UPDATED\"," +
                "\"description\": \"Updated permission\"}";
        mockMvc.perform(put("/api/permissions/" + permission.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UPDATED")))
                .andExpect(jsonPath("$.description", is("Updated permission")));
    }

    @Test
    void testDeletePermission() throws Exception {
        Permission permission = permissionRepository.save(new Permission("READ", "Read permission"));
        mockMvc.perform(delete("/api/permissions/" + permission.getId()))
                .andExpect(status().isNoContent());
    }
}

