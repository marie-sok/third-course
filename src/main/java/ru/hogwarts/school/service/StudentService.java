package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        logger.info("Was invoked method for create student: {}", student.getName());
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        logger.info("Was invoked method for find student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student update(Long id, Student student) {
        logger.info("Was invoked method for update student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            logger.error("Cannot update student: student with id {} not found", id);
            return null;
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        logger.info("Was invoked method for delete student with id: {}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            logger.debug("Student with id {} deleted successfully", id);
        } else {
            logger.warn("Cannot delete student: student with id {} not found", id);
        }
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find students by age between {} and {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public List<Student> findByFacultyId(Long facultyId) {
        logger.info("Was invoked method for find students by faculty id: {}", facultyId);
        return studentRepository.findByFacultyId(facultyId);
    }

    public List<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }
}