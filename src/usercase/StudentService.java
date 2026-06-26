package usercase;

import domain.Student;
import adapter.StudentRepository;
import java.util.List;

public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public void addStudent(Student student) {
        if (student == null) {
            System.out.println("Error: Cannot add a null student.");
            return;
        }
        if (repo.findById(student.getId()) != null) {
            System.out.println("Error: Student with ID " + student.getId() + " already exists.");
            return;
        }
        repo.save(student);
    }

    public void updateStudent(Student student) {
        if (student == null) {
            System.out.println("Error: Cannot Update a null Student.");
            return;
        }
        repo.update(student);
    }

    public void deleteStudent(int id) {
        repo.delete(id);
    }

    public List<Student> listStudent() {
        return repo.findAll();
    }
    
    public Student findStudent(int id) {
        Student s = repo.findById(id);
        if (s == null) {
            System.out.println("Student with ID " + id + " not found.");
        }
        return s;
    }

}
