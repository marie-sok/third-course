package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        Student student = new Student(1L, "Harry Potter", 17, null);
        when(studentService.findByAgeBetween(anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age-between")
                        .param("minAge", "16")
                        .param("maxAge", "18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[0].age").value(17))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Student student = new Student(1L, "Harry Potter", 17, null);
        when(studentService.findByFacultyId(anyLong()))
                .thenReturn(Collections.singletonList(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-faculty")
                        .param("facultyId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Harry Potter"))
                .andExpect(jsonPath("$[0].age").value(17))
                .andExpect(jsonPath("$.length()").value(1));
    }
}