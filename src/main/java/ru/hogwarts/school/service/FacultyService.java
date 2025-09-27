package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        Faculty existingFaculty = getFaculty(id);
        if (existingFaculty == null) {
            return null;
        }
        existingFaculty.setName(faculty.getName());
        existingFaculty.setColor(faculty.getColor());
        return facultyRepository.save(existingFaculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> searchFaculties(String name, String color) {
        if (name != null && color != null) {
            return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        } else if (name != null) {
            return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, "");
        } else if (color != null) {
            return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("", color);
        }
        return facultyRepository.findAll();
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public String getLongestFacultyName() {
        Optional<Faculty> facultyWithLongestName = facultyRepository.findAll().stream()
                .max(Comparator.comparingInt(f -> f.getName().length()));
        return facultyWithLongestName.map(Faculty::getName).orElse("");
    }
}