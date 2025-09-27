package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.service.StudentService;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerStreamTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void calculateSum_ShouldReturnSum() throws Exception {
        when(studentService.calculateSum()).thenReturn(500000L);

        mockMvc.perform(get("/student/stream/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string("500000"));
    }

    @Test
    void calculateSumOptimized_ShouldReturnSum() throws Exception {
        when(studentService.calculateSumOptimized()).thenReturn(500000L);

        mockMvc.perform(get("/student/stream/sum-optimized"))
                .andExpect(status().isOk())
                .andExpect(content().string("500000"));
    }

    @Test
    void getStudentNamesStartingWithA_ShouldReturnNames() throws Exception {
        List<String> names = Arrays.asList("ALICE", "ANDREW", "ANNA");
        when(studentService.getStudentNamesStartingWithA()).thenReturn(names);

        mockMvc.perform(get("/student/names-starting-with-a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("ALICE"))
                .andExpect(jsonPath("$[1]").value("ANDREW"))
                .andExpect(jsonPath("$[2]").value("ANNA"));
    }

    @Test
    void getStudentsCount_ShouldReturnCount() throws Exception {
        when(studentService.getStudentsCount()).thenReturn(5);

        mockMvc.perform(get("/student/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void getAverageAge_ShouldReturnAverage() throws Exception {
        when(studentService.getStudentsAverageAge()).thenReturn(20.5);

        mockMvc.perform(get("/student/average-age"))
                .andExpect(status().isOk())
                .andExpect(content().string("20.5"));
    }
}