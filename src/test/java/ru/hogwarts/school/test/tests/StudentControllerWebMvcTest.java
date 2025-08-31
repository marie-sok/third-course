package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void testGetStudentsByAgeBetween() throws Exception {
        Faculty gryffindor = new Faculty("Gryffindor", "Scarlet and Gold");
        gryffindor.setId(1L);

        Student student = new Student("Harry Potter", 17, gryffindor);
        student.setId(1L);

        when(studentService.findByAgeBetween(anyInt(), anyInt()))
                .thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age-between")
                        .param("minAge", "16")
                        .param("maxAge", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[0].age").value(17));
    }

    @Test
    void testGetStudentsByFaculty() throws Exception {
        Faculty gryffindor = new Faculty("Gryffindor", "Scarlet and Gold");
        gryffindor.setId(1L);

        Student student = new Student("Harry Potter", 17, gryffindor);
        student.setId(1L);

        when(studentService.findByFacultyId(anyLong()))
                .thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-faculty")
                        .param("facultyId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[0].faculty.name").value("Gryffindor"));
    }
}