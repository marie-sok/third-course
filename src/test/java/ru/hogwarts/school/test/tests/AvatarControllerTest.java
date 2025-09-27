package ru.hogwarts.school.test.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class AvatarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private StudentService studentService;

    @Test
    void uploadAvatar_ShouldReturnAvatar() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        avatar.setFilePath("/avatars/1.jpg");
        avatar.setMediaType("image/jpeg");
        avatar.setFileSize(1024L);

        when(avatarService.saveAvatar(any(Avatar.class))).thenReturn(avatar);

        mockMvc.perform(post("/avatar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(avatar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.filePath").value("/avatars/1.jpg"));
    }

    @Test
    void getAvatarByStudentId_WhenExists_ShouldReturnAvatar() throws Exception {
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        avatar.setFilePath("/avatars/1.jpg");

        when(avatarService.getAvatarByStudentId(1L)).thenReturn(Optional.of(avatar));

        mockMvc.perform(get("/avatar/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.filePath").value("/avatars/1.jpg"));
    }

    @Test
    void getAvatarByStudentId_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(avatarService.getAvatarByStudentId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/avatar/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void deleteAvatar_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/avatar/1"))
                .andExpect(status().isOk());
    }
}