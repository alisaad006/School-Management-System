package domain;

import domain.exceptoins.ValidationException;
import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    
    private int studentId;
    private int courseId;
    private LocalDate enrollDate;

    public Enrollment(int studentId, int courseId, LocalDate enrollDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollDate = enrollDate != null ? enrollDate : LocalDate.now();
        validate();
    }
    
    private void validate() {
        if (studentId <= 0) {
            throw new ValidationException("Student ID", studentId, "Must be greater than 0.");
        }
        if (courseId <= 0) {
            throw new ValidationException("Course ID", courseId, "Must be greater than 0.");
        }
        if (enrollDate == null) {
            throw new ValidationException("Enroll Date", null, "Cannot be null.");
        }
        if (enrollDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Enroll Date", enrollDate, "Cannot be in the future.");
        }
    }

    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public LocalDate getEnrollDate() { return enrollDate; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment e = (Enrollment) o;
        return studentId == e.studentId && courseId == e.courseId;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
    
    @Override
    public String toString() {
        return "Enrollment ["
                + "\n  Student ID = " + studentId
                + "\n  Course ID  = " + courseId
                + "\n  Date       = " + enrollDate
                + "\n]";
    }
    
}
