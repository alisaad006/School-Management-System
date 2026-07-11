package infrastructure.repository;

import adapter.GradeRepository;
import domain.Grade;
import domain.exceptoins.EntityNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InMemoryGrade implements GradeRepository {

    private final Map<Integer, Grade> store = new HashMap<>();

    @Override
    public void save(Grade grade) {
        store.put(grade.getGradeId(), grade);
    }

    @Override
    public void update(Grade grade) {
        if (!store.containsKey(grade.getGradeId())) {
            throw new EntityNotFoundException("Grade", grade.getGradeId());
        }
        store.put(grade.getGradeId(), grade);

    }

    @Override
    public void delete(int id) {
        if (!store.containsKey(id)) {
            throw new EntityNotFoundException("Grade", id);
        }
        store.remove(id);
    }

    @Override
    public Grade findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Grade> findByStudent(int studentId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : store.values()) {
            if (g.getStudentId() == studentId) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public List<Grade> findByCourse(int courseId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : store.values()) {
            if (g.getCourseId() == courseId) {
                result.add(g);
            }
        }
        return result;
    }

}
