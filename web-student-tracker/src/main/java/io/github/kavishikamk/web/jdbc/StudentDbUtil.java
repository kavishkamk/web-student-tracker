package io.github.kavishikamk.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
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
	
}
