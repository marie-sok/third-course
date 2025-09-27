package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testStudentEndpoints() {
        String baseUrl = "http://localhost:" + port;

        Student student = new Student();
        student.setName("Test Student");
        student.setAge(20);

        ResponseEntity<Student> postResponse = restTemplate.postForEntity(
                baseUrl + "/student", student, Student.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        Long studentId = postResponse.getBody().getId();

        ResponseEntity<Student> getResponse = restTemplate.getForEntity(
                baseUrl + "/student/" + studentId, Student.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Test Student", getResponse.getBody().getName());
    }

    @Test
    void testFacultyEndpoints() {
        String baseUrl = "http://localhost:" + port;

        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        faculty.setColor("Blue");

        ResponseEntity<Faculty> postResponse = restTemplate.postForEntity(
                baseUrl + "/faculty", faculty, Faculty.class);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());

        ResponseEntity<String> longestNameResponse = restTemplate.getForEntity(
                baseUrl + "/faculty/longest-name", String.class);
        assertEquals(HttpStatus.OK, longestNameResponse.getStatusCode());
    }
}