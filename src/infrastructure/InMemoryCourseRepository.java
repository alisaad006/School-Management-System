package infrastructure;

import adapter.CourseRepository;
import domain.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCourseRepository implements CourseRepository{
    
    private final Map<Integer, Course> store = new HashMap<>();

    @Override
    public void save(Course course) {
        store.put(course.getCourseId(), course);
    }

    @Override
    public void update(Course course) {
        if (!store.containsKey(course.getCourseId())) {
            throw new IllegalArgumentException("Course with ID " + course.getCourseId() + " does not exist.");
        }
        store.put(course.getCourseId(), course);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new IllegalArgumentException("Course with ID " + id + " does not exist.");
        }
        store.remove(id);
    }

    @Override
    public Course findByID(int id) {
        return store.get(id);
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(store.values());
    }
    
}
