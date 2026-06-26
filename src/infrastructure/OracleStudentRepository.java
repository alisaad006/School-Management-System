package infrastructure;

import adapter.StudentRepository;
import domain.Student;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class OracleStudentRepository implements StudentRepository {

    private final Connection con;

    public OracleStudentRepository(Connection con) {
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
            System.out.println("Student inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
    
    // U P D A T E
    @Override
    public void update(Student student) {
        String sql = "update student set student_name = ?,  student_code = ?, student_email = ? where student_id = ?";
        
        try{
            PreparedStatement pre = con.prepareStatement(sql);
            pre.setString(1, student.getName());
            pre.setString(2, student.getStudentCode());
            pre.setString(3, student.getEmail());
            pre.setInt(4, student.getId());
            
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Student data updated successfully");
            } else {
                System.out.println("No student found with ID " + student.getId() + " to update.");
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error during update: " + e.getMessage());
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {
        String sql  = "Delete from student where student_id = ?";
        
        try {
            PreparedStatement pre = con.prepareStatement(sql);
            
            pre.setInt(1, id);
            int rows = pre.executeUpdate();
            if (rows > 0) {
                System.out.println("Student with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Student found with ID " + id + " to delete.");
            }
            
        } catch (SQLException e) {
            System.out.println("Database Error during delete: " + e.getMessage());
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
                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("student_name"),
                            rs.getString("student_code"),
                            rs.getString("student_email")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
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
                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("student_code"),
                        rs.getString("student_email"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return students;
    }


}
