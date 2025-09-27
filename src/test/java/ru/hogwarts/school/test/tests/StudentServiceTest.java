package ru.hogwarts.school.test.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        studentService.getAllStudents().forEach(student ->
                studentService.deleteStudent(student.getId()));

        testStudent = new Student();
        testStudent.setName("Harry Potter");
        testStudent.setAge(17);
        testStudent = studentService.createStudent(testStudent);
    }

    @Test
    void testCreateStudent() {
        Student newStudent = new Student();
        newStudent.setName("Hermione Granger");
        newStudent.setAge(18);

        Student createdStudent = studentService.createStudent(newStudent);

        assertNotNull(createdStudent);
        assertNotNull(createdStudent.getId());
        assertEquals("Hermione Granger", createdStudent.getName());
        assertEquals(18, createdStudent.getAge());
    }

    @Test
    void testGetStudentById() {
        Student foundStudent = studentService.getStudentById(testStudent.getId());

        assertNotNull(foundStudent);
        assertEquals(testStudent.getId(), foundStudent.getId());
        assertEquals("Harry Potter", foundStudent.getName());
        assertEquals(17, foundStudent.getAge());
    }

    @Test
    void testGetStudentById_NotFound() {
        Student foundStudent = studentService.getStudentById(999L);
        assertNull(foundStudent);
    }

    @Test
    void testUpdateStudent() {
        Student updatedStudent = new Student();
        updatedStudent.setName("Harry Potter Updated");
        updatedStudent.setAge(18);

        Student result = studentService.updateStudent(testStudent.getId(), updatedStudent);

        assertNotNull(result);
        assertEquals(testStudent.getId(), result.getId());
        assertEquals("Harry Potter Updated", result.getName());
        assertEquals(18, result.getAge());
    }

    @Test
    void testUpdateStudent_NotFound() {
        Student updatedStudent = new Student();
        updatedStudent.setName("Not Found");
        updatedStudent.setAge(20);

        Student result = studentService.updateStudent(999L, updatedStudent);
        assertNull(result);
    }

    @Test
    void testDeleteStudent() {
        studentService.deleteStudent(testStudent.getId());

        Student deletedStudent = studentService.getStudentById(testStudent.getId());
        assertNull(deletedStudent);
    }

    @Test
    void testGetAllStudents() {
        Student student2 = new Student();
        student2.setName("Ron Weasley");
        student2.setAge(17);
        studentService.createStudent(student2);

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertTrue(students.size() >= 2);
    }

    @Test
    void testGetStudentsByAge() {
        Student student18 = new Student();
        student18.setName("Adult Student");
        student18.setAge(18);
        studentService.createStudent(student18);

        List<Student> studentsAge17 = studentService.getStudentsByAge(17);
        List<Student> studentsAge18 = studentService.getStudentsByAge(18);

        assertNotNull(studentsAge17);
        assertNotNull(studentsAge18);
        assertTrue(studentsAge18.size() >= 1);
    }

    @Test
    void testGetStudentsByAgeBetween() {
        Student student16 = new Student();
        student16.setName("Young Student");
        student16.setAge(16);
        studentService.createStudent(student16);

        List<Student> students = studentService.getStudentsByAgeBetween(16, 18);

        assertNotNull(students);
        assertTrue(students.size() >= 2);
    }

    @Test
    void testGetFacultyByStudentId() {
        Object faculty = studentService.getFacultyByStudentId(testStudent.getId());
        assertTrue(faculty == null || faculty instanceof ru.hogwarts.school.model.Faculty);
    }

    @Test
    void testGetStudentsCount() {
        Student student2 = new Student();
        student2.setName("Student 2");
        student2.setAge(18);
        studentService.createStudent(student2);

        Long count = studentService.getStudentsCount();

        assertNotNull(count);
        assertTrue(count >= 2);
    }

    @Test
    void testGetAverageAge() {
        Student student18 = new Student();
        student18.setName("Student 18");
        student18.setAge(18);
        studentService.createStudent(student18);

        Double averageAge = studentService.getAverageAge();

        assertNotNull(averageAge);
        assertTrue(averageAge > 0);
    }

    @Test
    void testGetLastFiveStudents() {
        for (int i = 1; i <= 7; i++) {
            Student student = new Student();
            student.setName("Student " + i);
            student.setAge(17 + i);
            studentService.createStudent(student);
        }

        List<Student> lastFive = studentService.getLastFiveStudents();

        assertNotNull(lastFive);
        assertTrue(lastFive.size() <= 5);
    }

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

    @Test
    void testPrintStudentsParallel() {
        createTestStudents(6);

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            studentService.printStudentsParallel();

            String output = outputStream.toString();
            assertTrue(output.contains("Test Student"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintStudentsSynchronized() {
        createTestStudents(6);

        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            studentService.printStudentsSynchronized();

            String output = outputStream.toString();
            assertTrue(output.contains("Test Student"));
        } finally {
            System.setOut(originalOut);
        }
    }

    private void createTestStudents(int count) {
        studentService.getAllStudents().forEach(student ->
                studentService.deleteStudent(student.getId()));

        for (int i = 1; i <= count; i++) {
            Student student = new Student();
            student.setName("Test Student " + i);
            student.setAge(20 + i);
            studentService.createStudent(student);
        }
    }
}