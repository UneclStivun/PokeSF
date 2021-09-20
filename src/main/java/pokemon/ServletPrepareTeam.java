package pokemon;

import java.io.IOException;
import java.util.List;

import cbr_Pokemonteam.CaseBaseLoader_Pokemonteam;
import cbr_Pokemonteam.Retrieval_Pokemonteam;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class ServletPrepareTeam
 */
public class ServletPrepareTeam extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;

    /**
     * Default constructor. 
     */
    public ServletPrepareTeam() {
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
		// Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
		
		List<Pokemonteam> allTeams = (List<Pokemonteam>) session.getAttribute("allTeamList");
		
		int counter;
		String all = request.getParameter("all");		
			
		//add Team to user
		if(request.getParameter("user") != null && !all.isEmpty()) {
			counter = Integer.parseInt(all);
			session.setAttribute("userTeam", allTeams.get(counter));
			session.setAttribute("isUserTeamDefeated", false);
		}
			
		//add Team to Enemy
		if(request.getParameter("enemy") != null && !all.isEmpty()) {
			counter = Integer.parseInt(all);
			session.setAttribute("enemyTeam", allTeams.get(counter));
			session.setAttribute("isEnemyTeamDefeated", false);
		}
		
		//Then possibility to gain similar Teams for either user or enemy Team
		//Then (optional get a "counter" to the current user / enemy Team)
		request.getRequestDispatcher("pokemonPrepareTeam.jsp").forward(request, response);
	}
}
