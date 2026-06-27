package infrastructure;

import adapter.TeacherRepository;
import domain.Teacher;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryTeacherRepository implements TeacherRepository {

    private final Map<Integer, Teacher> store = new HashMap<>();

    @Override
    public void save(Teacher teacher) {
        store.put(teacher.getId(), teacher);
    }

    @Override
    public void update(Teacher teacher) {
        if (!store.containsKey(teacher.getId())) {
            System.out.println("Teacher with ID " + teacher.getId() + " does not exist.");
        }
        store.put(teacher.getId(), teacher);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            System.out.println("Teacher with ID " + id + " does no exist.");
        }
        store.remove(id);
    }

    @Override
    public Teacher findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(store.values());
    }

}
