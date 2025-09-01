package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student testStudent;
    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty();
        testFaculty.setId(1L);
        testFaculty.setName("Gryffindor");
        testFaculty.setColor("Red");

        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setName("Harry Potter");
        testStudent.setAge(17);
        testStudent.setFaculty(testFaculty);
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.findById(1L)).thenReturn(testStudent);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.age").value(17))
                .andExpect(jsonPath("$.faculty.name").value("Gryffindor"));
    }

    @Test
    void testGetStudentsByAgeBetween() throws Exception {
        when(studentService.findByAgeBetween(16, 18)).thenReturn(List.of(testStudent));

        mockMvc.perform(get("/student/age-between")
                        .param("minAge", "16")
                        .param("maxAge", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }

    @Test
    void testGetStudentsByFaculty() throws Exception {
        when(studentService.findByFacultyId(1L)).thenReturn(List.of(testStudent));

        mockMvc.perform(get("/student/by-faculty")
                        .param("facultyId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }
}