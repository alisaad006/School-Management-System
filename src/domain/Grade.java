package domain;

import domain.exceptoins.ValidationException;
import java.util.Objects;

public class Grade {
    
    private int    gradeId;
    private int    studentId;
    private int    courseId;
    private double score;
    private String letter;

    public Grade(int greadeId, int studentId, int courseId, double score, String latter) {
        this.gradeId = greadeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.letter = calcLetter();
        validate();
    }

    public Grade(int gradeId, int studentId, int courseId, double score) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }
    
    
    
        public void validate() {
        validateGradeId();
        validateStudentId();
        validateCourseId();
        validateScore();
    }

    private void validateGradeId() {
        if (gradeId <= 0) {
            throw new ValidationException("Grade ID", gradeId, "Must be greater than 0.");
        }
    }

    private void validateStudentId() {
        if (studentId <= 0) {
            throw new ValidationException("Student ID", studentId, "Must be greater than 0.");
        }
    }

    private void validateCourseId() {
        if (courseId <= 0) {
            throw new ValidationException("Course ID", courseId, "Must be greater than 0.");
        }
    }

    private void validateScore() {
        if (score < 0 || score > 100) {
            throw new ValidationException("Score", score, "Must be between 0 and 100.");
        }
    }
    
    public String calcLetter() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }



    public int    getGradeId()   { return gradeId; }
    public int    getStudentId() { return studentId; }
    public int    getCourseId()  { return courseId; }
    public double getScore()     { return score; }
    public String getLetter()    { return letter; }

    public void setScore(double score) {
        if (score < 0 || score > 100) {
            throw new ValidationException("Score", score, "Must be between 0 and 100.");
        }
        this.score = score;
        this.calcLetter();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade)) return false;
        Grade g = (Grade) o;
        return gradeId == g.gradeId;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(gradeId);
    }
    
    public String getInfo() {
        return "Grade ["
                + "\n  Grade ID   = " + gradeId
                + "\n  Student ID = " + studentId
                + "\n  Course ID  = " + courseId
                + "\n  Score      = " + score
                + "\n  Letter     = " + letter
                + "\n]";
    }
 
    @Override
    public String toString() {
        return getInfo();
    }

    
}
