package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/students";
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(17);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                getBaseUrl(), student, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getName());
    }

    @Test
    void testGetStudent() {
        Student student = new Student();
        student.setName("Hermione Granger");
        student.setAge(17);
        ResponseEntity<Student> createResponse = restTemplate.postForEntity(
                getBaseUrl(), student, Student.class);
        Long studentId = createResponse.getBody().getId();

        ResponseEntity<Student> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + studentId, Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hermione Granger", response.getBody().getName());
    }

    @Test
    void testGetStudentsByAge() {
        Student student = new Student();
        student.setName("Ron Weasley");
        student.setAge(17);
        restTemplate.postForEntity(getBaseUrl(), student, Student.class);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                getBaseUrl() + "?age=17", Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testPrintStudentsParallel() {
        createTestStudents();

        ResponseEntity<Void> response = restTemplate.getForEntity(
                getBaseUrl() + "/print-parallel", Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testPrintStudentsSynchronized() {
        createTestStudents();

        ResponseEntity<Void> response = restTemplate.getForEntity(
                getBaseUrl() + "/print-synchronized", Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private void createTestStudents() {
        for (int i = 1; i <= 6; i++) {
            Student student = new Student();
            student.setName("Test Student " + i);
            student.setAge(20 + i);
            restTemplate.postForEntity(getBaseUrl(), student, Student.class);
        }
    }
}