package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Creating student: {}", student.getName());
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Getting student by id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student) {
        logger.info("Updating student with id: {}", id);
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(student.getName());
                    existingStudent.setAge(student.getAge());
                    return studentRepository.save(existingStudent);
                })
                .orElse(null);
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: {}", id);
        studentRepository.deleteById(id);
    }

    public List<Student> getAllStudents() {
        logger.info("Getting all students");
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByAge(int age) {
        logger.info("Getting students by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Getting students by age between {} and {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Object getFacultyByStudentId(Long studentId) {
        logger.info("Getting faculty for student id: {}", studentId);
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Long getStudentsCount() {
        logger.info("Getting students count");
        return studentRepository.getStudentsCount();
    }

    public Double getAverageAge() {
        logger.info("Getting average age of students");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Getting last five students");
        return studentRepository.findLastFiveStudents();
    }

    public long calculateSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
    }

    public long calculateSumOptimized() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public void printStudentsParallel() {
        List<Student> students = getAllStudents();
        if (students.size() < 6) {
            logger.warn("Need at least 6 students for parallel printing");
            return;
        }

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread was interrupted", e);
        }
    }

    public void printStudentsSynchronized() {
        List<Student> students = getAllStudents();
        if (students.size() < 6) {
            logger.warn("Need at least 6 students for synchronized printing");
            return;
        }

        printStudentNameSync(students.get(0).getName());
        printStudentNameSync(students.get(1).getName());

        Thread thread1 = new Thread(() -> {
            printStudentNameSync(students.get(2).getName());
            printStudentNameSync(students.get(3).getName());
        });

        Thread thread2 = new Thread(() -> {
            printStudentNameSync(students.get(4).getName());
            printStudentNameSync(students.get(5).getName());
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread was interrupted", e);
        }
    }

    private synchronized void printStudentNameSync(String name) {
        System.out.println(name);
    }
}