package infrastructure;

import adapter.GradeRepository;
import domain.Grade;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryGradeRepo implements GradeRepository {

    private final Map<Integer, Grade> store = new HashMap<>();

    @Override
    public void save(Grade grade) {
        store.put(grade.getGreadeId(), grade);
    }

    @Override
    public void update(Grade grade) {
        if (!store.containsKey(grade.getGreadeId())) {
            System.out.println("Grade with ID " + grade.getGreadeId() + " does not exist.");
        }
        store.put(grade.getGreadeId(), grade);

    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            System.out.println("grade with ID " + id + " does no exist.");
        }
        store.remove(id);

    }

    @Override
    public List<Grade> findByStudent(int studentId) {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Grade> findByCourse(int courseId) {
        return new ArrayList<>(store.values());
    }

    @Override
    public Grade findById(int id) {
        return store.get(id);
    }

}
