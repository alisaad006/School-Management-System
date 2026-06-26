package adapter;

import domain.Course;
import java.util.List;

public interface CourseRepository {

    void save(Course course);
    
    void update(Course course);
    
    void delete(int id);

    Course findByID(int id);

    List<Course> findAll();
}
