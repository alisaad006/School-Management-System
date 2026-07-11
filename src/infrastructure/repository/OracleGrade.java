package infrastructure.repository;

import adapter.GradeRepository;
import domain.Grade;
import domain.exceptoins.*;
import java.util.*;
import java.sql.*;
import java.util.logging.*;

public class OracleGrade implements GradeRepository {

    private final Connection con;
    private static final Logger LOGGER = Logger.getLogger(OracleGrade.class.getName());

    public OracleGrade(Connection con) {
        if (con == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.con = con;
    }

    // A D D
    @Override
    public void save(Grade grade) {

        String query = "INSERT INTO Grades (grade_id, student_id, course_id, score, letter) VALUES (?,?,?,?,?)";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, grade.getGradeId());
            pre.setInt(2, grade.getStudentId());
            pre.setInt(3, grade.getCourseId());
            pre.setDouble(4, grade.getScore());
            pre.setString(5, grade.getLetter());
            pre.executeUpdate();
            LOGGER.info("Grade inserted: ID=" + grade.getGradeId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting grade ID=" + grade.getGradeId(), e);
            throw new DatabaseException("Failed to insert grade with ID " + grade.getGradeId(), e);
        }

    }

    // UPDATE
    @Override
    public void update(Grade grade) {
        String query = "UPDATE grades SET student_id = ?, course_id = ?, score = ?, letter = ? where grade_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query);) {
            pre.setDouble(1, grade.getScore());
            pre.setString(2, grade.getLetter());
            pre.setInt(3, grade.getGradeId());

            int row = pre.executeUpdate();
            if (row == 0) {
                throw new EntityNotFoundException("Grade", grade.getGradeId());
            }
            LOGGER.info("Grade Update: ID = " + grade.getGradeId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error update grade ID = " + grade.getGradeId(), e);
            throw new DatabaseException("Failed to update grade with ID " + grade.getGradeId(), e);
        }

    }

    // Delete
    @Override
    public void delete(int id) {
        String query = "DELETE FROM grades WHERE grade_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, id);
            int rows = pre.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("Grade", id);
            }
            LOGGER.info("Grade deleted: ID=" + id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting grade ID=" + id, e);
            throw new DatabaseException("Failed to delete grade with ID " + id, e);
        }
    }

    @Override
    public Grade findById(int id) {
        String query = "SELECT * FROM grades WHERE grade_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding grade ID=" + id, e);
            throw new DatabaseException("Failed to find grade with ID " + id, e);
        }
        return null;
    }

    @Override
    public List<Grade> findByStudent(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT grade_id, student_id, course_id, score, letter FROM grades WHERE student_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, studentId);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    grades.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding grades for student ID=" + studentId, e);
            throw new DatabaseException("Failed to find grades for student ID " + studentId, e);
        }
        return grades;
    }

    @Override
    public List<Grade> findByCourse(int courseId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT grade_id, student_id, course_id, score, letter FROM grades WHERE course_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, courseId);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    grades.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding grades for course ID=" + courseId, e);
            throw new DatabaseException("Failed to find grades for course ID " + courseId, e);
        }
        return grades;
    }

    private Grade mapRow(ResultSet rs) throws SQLException {
        return new Grade(
                rs.getInt("grade_id"),
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                rs.getDouble("score")
        );
    }

}
