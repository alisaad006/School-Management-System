package adapter;

import domain.Teacher;
import java.util.List;

public interface TeacherRepository {
    
    void save(Teacher teacher);
    
    void update(Teacher teacher);
    
    void delete(int id);
    
    Teacher findById(int id);
    
    List<Teacher> findAll();
    
}
