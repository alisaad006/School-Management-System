package domain;

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
    }

    // check if course is full
    public boolean isFull() {
        return enrolledCount >= maxStudents;
    }
    
    public void incrementEnrolled() {
        enrolledCount++;
    }
    
    

    public String getInfo() {
        return "Course [ Id =" + courseId
                + "\nTitle =" + title
                + "\nTeacher Id =" + teacherId
                + "\nMax Students =" + maxStudents
                + "\nSeats =" + enrolledCount + "]";
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(int enrolledCount) {
        this.enrolledCount = enrolledCount;
    }
    

}
