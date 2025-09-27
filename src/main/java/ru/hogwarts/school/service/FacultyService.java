package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> findFacultiesByNameOrColor(String name, String color) {
        if (name != null && color != null) {
            return facultyRepository.findByNameContainingIgnoreCaseAndColorContainingIgnoreCase(name, color);
        } else if (name != null) {
            return facultyRepository.findByNameContainingIgnoreCase(name);
        } else if (color != null) {
            return facultyRepository.findByColorContainingIgnoreCase(color);
        } else {
            return facultyRepository.findAll();
        }
    }

    public Faculty findFaculty(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.orElse(null);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty faculty) {
        Optional<Faculty> existingFaculty = facultyRepository.findById(id);
        if (existingFaculty.isPresent()) {
            faculty.setId(id);
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Object searchFaculties(String gryffindor, String red) {
        return gryffindor;
    }
}