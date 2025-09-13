package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        Student student = new Student();
        student.setName("Test Student");
        student.setAge(20);
        studentRepository.save(student);
    }

    @Test
    public void testGetStudentsByAgeBetween() {
        String url = "http://localhost:" + port + "/student/age-between?minAge=18&maxAge=25";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetStudentsByFaculty() {
        String url = "http://localhost:" + port + "/student/by-faculty?facultyId=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}