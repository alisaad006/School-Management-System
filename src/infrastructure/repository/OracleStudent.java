package infrastructure.repository;

import adapter.StudentRepository;
import domain.Student;
import domain.exceptoins.BusinessException;
import domain.exceptoins.DatabaseException;
import domain.exceptoins.EntityNotFoundException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleStudent implements StudentRepository {

    private final Connection con;
    private static final Logger LOGGER = Logger.getLogger(OracleStudent.class.getName());
    private static final int ORA_FK_VIOLATION = 2292;

    public OracleStudent(Connection con) {
        if (con == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.con = con;
    }

    // A D D
    @Override
    public void save(Student student) {
        String sql = "INSERT INTO student (student_id, student_name, student_code, student_email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, student.getId());
            pre.setString(2, student.getName());
            pre.setString(3, student.getStudentCode());
            pre.setString(4, student.getEmail());
            pre.executeUpdate();

            LOGGER.info("Student inserted: ID=" + student.getId());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting student ID=" + student.getId(), e);
            throw new DatabaseException("Failed to insert student with ID " + student.getId(), e);
        }
    }

    // U P D A T E
    @Override
    public void update(Student student) {
        String sql = "update student set student_name = ?,  student_code = ?, student_email = ? where student_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setString(1, student.getName());
            pre.setString(2, student.getStudentCode());
            pre.setString(3, student.getEmail());
            pre.setInt(4, student.getId());

            int row = pre.executeUpdate();
            if (row == 0) {
                throw new EntityNotFoundException("Student", student.getId());
            }
            LOGGER.info("Student updated: ID=" + student.getId());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating student ID=" + student.getId(), e);
            throw new DatabaseException("Failed to update student with ID " + student.getId(), e);
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {

        String sql = "delete from student where student_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setInt(1, id);
            int rows = pre.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("Student", id);
            }
            LOGGER.info("Student deleted ID = " + id);
        } catch (SQLException e) {
            if (e.getErrorCode() == ORA_FK_VIOLATION) {
                throw new BusinessException(
                        "Cannot delete student ID " + id
                        + " because they have active enrollments or grades."
                        + " Remove associated records first.");
            }
            LOGGER.log(Level.SEVERE, "Error deleting student ID=" + id, e);
            throw new DatabaseException("Failed to delete student with ID " + id, e);
        }
    }

    // S E A R C H
    @Override
    public Student findById(int id) {
        String sql = "select student_id, student_name, student_code, student_email from student where student_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding student ID = " + id, e);
            throw new DatabaseException("Failed to find student with ID " + id, e);
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "select student_id, student_name, student_code, student_email from student";

        try (PreparedStatement pre = con.prepareStatement(sql);
                ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                students.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all students", e);
            throw new DatabaseException("Failed to fetch all students", e);
        }
        return students;
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getString("student_code"),
                rs.getString("student_email")
        );
    }

}
