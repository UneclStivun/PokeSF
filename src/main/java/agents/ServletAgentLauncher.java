package agents;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletAgentLauncher
 */
public class ServletAgentLauncher extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletAgentLauncher() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Agenten terminieren
		new AgentLauncher().terminateAgents();
		
		response.sendRedirect("pokemonTeamCreator.jsp");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Rufe AgentLauncher auf, um Agenten dort zu initialisieren
		new AgentLauncher().launchAgents();
		
		// Weiterleitung für die Kampfsimulation
		request.getRequestDispatcher("pokemonFight.jsp").forward(request, response);
	}
}
