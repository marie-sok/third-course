package ru.hogwarts.school.test.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private AvatarService avatarService;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
        student.setAge(17);

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.age").value(17));
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Hermione Granger");
        student.setAge(18);

        Student createdStudent = new Student();
        createdStudent.setId(1L);
        createdStudent.setName("Hermione Granger");
        createdStudent.setAge(18);

        when(studentService.createStudent(any(Student.class))).thenReturn(createdStudent);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hermione Granger"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    void testGetStudentsByAge() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student1");
        student1.setAge(17);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student2");
        student2.setAge(17);

        List<Student> students = Arrays.asList(student1, student2);

        when(studentService.getStudentsByAge(17)).thenReturn(students);

        mockMvc.perform(get("/students").param("age", "17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testPrintStudentsParallel() throws Exception {
        mockMvc.perform(get("/students/print-parallel"))
                .andExpect(status().isOk());
    }

    @Test
    void testPrintStudentsSynchronized() throws Exception {
        mockMvc.perform(get("/students/print-synchronized"))
                .andExpect(status().isOk());
    }
}