package domain;

public class Grade {
    
    private int greadeId;
    private int studentId;
    private int courseId;
    private double score;
    private String latter;

    public Grade(int greadeId, int studentId, int courseId, double score, String latter) {
        this.greadeId = greadeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.latter = latter;
    }
    
    public String calcLetter() {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public int getGreadeId() {
        return greadeId;
    }

    public void setGreadeId(int greadeId) {
        this.greadeId = greadeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLatter() {
        return latter;
    }

    public void setLatter(String latter) {
        this.latter = latter;
    }
    
    public String getInfo () {
        return "Grade: [ Grade ID: " + greadeId 
                + "\nStudent ID: " + studentId
                + "\nCourse ID: " + courseId
                + "\nScore: " + score
                + "\nLetter: " + latter + " ]";
    }
    
}
