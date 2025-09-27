package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student) {
        Student existingStudent = getStudent(id);
        if (existingStudent == null) {
            return null;
        }
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setFaculty(student.getFaculty());
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> getStudentsByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getStudentsByFaculty(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    public Integer getStudentsCount() {
        return studentRepository.findAll().size();
    }

    public Double getStudentsAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public List<String> getStudentNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public Long calculateSum() {
        return java.util.stream.Stream.iterate(1L, a -> a + 1L)
                .limit(1_000_000L)
                .reduce(0L, Long::sum);
    }

    public Long calculateSumOptimized() {
        return (1_000_000L * (1_000_000L + 1L)) / 2L;
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}