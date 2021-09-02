package userManagement;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletUserSession
 */
public class ServletUserSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Konstruktor für das Aufrufen aus Javaklassen
    public ServletUserSession(HttpServletRequest request, String user_name, String user_email, String user_role) {
    	//Session wird gesetzt
	    HttpSession session = request.getSession();
	    session.setAttribute("user_name", user_name);
	    session.setAttribute("user_email", user_email);
	    session.setAttribute("user_role", user_role);
    }
    
    // Leerer Konstruktor für das Aufrufen aus nicht-Javaklassen
    // Zum Beenden der Session
    public ServletUserSession() {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        try {
        	// Wert aus dem action Parameter holen
            String action = request.getParameter("action");
            
            // Wenn Parameter = Logout, beende die Session
            if (action != null && action.equals("logout")) {
                session.invalidate();
                response.sendRedirect("index.jsp");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
