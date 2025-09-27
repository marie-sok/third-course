package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    void testGetFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl(), faculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + facultyId, Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
    }

    @Test
    void testGetByName() {
        Faculty faculty = new Faculty();
        faculty.setName("Ravenclaw");
        faculty.setColor("Blue");
        restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);

        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                getBaseUrl() + "?name=Ravenclaw", Faculty[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetByColor() {
        Faculty faculty = new Faculty();
        faculty.setName("Hufflepuff");
        faculty.setColor("Yellow");
        restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);

        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                getBaseUrl() + "?color=Yellow", Faculty[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetStudentsByFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Slytherin");
        faculty.setColor("Green");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl(), faculty, Faculty.class);
        Long facultyId = createResponse.getBody().getId();

        ResponseEntity<Object[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + facultyId + "/students", Object[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}