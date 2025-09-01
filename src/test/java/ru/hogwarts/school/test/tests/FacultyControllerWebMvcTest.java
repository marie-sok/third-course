package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Gryffindor");
        testFaculty.setColor("Red");
    }

    @Test
    void testGetByColor() throws Exception {
        when(facultyService.getByColor("Red"))
                .thenReturn(List.of(testFaculty));

        mockMvc.perform(get("/faculty/by-color")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    void testGetByName() throws Exception {
        when(facultyService.getByName("Gryffindor"))
                .thenReturn(List.of(testFaculty));

        mockMvc.perform(get("/faculty/by-name")
                        .param("name", "Gryffindor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    void testSearchByNameOrColor() throws Exception {
        when(facultyService.findByNameOrColor("Red"))
                .thenReturn(List.of(testFaculty));

        mockMvc.perform(get("/faculty/search")
                        .param("nameOrColor", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }
}