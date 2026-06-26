package infrastructure;

import adapter.CourseRepository;
import domain.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleCourseRepository implements CourseRepository{

    
    private final Connection con;

    public OracleCourseRepository(Connection con) {
        this.con = con;
    }
    
    // A D D
    @Override
    public void save(Course course) {
        String sql = "INSERT INTO course (course_id, title, teacher_id, max_students) VALUES (?, ?, ?, ?)";
        
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setInt(1, course.getCourseId());
            pre.setString(2, course.getTitle());
            pre.setInt(3, course.getTeacherId());
            pre.setInt(4, course.getMaxStudents());
            
            pre.executeUpdate();
            System.out.println("Course Inserted Successfully.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // U P D A T E
    @Override
    public void update(Course course) {
        String sql = "update course set title = ?, teacher_id = ?, max_students = ? where course_id = ?";
        
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, course.getTitle());
            pre.setInt(2, course.getTeacherId());
            pre.setInt(3, course.getMaxStudents());
            pre.setInt(4, course.getCourseId());
            
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Course data updated successfully");
            } else {
                                System.out.println("No Course found with ID " + course.getCourseId()+ " to update.");
            }
            
        } catch (SQLException e) {
                        System.out.println("Database Error during update: " + e.getMessage());
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {
        String sql = "delete from course where course_id = ?";
        
        try {
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setInt(1, id);
        
        int row = pre.executeUpdate();
        if (row > 0) {
                         System.out.println("Course with ID " + id + " deleted successfully.");   
        } else {
                            System.out.println("No course found with ID " + id + " to delete.");
        }
            
        } catch (SQLException e) {
                        System.out.println("Database Error during delete: " + e.getMessage());
        }
    }

    // S E A R C H
    @Override
    public Course findByID(int id) {
        String sql = "select course_id, title, teacher_id, max_students from course where course_id = ?";
        
        try {
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setInt(1, id);
        try (ResultSet rs = pre.executeQuery()) {
            if (rs.next()) {
                return new Course(
                        rs.getInt("course_id"),
                        rs.getString("title"),
                        rs.getInt("teacher_id"),
                        rs.getInt("max_students"));
            }
        }
        } catch (SQLException e) {
                        System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "select course_id, title, teacher_id, max_students from course";
        
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("title"),
                        rs.getInt("teacher_id"),
                        rs.getInt("max_students"));
                courses.add(course);
            }
            
        } catch (SQLException e) {
                        System.out.println("Database Error: " + e.getMessage());
        }
        return courses;
    }
    
    
    
}
