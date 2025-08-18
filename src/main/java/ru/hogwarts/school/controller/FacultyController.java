package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<Faculty>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(facultyService.findByName(name));
    }

    @GetMapping("/by-color")
    public ResponseEntity<List<Faculty>> getByColor(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.findByColor(color));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {

        if (name != null && color != null) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        } else if (name != null) {
            return ResponseEntity.ok(facultyService.searchByName(name));
        } else if (color != null) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.badRequest().build();
    }
}