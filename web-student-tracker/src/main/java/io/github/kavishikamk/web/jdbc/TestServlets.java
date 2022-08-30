package io.github.kavishikamk.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlets
 */
public class TestServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// set up print writer
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		// get the connection
		Connection connection;
		Statement statement;
		ResultSet result;
		
		try {
			connection = dataSource.getConnection();
			
			// create MySQL statement
			String sql = "SELECT * FROM student;";
			statement = connection.createStatement();
			
			// execute the query
			result = statement.executeQuery(sql);
			
			// process the result set
			while (result.next()) {
				String email = result.getString("email");
				out.println(email);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
