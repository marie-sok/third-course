package ru.hogwarts.school.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetByName() {
        String url = "http://localhost:" + port + "/faculty/by-name?name=Gryffindor";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetByColor() {
        String url = "http://localhost:" + port + "/faculty/by-color?color=Red";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSearchWithNameAndColor() {
        String url = "http://localhost:" + port + "/faculty/search?name=Gryffindor&color=Red";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSearchWithNameOnly() {
        String url = "http://localhost:" + port + "/faculty/search?name=Gryffindor";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSearchWithColorOnly() {
        String url = "http://localhost:" + port + "/faculty/search?color=Red";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSearchWithoutParams() {
        String url = "http://localhost:" + port + "/faculty/search";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(400, response.getStatusCodeValue());
    }
}