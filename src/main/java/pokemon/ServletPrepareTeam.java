package pokemon;

import java.io.IOException;
import java.util.List;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get Action from PrepareTeam.jsp
		HttpSession session = request.getSession();
		List<Pokemonteam> allTeams = (List<Pokemonteam>) session.getAttribute("allTeamList");
		int counter;
			
		//add Team to user
		if(request.getParameter("user") != null) {
			counter = Integer.parseInt(request.getParameter("all"));
			session.setAttribute("userTeam", allTeams.get(counter));
		}
			
		//add Team to Enemy
		if(request.getParameter("enemy") != null) {
			counter = Integer.parseInt(request.getParameter("all"));
			session.setAttribute("enemyTeam", allTeams.get(counter));
		}
		//Then possibility to gain similar Teams for either user or enemy Team
		//Then (optional get a "counter" to the current user / enemy Team)
		request.getRequestDispatcher("pokemonPrepareTeam.jsp").forward(request, response);
		}
	}
