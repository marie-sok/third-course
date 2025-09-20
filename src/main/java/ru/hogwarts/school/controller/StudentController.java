package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Students", description = "API for student management")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Create student")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student createdStudent = studentService.create(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return student != null
                ? ResponseEntity.ok(student)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.update(id, student);
        return updatedStudent != null
                ? ResponseEntity.ok(updatedStudent)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (studentService.findById(id) != null) {
            studentService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/age-between")
    @Operation(summary = "Search student by the age's diapason")
    public List<Student> getStudentsByAgeBetween(@RequestParam int minAge,
                                                 @RequestParam int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/by-faculty")
    @Operation(summary = "Search student by the faculty")
    public List<Student> getStudentsByFaculty(@RequestParam Long facultyId) {
        return studentService.findByFacultyId(facultyId);
    }

    @GetMapping
    @Operation(summary = "Get all students")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }
}