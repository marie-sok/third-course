package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByNameIgnoreCase(String name);
    List<Faculty> findByColorIgnoreCase(String color);
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
    List<Faculty> findByNameContainingIgnoreCase(String part);
    List<Faculty> findByColorContainingIgnoreCase(String part);
    List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String namePart, String colorPart);
}