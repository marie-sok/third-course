package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student update(Long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> findByFacultyId(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }


    public Integer getStudentsCount() {
        return studentRepository.getStudentsCount();
    }

    public Double getStudentsAverageAge() {
        return studentRepository.getStudentsAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}