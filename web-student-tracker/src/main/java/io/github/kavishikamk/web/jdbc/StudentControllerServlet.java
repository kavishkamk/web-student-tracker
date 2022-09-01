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
		
		String theCommand = request.getParameter("command");
		
		if (theCommand == null) {
			theCommand = "LIST";
		}
		
		try {
			
			switch (theCommand) {
				case "LIST":
					listTheStudents(request, response);
					return;
				case "ADD":
					addStudent(request, response);
					return;
				case "LOAD":
					loadStudentData(request, response);
					return;
				case "UPDATE":
					updateStudent(request, response);
					return;
				case "DELETE":
					deleteStudent(request, response);
				default:
					listTheStudents(request, response);
			}
			
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.studentDbUtil.deleteStudent(Integer.parseInt(request.getParameter("studentId")));
		listTheStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.studentDbUtil.updateStudent(
				Integer.parseInt(request.getParameter("studentId")), 
				request.getParameter("first_name"),
				request.getParameter("last_name"),
				request.getParameter("email")
		);
		
		listTheStudents(request, response);
	}

	private void loadStudentData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Student student = this.studentDbUtil.getStudentDetails(request.getParameter("studentId"));
		request.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-update-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Student student = 
				new Student(
						request.getParameter("first_name"), 
						request.getParameter("last_name"), 
						request.getParameter("email")
				);
		
		this.studentDbUtil.addStudent(student);
		
		listTheStudents(request, response);
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
