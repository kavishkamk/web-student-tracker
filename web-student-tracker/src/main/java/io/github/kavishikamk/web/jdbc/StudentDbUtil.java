package io.github.kavishikamk.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Student> getStudentList() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;
		List<Student> students = new ArrayList<>();
		
		String sql = "SELECT * FROM student ORDER BY last_name;";
		
		try {
			conn = this.dataSource.getConnection();
			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);
			
			while(res.next()) {
				students.add(
						new Student(
							res.getInt("id"), 
							res.getString("first_name"),
							res.getString("last_name"), 
							res.getString("email")
						)
				);
			}
			
		} finally {
			close(conn, stmt, res);
		}
		return students;
	}

	private void close(Connection conn, Statement stmt, ResultSet res) {
		try {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close(); // not close connection, but set as free and add to connection pool
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addStudent(Student student) throws SQLException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		String sql = "INSERT INTO student " 
				+ "(first_name, last_name, email) "
				+ "VALUES (?, ?, ?)";
		
		try {
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
		
	}

	public Student getStudentDetails(String studentId) throws Exception {
		
		Student theStudent = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resSet = null;
		
		String sql = "SELECT * FROM student WHERE id = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(studentId));
			resSet = stmt.executeQuery();
			
			if (resSet.next()) {
				theStudent = new Student(
						Integer.parseInt(studentId), 
						resSet.getString("first_name"),
						resSet.getString("last_name"),
						resSet.getString("email")
				);
			} else {
				throw new Exception("Could not find user : " + studentId);
			}
		} finally {
			close(conn, stmt, resSet);
		}
		
		return theStudent;
	}

	public void updateStudent(int id, String fname, String lname, String email) throws SQLException {
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE student SET "
					+ "first_name=?, last_name=?, email=? "
					+ "WHERE id=?;";
			con = dataSource.getConnection();
			stmt = con.prepareStatement(sql);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, email);
			stmt.setInt(4, id);
			stmt.execute();
		} finally {
			close(con, stmt, null);
		}
		
		
	}

	public void deleteStudent(int id) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			String sql = "DELETE FROM student WHERE id = ?";
			
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} finally {
			close(conn, stmt, null);
		}
		
	}
	
}
