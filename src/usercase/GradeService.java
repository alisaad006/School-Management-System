package usercase;

import adapter.GradeRepository;
import domain.Grade;
import domain.Student;
import domain.exceptoins.DuplicateEntityException;
import domain.exceptoins.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GradeService {

    private final GradeRepository repo;
    private static final Logger LOGGER = Logger.getLogger(GradeService.class.getName());

    public GradeService(GradeRepository repo) {
        if (repo == null) {
            throw new IllegalArgumentException("GradeRepository Cannot be null.");
        }
        this.repo = repo;
    }

    public void addGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade Cannot be null.");
        }
        if (repo.findById(grade.getGradeId()) != null) {
            throw new DuplicateEntityException("Grade", grade.getGradeId());
        }
        repo.save(grade);
        LOGGER.info("Grade added: ID=" + grade.getGradeId()
                + ", StudentID=" + grade.getStudentId()
                + ", Score=" + grade.getScore()
                + ", Letter=" + grade.getLetter());
    }

    public void updateGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }
        if (repo.findById(grade.getGradeId()) != null) {
            throw new EntityNotFoundException("Grade", grade.getGradeId());
        }
        repo.update(grade);
        LOGGER.info("Grade updated: ID=" + grade.getGradeId()
                + ", Score=" + grade.getScore()
                + ", Letter=" + grade.getLetter());
    }

    public void deleteGrade(int id) {
        if (repo.findById(id) == null) {
            throw new EntityNotFoundException("Grade", id);
        }
        repo.delete(id);
        LOGGER.info("Grade deleted: ID=" + id);
    }

    public List<Grade> getStudentGrade(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }
        List<Grade> grades = repo.findByStudent(studentId);
        if (grades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for student ID " + studentId);
        }
        return grades;
    }

    public List<Grade> getCourseGrade(int courseId) {
        if (courseId <= 0) {
            throw new IllegalArgumentException("Course ID must be greater than 0.");
        }
        List<Grade> grades = repo.findByCourse(courseId);
        if (grades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for course ID " + courseId);
        }
        return grades;
    }

    public double calcAverage(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }
        List<Grade> grades = repo.findByStudent(studentId);
        if (grades.isEmpty()) {
            throw new EntityNotFoundException("No grades found for student ID " + studentId);
        }
        double total = 0.0;
        for (Grade g : grades) {
            total += g.getScore();
        }
        return total / grades.size();
    }

    public Grade findGrade(int gradeId) {
        if (gradeId <= 0) {
            throw new IllegalArgumentException("Grade ID must be greater than 0.");
        }
        Grade g = repo.findById(gradeId);
        if (g == null) {
            throw new EntityNotFoundException("Grade", gradeId);
        }
        return g;
    }

}
