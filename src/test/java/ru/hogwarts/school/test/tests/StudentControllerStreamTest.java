package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.service.StudentService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentControllerStreamTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testCalculateSum() {
        long result = studentService.calculateSum();
        long expected = 500000500000L;
        assertEquals(expected, result);
    }

    @Test
    void testCalculateSumOptimized() {
        long result = studentService.calculateSumOptimized();
        long expected = 500000500000L;
        assertEquals(expected, result);
    }
}