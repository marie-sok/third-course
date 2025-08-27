package ru.hogwarts.school.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetStudentsByAgeBetween() {
        String url = "http://localhost:" + port + "/student/age-between?minAge=18&maxAge=25";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentsByFaculty() {
        String url = "http://localhost:" + port + "/student/by-faculty?facultyId=1";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}