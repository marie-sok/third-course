package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.service.StudentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentControllerIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Test
    void calculateSumOptimized_ShouldReturnCorrectValue() {
        long result = studentService.calculateSumOptimized();
        long expected = 500000500000L;

        assertEquals(expected, result);
    }
}