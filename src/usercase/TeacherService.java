package usercase;

import adapter.TeacherRepository;
import domain.Teacher;
import domain.exceptoins.*;
import java.util.List;
import java.util.logging.Logger;

public class TeacherService {

    private final TeacherRepository repo;
    private static final Logger LOGGER = Logger.getLogger(TeacherService.class.getName());

    public TeacherService(TeacherRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("TeacherRepository cannot be null.");
        }
        this.repo = repo;
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null.");
        }
        if (repo.findById(teacher.getId()) != null) {
            throw new DuplicateEntityException("Teacher", teacher.getId());
        }
        repo.save(teacher);
        LOGGER.info("Teacher added: ID=" + teacher.getId() + ", Name=" + teacher.getName());
    }

    public void updateTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null.");
        }
        if (repo.findById(teacher.getId()) == null) {
            throw new EntityNotFoundException("Teacher", teacher.getId());
        }
        repo.update(teacher);
        LOGGER.info("Teacher updated: ID=" + teacher.getId());
    }

    public void deleteTeacher(int id) {
        if (repo.findById(id) == null) {
            throw new EntityNotFoundException("Teacher", id);
        }
        repo.delete(id);
        LOGGER.info("Teacher deleted: ID=" + id);
    }

    public List<Teacher> listTeacher() {
        return repo.findAll();
    }

    public Teacher findTeacher(int id) {
        Teacher t = repo.findById(id);
        if (t == null) {
            throw new EntityNotFoundException("Teacher", id);
        }
        return t;
    }

}
