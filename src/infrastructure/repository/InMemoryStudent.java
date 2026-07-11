package infrastructure.repository;

import adapter.StudentRepository;
import domain.Student;
import domain.exceptoins.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryStudent implements StudentRepository {

    private final Map<Integer, Student> store = new HashMap<>();

    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        if (!store.containsKey(student.getId())) {
            throw new EntityNotFoundException("Student", student.getId());
        }
        store.put(student.getId(), student);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new EntityNotFoundException("Student", id);
        }
        store.remove(id);
    }

    @Override
    public Student findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Student> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

}
