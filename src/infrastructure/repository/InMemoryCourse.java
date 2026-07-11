package infrastructure.repository;

import adapter.CourseRepository;
import domain.Course;
import domain.exceptoins.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCourse implements CourseRepository {

    private final Map<Integer, Course> store = new HashMap<>();

    @Override
    public void save(Course course) {
        store.put(course.getCourseId(), course);
    }

    @Override
    public void update(Course course) {
        if (!store.containsKey(course.getCourseId())) {
            throw new EntityNotFoundException("Course", course.getCourseId());
        }
        store.put(course.getCourseId(), course);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new EntityNotFoundException("Course", id);
        }
        store.remove(id);
    }

    @Override
    public Course findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Course> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

}
