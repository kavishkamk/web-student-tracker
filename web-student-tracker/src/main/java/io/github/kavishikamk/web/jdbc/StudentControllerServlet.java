package io.github.kavishikamk.web.jdbc;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	private StudentDbUtil studentDbUtil;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			this.studentDbUtil = new StudentDbUtil(dataSource);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			listTheStudents(request, response);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	private void listTheStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// get student list
		List<Student> students = this.studentDbUtil.getStudentList();
		
		// set list to request object
		request.setAttribute("STUDENT_LIST", students);
		
		// get the dispatcher
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("list_student.jsp");
		
		// forward response
		requestDispatcher.forward(request, response);
	}

}
