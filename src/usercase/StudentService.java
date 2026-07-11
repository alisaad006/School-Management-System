package usercase;

import domain.Student;
import adapter.StudentRepository;
import domain.exceptoins.*;
import infrastructure.repository.OracleStudent;
import java.util.List;
import java.util.logging.Logger;
import sun.util.logging.resources.logging;

public class StudentService {

    private final StudentRepository repo;
    private static final Logger LOGGER = Logger.getLogger(StudentService.class.getName());

    public StudentService(StudentRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("StudentRepository cannot be null.");
        }
        this.repo = repo;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (repo.findById(student.getId()) != null) {
            throw new DuplicateEntityException("Student", student.getId());
        }
        repo.save(student);
        LOGGER.info("Student added: ID= " + student.getId() + ", Name=" + student.getName());

    }

    public void updateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        if (repo.findById(student.getId()) == null) {
            throw new EntityNotFoundException("Student", student.getId());
        }
        repo.update(student);
        LOGGER.info("Student updated: ID= " + student.getId());
    }

    public void deleteStudent(int id) {

        if (repo.findById(id) == null) {
            throw new EntityNotFoundException("Student", id);
        }
        repo.delete(id);
        LOGGER.info("Student with ID " + id + " deleted successfully.");
    }

    public List<Student> listStudent() {
        return repo.findAll();
    }

    public Student findStudent(int id) {
        Student s = repo.findById(id);
        if (s == null) {
            throw new EntityNotFoundException("Student", id);
        }
        return s;
    }

}
