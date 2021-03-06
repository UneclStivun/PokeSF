package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cbr_Pokemon.CaseBaseLoader_Pokemon;
import cbr_Pokemon.Case_Pokemon;
import cbr_Pokemon.Retrieval_Pokemon;
import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**Servlet um die Funktionen auf der pokemonTeamCreator.jsp umzusetzen.
 * @author Steven Oberle
 * */

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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		CaseBaseLoader_Pokemon cbl = (CaseBaseLoader_Pokemon) session.getAttribute("cbl");
		List<Pokemon> poketeam;
		List<Pokemon> quickList;
		List<Pokemon> allPokemon;
		Pokemonteam team;
		int pos;
		
		quickList = (List<Pokemon>) session.getAttribute("quickList");
		
		//check if there is already an existing poketeam in the session
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
		
		//if user has at least 1 Pokemon in his Team
		if(poketeam.size() > 0) {
			Pokemonteam uTeam = new Pokemonteam();
			uTeam.setPokemon(poketeam);
			uTeam.pokemonAttsToString();
			//method to retrieve the types needed for a balanced team composition
			List<ScorePair> result = checkTeamBalance(cbl, uTeam);
			if(result.size() > 0) {
				List<Case_Pokemon> resultCasesAtt = Retrieval_Pokemon.retrieveSimCases(cbl, result.get(0).getPokemon());
				List<Pokemon> pokeAttList = new ArrayList<Pokemon>();
				List<String> topTypes = new ArrayList<String>();
				//shorten List to 3 most similar Pokemon to best type combination
				for(int i = 0; i < resultCasesAtt.size(); i++) {
					if(i < 3) {
						pokeAttList.add(resultCasesAtt.get(i).getPokemon());
					}
				}
				
				for(int i = 0; i < result.size(); i++) {
					if(i < 3) {
						if(result.get(i).getPokemon().getType2() != null) {
							topTypes.add(result.get(i).getPokemon().getType1() + "/" + result.get(i).getPokemon().getType2());
						} else {
							topTypes.add(result.get(i).getPokemon().getType1());
						}
					}
				}
				//get the team attributes for display
				session.setAttribute("uTeam", uTeam);
				session.setAttribute("resultCasesAtt", pokeAttList);
				session.setAttribute("topTypes", topTypes);
			}
		}
		session.setAttribute("poketeam", poketeam);
		request.getRequestDispatcher("pokemonTeamCreator.jsp").forward(request, response);
	}
	
	//this method checks team balance and starts retrieving pokemon with types similar to the ones needed and 
	private List<ScorePair> checkTeamBalance(CaseBaseLoader_Pokemon cbl,  Pokemonteam uTeam) {
		boolean resFound;
		List<String> weaknesses = new ArrayList<String>();
		List<String> weaknessesLeft = new ArrayList<String>();
		
		//extract type of all weaknesses
		for(int i = 0; i < uTeam.getWeaknesses().size(); i++) {
			weaknesses.add((String) uTeam.getWeaknesses().get(i).subSequence(0, uTeam.getWeaknesses().get(i).indexOf(":")));
		}
		
		//check if weaknesses have been adressed by resistances and immunities
		for(int i = 0; i < weaknesses.size(); i++) {
			resFound = false;
			for(int j = 0; j < uTeam.getResistances().size(); j++) {
				if(uTeam.getResistances().get(j).contains(weaknesses.get(i))) {
					resFound = true;
				}
			}
			for(int j = 0; j < uTeam.getImmunities().size(); j++) {
				if(uTeam.getImmunities().get(j).contains(weaknesses.get(i))) {
					resFound = true;
				}
			}
			//weakness has no resistance/ immune couterpart
			if(!resFound) {
				weaknessesLeft.add(weaknesses.get(i));
			}
		}
		//Preparing input of type combinations for retrieval
		List<String> resTypes = TypeTableSupport.adressWeaknesses(weaknessesLeft);
		//return List of Case_Pokemon
		return TypeTableSupport.calculateTypeComboResistance(resTypes, uTeam.getResistances());
	}
	
	//this method checks if pokemon that user wants to add is already in the team
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
