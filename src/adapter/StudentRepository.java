package adapter;

import domain.Student;
import java.util.*;

public interface StudentRepository {
    
    void save(Student student);
    
    void update(Student student);
    
    void delete(int id);
    
    Student findById(int id);
    
    List<Student> findAll();
}
