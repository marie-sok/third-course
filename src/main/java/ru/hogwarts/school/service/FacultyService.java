package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.List;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method for create faculty: {}", faculty.getName());
        Faculty createdFaculty = facultyRepository.save(faculty);
        logger.debug("Faculty created successfully: {}", createdFaculty);
        return createdFaculty;
    }

    public Faculty read(long id) {
        logger.info("Was invoked method for read faculty with id: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method for update faculty with id: {}", faculty.getId());
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.error("Cannot update faculty: faculty with id {} not found", faculty.getId());
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public Faculty delete(long id) {
        logger.info("Was invoked method for delete faculty with id: {}", id);
        Faculty faculty = read(id);
        if (faculty != null) {
            facultyRepository.deleteById(id);
            logger.debug("Faculty deleted successfully: {}", faculty);
        } else {
            logger.warn("Cannot delete faculty: faculty with id {} not found", id);
        }
        return faculty;
    }

    public List<Faculty> getByColor(String color) {
        logger.info("Was invoked method for get faculties by color: {}", color);
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getByName(String name) {
        logger.info("Was invoked method for get faculties by name: {}", name);
        return facultyRepository.findByName(name);
    }

    public List<Faculty> findByNameOrColor(String nameOrColor) {
        logger.info("Was invoked method for search faculties by name or color: {}", nameOrColor);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public List<Faculty> getAll() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }
}