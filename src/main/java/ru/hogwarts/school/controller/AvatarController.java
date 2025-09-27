package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping
    public Avatar uploadAvatar(@RequestBody Avatar avatar) {
        return avatarService.saveAvatar(avatar);
    }

    @GetMapping("/student/{studentId}")
    public Avatar getAvatarByStudentId(@PathVariable Long studentId) {
        return avatarService.getAvatarByStudentId(studentId).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteAvatar(@PathVariable Long id) {
        avatarService.deleteAvatar(id);
    }
}