package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		List<Pokemon> allPokemon;
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
				if(checkUnique(poketeam, quickList.get(pos))){
					poketeam.add(quickList.get(pos));
				} else {
					request.setAttribute("errorMsg", "Error! Please dont add 2 of the same Pokemon on one Team!");	
				}			
			}
		}
		
//if user wants to add from all PokemonList to poketeam
		if(request.getParameter("all") != null) {
			//more than 6 Pokemon to be added
			if(poketeam.size() > 5) {
				request.setAttribute("errorMsg", "Error! No more than 6 Pokemons per Team possible!");
			} else {
				allPokemon = (List<Pokemon>) session.getAttribute("allPokeList");
				pos = Integer.parseInt(request.getParameter("all"));
				if(checkUnique(poketeam, allPokemon.get(pos))){
					poketeam.add(allPokemon.get(pos));
				} else {
					request.setAttribute("errorMsg", "Error! Please dont add 2 of the same Pokemon on one Team!");	
				}			
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

	private boolean checkUnique(List<Pokemon> poketeam, Pokemon pokemon) {
		boolean unique = true;
		for(int i = 0; i < poketeam.size(); i++) {
			if(poketeam.get(i).getName().equals(pokemon.getName())) {
				unique = false;
			}
		}
		return unique;
	}
}
