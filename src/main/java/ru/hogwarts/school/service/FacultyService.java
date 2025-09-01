package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty read(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty delete(long id) {
        Faculty faculty = read(id);
        if (faculty != null) {
            facultyRepository.deleteById(id);
        }
        return faculty;
    }

    public List<Faculty> getByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getByName(String name) {
        return facultyRepository.findByName(name);
    }

    public List<Faculty> findByNameOrColor(String nameOrColor) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }
}