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
 * Servlet implementation class ServletuserRegistration
 */
@WebServlet("/ServletUserRegistration")
public class ServletUserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletUserRegistration() {
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
		
		String user_name = "";
		String user_password = "";
		String user_email = "";
		
		// Prüfen ob alle Formularfelder ausgefüllt wurden
		// Wenn alle Felder gefüllt wurden, setze die Werte, ansonsten entsprechende Meldung
		if(!request.getParameter("user_name").isEmpty() && !request.getParameter("user_password").isEmpty()
				&& !request.getParameter("user_email").isEmpty()) {
			
			user_name = request.getParameter("user_name");
			user_password = request.getParameter("user_password");
			user_email = request.getParameter("user_email");
			
			//// PASSWORT UMWANDELN /////
			
			// Instanz von DatabaseManipulator wird erstellt
			DatabaseManipulator dmUserDatabase = new DatabaseManipulator();
			
			try {
				// Überprüfe, ob die Email einmalig ist.
				// Wenn Email einmalig, füge Nutzer der Datenbank hinzu
				// Falls Email bereits vorhanden, gebe entsprechende Meldung zurück
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
		
		// Setze eine Session
		new ServletUserSession(request, user_name, user_email, "user");
		
		// Führe zurück zur Seite der Registrierung
		request.getRequestDispatcher("userRegistration.jsp").forward(request, response);
	}
}
