package adapter;

import domain.*;
import java.util.List;

public interface GradeRepository {
    
    void save(Grade grade);
    
    void update(Grade grade);
    
    void delete(int id);
    
    Grade findById(int id);
    
    List<Grade> findByStudent(int studentId);
    
    List<Grade> findByCourse(int courseId);
    
}
