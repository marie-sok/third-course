package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getStudentsUrl() {
        return "http://localhost:" + port + "/students";
    }

    private String getFacultyUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    void testStudentEndpoints() {

        Student student = new Student();
        student.setName("Test Student");
        student.setAge(20);

        ResponseEntity<Student> createResponse = restTemplate.postForEntity(
                getStudentsUrl(), student, Student.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());


        Long studentId = createResponse.getBody().getId();
        ResponseEntity<Student> getResponse = restTemplate.getForEntity(
                getStudentsUrl() + "/" + studentId, Student.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());


        createTestStudents();

        ResponseEntity<Void> parallelResponse = restTemplate.getForEntity(
                getStudentsUrl() + "/print-parallel", Void.class);
        assertEquals(HttpStatus.OK, parallelResponse.getStatusCode());

        ResponseEntity<Void> syncResponse = restTemplate.getForEntity(
                getStudentsUrl() + "/print-synchronized", Void.class);
        assertEquals(HttpStatus.OK, syncResponse.getStatusCode());
    }

    @Test
    void testFacultyEndpoints() {
        // Создаем факультет
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getFacultyUrl(), faculty, Faculty.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());


        Long facultyId = createResponse.getBody().getId();
        ResponseEntity<Faculty> getResponse = restTemplate.getForEntity(
                getFacultyUrl() + "/" + facultyId, Faculty.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());


        ResponseEntity<Faculty[]> colorResponse = restTemplate.getForEntity(
                getFacultyUrl() + "?color=Red", Faculty[].class);
        assertEquals(HttpStatus.OK, colorResponse.getStatusCode());


        ResponseEntity<Faculty[]> nameResponse = restTemplate.getForEntity(
                getFacultyUrl() + "?name=Gryffindor", Faculty[].class);
        assertEquals(HttpStatus.OK, nameResponse.getStatusCode());


        ResponseEntity<String> longestNameResponse = restTemplate.getForEntity(
                getFacultyUrl() + "/longest-name", String.class);
        assertEquals(HttpStatus.OK, longestNameResponse.getStatusCode());
    }

    private void createTestStudents() {
        for (int i = 1; i <= 6; i++) {
            Student student = new Student();
            student.setName("Test Student " + i);
            student.setAge(20 + i);
            restTemplate.postForEntity(getStudentsUrl(), student, Student.class);
        }
    }
}