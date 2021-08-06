package userManagement;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletRegistUser
 */
@WebServlet("/ServletRegistUser")
public class ServletRegistUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletRegistUser() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		String user_name;
		String user_password;
		String user_email;
		
		// Prüfen ob Felder ausgefüllt wurden
		if(!request.getParameter("user_name").isEmpty() && !request.getParameter("user_password").isEmpty()
				&& !request.getParameter("user_email").isEmpty()) {
			user_name = request.getParameter("user_name");
			user_password = request.getParameter("user_password");
			user_email = request.getParameter("user_email");
			
			System.out.println(user_name + " ist nun mit der Email " + user_email + " angemeldet.");
		} else {
			response.sendRedirect("register.html");
		}
	}

}
