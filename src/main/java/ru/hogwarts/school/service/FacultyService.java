package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long counter = 1;

    public Faculty create(Faculty faculty) {
        faculty.setId(counter++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty read(long id) {
        return faculties.get(id);
    }

    public Faculty update(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    public Faculty delete(long id) {
        return faculties.remove(id);
    }

    public List<Faculty> getByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public List<Faculty> getAll() {
        return new ArrayList<>(faculties.values());
    }
}