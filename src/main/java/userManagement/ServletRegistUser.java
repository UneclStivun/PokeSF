package userManagement;

import java.io.IOException;
import java.sql.SQLException;

import database.DatabaseManipulator;
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
		
		// Pr�fen ob alle Formularfelder ausgef�llt wurden
		if(!request.getParameter("user_name").isEmpty() && !request.getParameter("user_password").isEmpty()
				&& !request.getParameter("user_email").isEmpty()) {
			
			// Wenn alle Felder gef�llt wurden, setze die Werte
			user_name = request.getParameter("user_name");
			user_password = request.getParameter("user_password");
			user_email = request.getParameter("user_email");
			
			//// PASSWORT UMWANDELN /////
			
			// Instanz von DatabaseManipulator wird erstellt
			// Der Methode addUserToDatabase aus DatabaseManipulator werden die Nutzerdaten �bergeben
			DatabaseManipulator dmUserDatabase = new DatabaseManipulator();
			
			try {
				// �berpr�fe, ob die Email einmalig ist
				// Wenn Email einmalig, f�ge Nutzer der Datenbank hinzu
				// Falls Email bereits vorhanden, gebe entsprechende Meldung zur�ck
				if(dmUserDatabase.emailIsUnique(user_email)) {
					dmUserDatabase.addUserToDatabase(user_name, user_password, user_email);
					request.setAttribute("message", "User " + user_name + " was created");
				} else {
					request.setAttribute("message", "The email address is already in use");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "Something went wrong. Please try again");
			}
		} else {
			request.setAttribute("message", "Please fill in all empty fields");
		}
		
		request.getRequestDispatcher("registUser.jsp").forward(request, response);
	}
}
