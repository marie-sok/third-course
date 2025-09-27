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
import org.springframework.test.context.TestPropertySource;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.liquibase.enabled=false"})
public class StudentControllerNewEndpointsTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        studentRepository.deleteAll();

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
        ResponseEntity<Integer> response = restTemplate.getForEntity(baseUrl + "/student/count", Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody());
    }

    @Test
    public void testGetStudentsAverageAge() {
        ResponseEntity<Double> response = restTemplate.getForEntity(baseUrl + "/student/average-age", Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(16.666666666666668, response.getBody(), 0.0001);
    }

    @Test
    public void testGetStudentNamesStartingWithA() {
        Student student4 = new Student();
        student4.setName("Alice");
        student4.setAge(20);
        studentRepository.save(student4);

        Student student5 = new Student();
        student5.setName("Andrew");
        student5.setAge(22);
        studentRepository.save(student5);

        ResponseEntity<String[]> response = restTemplate.getForEntity(baseUrl + "/student/names-starting-with-a", String[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        assertTrue(java.util.Arrays.asList(response.getBody()).contains("ALICE"));
        assertTrue(java.util.Arrays.asList(response.getBody()).contains("ANDREW"));
    }

    @Test
    public void testCalculateSum() {
        ResponseEntity<Long> response = restTemplate.getForEntity(baseUrl + "/student/stream/sum", Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(500000500000L, response.getBody());
    }

    @Test
    public void testCalculateSumOptimized() {
        ResponseEntity<Long> response = restTemplate.getForEntity(baseUrl + "/student/stream/sum-optimized", Long.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(500000500000L, response.getBody());
    }
}