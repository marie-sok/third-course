package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void testGetByName() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red", null);
        when(facultyService.findByName(anyString()))
                .thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/by-name")
                        .param("name", "Gryffindor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetByColor() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red", null);
        when(facultyService.findByColor(anyString()))
                .thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/by-color")
                        .param("color", "Red")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$.length()").value(1));
    }
}