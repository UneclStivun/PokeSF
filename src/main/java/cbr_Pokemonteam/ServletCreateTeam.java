package cbr_Pokemonteam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pokemon.Pokemon;
import pokemon.Pokemonteam;

/**
 * Servlet implementation class ServletCreateTeam
 */
public class ServletCreateTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletCreateTeam() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		List<Pokemon> poketeam;
		List<Pokemon> quickList;
		Pokemonteam team;
		int pos;
		
		quickList = (List<Pokemon>) session.getAttribute("quickList");
		//check if there is alrady an existing poketeam in the session
		if((List) session.getAttribute("poketeam") != null) {
			poketeam = (List)session.getAttribute("poketeam");
		} else {
			poketeam = new ArrayList<Pokemon>();
		}
		
		//if user wants to add to poketeam
		if(request.getParameter("add") != null) {
			//more than 6 Pokemon to be added
			if(poketeam.size() > 5) {
				request.setAttribute("errorMsg", "Error! No more than 6 Pokemons per Team possible!");
			} else {
				pos = Integer.parseInt(request.getParameter("add"));
				poketeam.add(quickList.get(pos));
			}
		}
		
		//if user wants to delete from poketeam
		if(request.getParameter("delete") != null) {
			System.out.println(poketeam.size());
			pos = Integer.parseInt(request.getParameter("delete"));
			poketeam.remove(pos);
		}
		
		//if user wants to save his team to the database!
		if(request.getParameter("save") != null) {
			if(request.getParameter("team_name") != null && !request.getParameter("team_name").isEmpty()) {
				team = new Pokemonteam();
				team.setTeamname(request.getParameter("team_name"));
				team.setPokemon(poketeam);
				DatabaseManipulator dbm = new DatabaseManipulator();
				try {
					dbm.addPokemonteamToDatabase(team);
					request.setAttribute("MsgTeam", "Team has been added successfully!");
				} catch (Exception e) {
					request.setAttribute("MsgTeam", "Database operation has failed!");
				}
			} else {
				request.setAttribute("MsgTeam", "Please enter a teamname!");
			}
		}
		session.setAttribute("poketeam", poketeam);
		request.getRequestDispatcher("pokemonTeamCreator.jsp").forward(request, response);
	}

}
