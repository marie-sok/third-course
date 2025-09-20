package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Faculties", description = "API for faculty management")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @Operation(summary = "Create faculty")
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.create(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get faculty for ID")
    public ResponseEntity<Faculty> read(@PathVariable long id) {
        Faculty faculty = facultyService.read(id);
        return faculty != null
                ? ResponseEntity.ok(faculty)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    @Operation(summary = "Update faculty")
    public ResponseEntity<Faculty> update(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.update(faculty);
        return updatedFaculty != null
                ? ResponseEntity.ok(updatedFaculty)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete faculty")
    public ResponseEntity<Faculty> delete(@PathVariable long id) {
        Faculty faculty = facultyService.delete(id);
        return faculty != null
                ? ResponseEntity.ok(faculty)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/by-color")
    @Operation(summary = "Search faculty be the color")
    public List<Faculty> getByColor(@RequestParam String color) {
        return facultyService.getByColor(color);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Search faculty by the name")
    public List<Faculty> getByName(@RequestParam String name) {
        return facultyService.getByName(name);
    }

    @GetMapping("/search")
    @Operation(summary = "Search faculty by the name or color")
    public List<Faculty> searchByNameOrColor(@RequestParam String nameOrColor) {
        return facultyService.findByNameOrColor(nameOrColor);
    }

    @GetMapping
    @Operation(summary = "Get all faculties")
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }
}