package infrastructure;

import adapter.StudentRepository;
import domain.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryStudentRepository implements StudentRepository{
    
    private final Map<Integer, Student> store = new HashMap<>();

    @Override
    public void save(Student student) {
        store.put(student.getId(), student);
    }

    @Override
    public void update(Student student) {
        if (!store.containsKey(student.getId())) {
            System.out.println("Student with ID " + student.getId() + " does not exist.");
        }
        store.put(student.getId(), student);
    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            System.out.println("Student with ID " + id + " does not exist.");
        }
        store.remove(id);
    }

    @Override
    public Student findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }
    
}
