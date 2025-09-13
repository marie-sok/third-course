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
public class StudentControllerNewEndpointsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        Student student1 = new Student();
        student1.setName("Ron Weasley");
        student1.setAge(17);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Ginny Weasley");
        student2.setAge(16);
        studentRepository.save(student2);

        Student student3 = new Student();
        student3.setName("Neville Longbottom");
        student3.setAge(17);
        studentRepository.save(student3);
    }

    @Test
    public void testGetStudentsCount() {
        String url = "http://localhost:" + port + "/student/count";
        ResponseEntity<Integer> response = restTemplate.getForEntity(url, Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody());
    }

    @Test
    public void testGetStudentsAverageAge() {
        String url = "http://localhost:" + port + "/student/average-age";
        ResponseEntity<Double> response = restTemplate.getForEntity(url, Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.getBody() > 16.6 && response.getBody() < 16.7);
    }

    @Test
    public void testGetLastFiveStudents() {
        String url = "http://localhost:" + port + "/student/last-five";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(response.getBody().contains("Ron Weasley"));
        assertTrue(response.getBody().contains("Ginny Weasley"));
        assertTrue(response.getBody().contains("Neville Longbottom"));
    }

    @Test
    public void testGetAvatarsWithPagination() {
        String url = "http://localhost:" + port + "/avatar?page=0&size=5";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("[]", response.getBody());
    }
}