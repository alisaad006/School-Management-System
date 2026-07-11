package infrastructure.repository;

import adapter.TeacherRepository;
import domain.Teacher;
import domain.exceptoins.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryTeacher implements TeacherRepository {

    private final Map<Integer, Teacher> store = new HashMap<>();

    @Override
    public void save(Teacher teacher) {
        store.put(teacher.getId(), teacher);
    }

    @Override
    public void update(Teacher teacher) {
        if (!store.containsKey(teacher.getId())) {
            throw new EntityNotFoundException("Teacher", teacher.getId());
        }
        store.put(teacher.getId(), teacher);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new EntityNotFoundException("Teacher", id);
        }
        store.remove(id);
    }

    @Override
    public Teacher findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Teacher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

}
