package domain;

import domain.exceptoins.*;
import java.util.Objects;

public class Course {

    private int courseId;
    private String title;
    private int teacherId;
    private int maxStudents;
    private int enrolledCount;

    public Course() {
    }

    public Course(int courseId, String title, int teacherId, int maxStudents) {
        this.courseId = courseId;
        this.title = title;
        this.teacherId = teacherId;
        this.maxStudents = maxStudents;
        this.enrolledCount = 0;
        validate();
    }

    public void validate() {
        validateCourseId();
        validateTitle();
        validateTeacherId();
        validateMaxStudents();
    }

    private void validateCourseId() {
        if (courseId <= 0) {
            throw new ValidationException("Course ID ", courseId, "Must be greater than 0.");
        }
    }

    private void validateTitle() {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Title ", title, "Cannot be empty.");
        }
        if (title.length() > 100) {
            throw new ValidationException("Title ", title, "Exceeds maximum length of 100 characters.");
        }
        this.title = title.trim();
    }

    private void validateTeacherId() {
        if (teacherId <= 0) {
            throw new ValidationException("Teacher ID ", teacherId, "Must be greater than 0.");
        }
    }

    private void validateMaxStudents() {
        if (maxStudents <= 0) {
            throw new ValidationException("Max Students", maxStudents, "Must be greater than 0.");
        }
    }

    // check if course is full
    public boolean isFull() {
        return enrolledCount >= maxStudents;
    }

    public void incrementEnrolled() {
        if (isFull()) {
            throw new IllegalStateException("Course '" + title + "' is already full.");
        }
        enrolledCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course c = (Course) o;
        return courseId == c.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    public String getInfo() {
        return "Course ["
                + "\n  ID           = " + courseId
                + "\n  Title        = " + title
                + "\n  Teacher ID   = " + teacherId
                + "\n  Max Students = " + maxStudents
                + "\n  Enrolled     = " + enrolledCount + " / " + maxStudents
                + "\n]";
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Title", title, "Cannot be empty.");
        }
        if (title.trim().length() > 100) {
            throw new ValidationException("Title", title, "Exceeds maximum of 100 characters.");
        }
        this.title = title.trim();
    }

    public void setTeacherId(int teacherId) {
        if (teacherId <= 0) {
            throw new ValidationException("Teacher ID", teacherId, "Must be greater than 0.");
        }
        this.teacherId = teacherId;
    }

    public void setMaxStudents(int maxStudents) {
        if (maxStudents <= 0) {
            throw new ValidationException("Max Students", maxStudents, "Must be greater than 0.");
        }
        if (maxStudents < enrolledCount) {
            throw new ValidationException("Max Students", maxStudents,
                    "Cannot be less than current enrollment count (" + enrolledCount + ").");
        }
        this.maxStudents = maxStudents;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

}
