package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Test
    void getFaculty_ShouldReturnFaculty() throws Exception {
        Faculty faculty = new Faculty("Gryffindor", "Red");
        faculty.setId(1L);

        when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    void getFacultiesByColor_ShouldReturnFaculties() throws Exception {
        Faculty faculty1 = new Faculty("Gryffindor", "Red");
        List<Faculty> faculties = Arrays.asList(faculty1);

        when(facultyService.getFacultiesByColor("Red")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/color/Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    void searchFaculties_ShouldReturnFaculties() throws Exception {
        Faculty faculty1 = new Faculty("Gryffindor", "Red");
        List<Faculty> faculties = Arrays.asList(faculty1);

        when(facultyService.searchFaculties("Gryffindor", null)).thenReturn(faculties);

        mockMvc.perform(get("/faculty/search")
                        .param("name", "Gryffindor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }
}