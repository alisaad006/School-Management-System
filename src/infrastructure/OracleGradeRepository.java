package infrastructure;

import adapter.GradeRepository;
import domain.Grade;
import java.util.*;
import java.sql.*;

public class OracleGradeRepository implements GradeRepository {

    private final Connection con;

    public OracleGradeRepository(Connection con) {
        this.con = con;
    }

    // A D D
    @Override
    public void save(Grade grade) {

        String query = "INSERT INTO Grades (grade_id, student_id, course_id, score, letter) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, grade.getGreadeId());
            pre.setInt(2, grade.getStudentId());
            pre.setInt(3, grade.getCourseId());
            pre.setDouble(4, grade.getScore());
            pre.setString(5, grade.getLatter());

            pre.executeUpdate();
            System.out.println("Grade Inserted Successfully.");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

    }
    
    // UPDATE
    @Override
    public void update(Grade grade) {
        String query = "update grade set studnet_id = ?, course_id = ?, score = ?, letter = ? where grade_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, grade.getStudentId());
            pre.setInt(2, grade.getCourseId());
            pre.setDouble(3, grade.getScore());
            pre.setString(4, grade.getLatter());
            pre.setInt(5, grade.getGreadeId());

            int row = pre.executeUpdate();

            if (row > 0) {
                System.out.println("Grade data updated successfully");
            } else {
                System.out.println("No Grade found with ID " + grade.getGreadeId() + " to update.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error during updataing: " + e.getMessage());
        }

    }
    
    // Delete
    @Override
    public void delete(int id) {
        String query = "Delete form grade where grade_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);

            pre.setInt(1, id);
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Grade with ID " + id + " Delete successfuly.");
            } else {
                System.out.println("No Grade found with ID " + id + " to Delete.");
            }

        } catch (SQLException e) {
            System.out.println("Database Error during Delete: " + e.getMessage());
        }
    }
    
        @Override
    public Grade findById(int id) {
           String query = "SELECT * FROM grades WHERE grade_id = ?";
    try (PreparedStatement pre = con.prepareStatement(query)) {
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            return new Grade(
                rs.getInt("grade_id"),
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                rs.getDouble("score"),
                rs.getString("letter")
            );
        }
    } catch (SQLException e) {
        System.out.println("Error fetching grade by ID: " + e.getMessage());
    }
    return null;  // not found
    }

    @Override
    public List<Grade> findByStudent(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT grade_id, student_id, course_id, score, letter FROM grades WHERE student_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade g = new Grade(
                    rs.getInt("grade_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getDouble("score"),
                    rs.getString("letter")
                );
                grades.add(g);
            }
        } catch (SQLException e) {
            System.out.println("Database error (findByStudent): " + e.getMessage());
        }
        return grades;
    }

    @Override
    public List<Grade> findByCourse(int courseId) {
        List<Grade> grades = new ArrayList<>();
        String query = "select * from grades where course_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, courseId);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Grade g = new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("Student_id"),
                        rs.getInt("course_id"),
                        rs.getDouble("score"),
                        rs.getString("latter"));
                grades.add(g);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching grades for Course ID " + courseId + ": " + e.getMessage());
        }
        return grades;

    }



}
