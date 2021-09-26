package agents;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pokemon.Pokemonteam;

/**Servlet um den Agentlauncher anzusprechen. Befehle aus der jsp zum Agentensystem
 * @author Tobias Brakel
 * */

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
		
		// Session heranziehen
		HttpSession session = request.getSession();
		List<Pokemonteam> allTeams = (List<Pokemonteam>) session.getAttribute("allTeamList");
		// Sessionattribute zurücksetzen
		Pokemonteam team = (Pokemonteam) session.getAttribute("userTeam");
		int teamId = team.getTeamid();
		session.removeAttribute("userTeam");
		session.removeAttribute("enemyTeam");
		
		if(allTeams.size() > 0) {
			for(int i = 0; i < allTeams.size(); i++) {
				if(teamId == allTeams.get(i).getTeamid()) {
					session.setAttribute("userTeam", allTeams.get(i));
				}
			}
		}
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
