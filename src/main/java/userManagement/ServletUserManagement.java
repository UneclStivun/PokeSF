package userManagement;

import java.io.IOException;

import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**Servlet um die Funktionen des User Managements umzusetzen
 * @author Tobias Brakel
 * */
/**
 * Servlet implementation class ServletUserManagement
 */
public class ServletUserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletUserManagement() {
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
	
		String action = request.getParameter("action");
		
		// Instanz von DatabaseManipulator, um Validationseintrag in der Datenbank zu aktualisieren
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		
		switch(action) {
		case "all":
			request.setAttribute("resultList", dmPokemonDatabase.getAllAccounts());
			break;
		case "user":
			request.setAttribute("resultList", dmPokemonDatabase.getUserAccounts());
			break;
		case "admin":
			request.setAttribute("resultList", dmPokemonDatabase.getAdminAccounts());
			break;
		case "change":
			String role = request.getParameter("role");
			if(role.equals("admin")) {
				role = "user";
			} else {
				role = "admin";
			}
			dmPokemonDatabase.changeAccountRole(role, request.getParameter("email"));
			
			request.getRequestDispatcher("userManagement.jsp").forward(request, response);
			break;
		case "delete":
			dmPokemonDatabase.deleteAccount(request.getParameter("email"));
			break;
		default:
			break;
		}
		
		// Spaltennamen und Liste weitersenden
		request.setAttribute("columnNames", new String[] { "Username", "Email", "Role"});

		request.getRequestDispatcher("userManagement.jsp").forward(request, response);
	}
}
