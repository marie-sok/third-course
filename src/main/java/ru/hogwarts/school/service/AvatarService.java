package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import java.util.Optional;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar saveAvatar(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public Optional<Avatar> getAvatarByStudentId(Long studentId) {
        return avatarRepository.findByStudentId(studentId);
    }

    public void deleteAvatar(Long id) {
        avatarRepository.deleteById(id);
    }
}