package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/age-between")
    public ResponseEntity<List<Student>> getStudentsByAgeBetween(
            @RequestParam int minAge,
            @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/by-faculty")
    public ResponseEntity<List<Student>> getStudentsByFaculty(@RequestParam Long facultyId) {
        return ResponseEntity.ok(studentService.findByFacultyId(facultyId));
    }
}