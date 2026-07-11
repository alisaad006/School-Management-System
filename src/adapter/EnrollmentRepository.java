package adapter;

import domain.Enrollment;
import java.util.List;

public interface EnrollmentRepository {
    
    void enroll(int studentId, int courseId);
    
    void unenroll(int studentId, int courseId);
    
    boolean isEnrolled(int studentId, int courseId);
    
    List<Enrollment> findEnrollmentsByStudent(int studentId);
    
    List<Enrollment> findEnrollmentsByCourse(int courseId);
    
    void deleteByStudent(int studentId);
    
    void deleteByCourse(int courseId);
    
}
