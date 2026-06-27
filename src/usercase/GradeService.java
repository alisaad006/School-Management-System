package usercase;

import adapter.GradeRepository;
import domain.Grade;
import domain.Student;
import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private final GradeRepository repo;

    public GradeService(GradeRepository repo) {
        this.repo = repo;
    }

    public void addGrade(Grade grade) {
        if (grade == null) {
            System.out.println("Error: Cannot add null grade.");
            return;
        }
        repo.save(grade);
    }

    public void updateGrade(Grade grade) {
        if (grade == null) {
            System.out.println("Error: Cannot Update null Grade");
            return;
        }
        repo.update(grade);
    }

    public void deleteGrade(int id) {
        repo.delete(id);
    }

    public List<Grade> getStudentGrade(int studentId) {
        List<Grade> grades = repo.findByStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades found for student ID " + studentId);
        }
        return grades;
    }
    
    public List<Grade> getCourseGrade(int courseId) {
        List<Grade> grade = repo.findByCourse(courseId);
        if (grade.isEmpty()) {
            System.out.println("No grades found for course ID: " + courseId);
        }
        return grade;
    }
    
    public double calcAverage(int studentId) {
        List<Grade> grades = repo.findByStudent(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades found for student ID " + studentId);
            return 0.0;
        }
        double total = 0.0;
        for (Grade g : grades) {
            total += g.getScore();
        }
        return total / grades.size();
    }
    
    public Grade findGrade(int gradeId) {
        return repo.findById(gradeId);
    }

}
