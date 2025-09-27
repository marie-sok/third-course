package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(required = false) Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.getStudentsByAge(age));
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/by-age-between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(studentService.getStudentsByAgeBetween(min, max));
    }

    @GetMapping("/faculty/{studentId}")
    public ResponseEntity<Object> getFacultyByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(studentId));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }


    @GetMapping("/print-parallel")
    public ResponseEntity<Void> printStudentsParallel() {
        studentService.printStudentsParallel();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<Void> printStudentsSynchronized() {
        studentService.printStudentsSynchronized();
        return ResponseEntity.ok().build();
    }
}