package userManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**Servlet um die Funktionen des User Logins umzusetzen
 * @author Tobias Brakel
 * */
/**
 * Servlet implementation class ServletUserLogin
 */
public class ServletUserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletUserLogin() {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		String user_email = "";
		String user_password = "";
		
		// Pr�fen ob alle Formularfelder ausgef�llt wurden
		if(!request.getParameter("user_email").isEmpty() && !request.getParameter("user_password").isEmpty()) {
			
			// Wenn alle Felder gef�llt wurden, setze die Werte
			user_email = request.getParameter("user_email");
			user_password = request.getParameter("user_password");
			
			// Instanz von DatabaseManipulator wird erstellt
			// Der Methode userExists der DatabaseManipulator Klasse werden die Parameter f�r die Usersuche �bergeben
			DatabaseManipulator dmUserDatabase = new DatabaseManipulator();
			
			try {
				if(dmUserDatabase.userExists(user_email, user_password)) {
					
					ArrayList<String[]> userData = dmUserDatabase.getUserData(user_email);
					
					// Setze eine Session und f�hre zur�ck zur Startseite
					new ServletUserSession(request, userData.get(0)[0], userData.get(0)[2], userData.get(0)[3]);
					
					response.sendRedirect("index.jsp");					
					return;
				} else {
					request.setAttribute("message", "The combination of entered "
							+ "email and password wasn't found in the database. Please try again.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		// Entsprechende Meldung, wenn nicht alle Felder gef�llt wurden
		} else {
			request.setAttribute("message", "Please fill in all empty fields");
		}
		request.getRequestDispatcher("userLogin.jsp").forward(request, response);
	}
}
