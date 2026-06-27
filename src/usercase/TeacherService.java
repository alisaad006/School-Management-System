package usercase;

import adapter.TeacherRepository;
import domain.Teacher;
import java.util.List;

public class TeacherService {
    
    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }
    
    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            System.out.println("Error: Cannot add null teacher.");
            return;
        }
        if (repo.findById(teacher.getId()) != null) {
            System.out.println("Error: Teacher with ID " + teacher.getId() + " already exist.");
            return;
        }
        repo.save(teacher);
    }
    
    public void updateTeacher(Teacher teacher) {
        if (teacher == null) {
            System.out.println("Error: Cannot Update null teacher.");
            return;
        }
        repo.update(teacher);
    }
    
    public void deleteTeacher(int id) {
        repo.delete(id);
    }
    
    public List<Teacher> listTeacher() {
        return repo.findAll();
    }
    
    public Teacher findTeacher(int id) {
        Teacher teacher = repo.findById(id);
        if (teacher == null) {
            System.out.println("Teacher by ID " + id + " not found.");
        }
        return teacher;
    }
    
}
