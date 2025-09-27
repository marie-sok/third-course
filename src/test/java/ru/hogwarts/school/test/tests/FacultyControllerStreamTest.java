package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class FacultyControllerStreamTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void getAllFaculties_ShouldReturnAllFaculties() throws Exception {
        Faculty faculty1 = new Faculty("Gryffindor", "Red");
        Faculty faculty2 = new Faculty("Slytherin", "Green");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[1].name").value("Slytherin"));
    }

    @Test
    void searchFaculties_ShouldReturnMatchingFaculties() throws Exception {
        Faculty faculty1 = new Faculty("Gryffindor", "Red");
        Faculty faculty2 = new Faculty("Ravenclaw", "Blue");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.searchFaculties("Gryffindor", "Red")).thenReturn(Arrays.asList(faculty1));

        mockMvc.perform(get("/faculty/search")
                        .param("name", "Gryffindor")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }
}