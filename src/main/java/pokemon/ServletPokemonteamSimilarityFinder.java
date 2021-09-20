package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cbr_Pokemonteam.CaseBaseLoader_Pokemonteam;
import cbr_Pokemonteam.Case_Pokemonteam;
import cbr_Pokemonteam.Retrieval_Pokemonteam;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class ServletPokemonteamSimilarityFinder
 */
public class ServletPokemonteamSimilarityFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletPokemonteamSimilarityFinder() {
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
		HttpSession session = request.getSession();
		String sim = request.getParameter("sim");
		String count = request.getParameter("count");
		List<Pokemonteam> allTeams = (List<Pokemonteam>) session.getAttribute("allTeamList");
		CaseBaseLoader_Pokemonteam cbl = (CaseBaseLoader_Pokemonteam) session.getAttribute("cbl_pt");
		
		//check for empty object
		if(request.getParameter("retrieve") != null && !sim.isEmpty()) {
			int simInt = Integer.parseInt(sim); 
			Pokemonteam team = null;
			
			for(int i = 0; i < allTeams.size(); i++) {
				if(simInt == allTeams.get(i).getTeamid()) {
					team = allTeams.get(i);
					//exit loop
					i = allTeams.size();
				}
			}
			
			if(team != null) {
				//get all Teams listed by similarity
				List<Case_Pokemonteam> shortList = new ArrayList<Case_Pokemonteam>();
				List<Case_Pokemonteam> retrievedCases = Retrieval_Pokemonteam.retrieveSimCases(cbl, team, allTeams);
				
				//remove the top most item for which is always the same team as the query team
				if(retrievedCases.size() > 0) {
					retrievedCases.remove(0);
					int j = 0;
					
					for(int i = 0; i < retrievedCases.size();i++) {
						shortList.add(retrievedCases.get(i));
						j++;
						//Add up to 3 elements and then jump out of loop
						if(j > 2) {
							i = retrievedCases.size();
						}
					}
					
				}
				
				for(int i = 0; i < shortList.size(); i++) {
					shortList.get(i).pokemonAttsToString();
				}
				session.setAttribute("simTeams", shortList);
			}
		}
		
		//if user wants a counter to his selected team
		if(request.getParameter("counter") != null && !count.isEmpty()) {
			int countInt = Integer.parseInt(count);
			Pokemonteam team = null;
			
			for(int i = 0; i < allTeams.size(); i++) {
				if(countInt == allTeams.get(i).getTeamid()) {
					team = allTeams.get(i);
					//exit loop
					i = allTeams.size();
				}
			}
			
			if(team != null) {
				List<Integer> scoreList = new ArrayList<Integer>();
				int scorer;
				//retrieve hard counter teams from all Teams and calculate the strongest teams with a new TypeTableSupport function
				Map<String, Integer> defAff = TypeTableSupport.checkTeamDefenseAffinities(team);
				
				for(int i = 0; i < allTeams.size(); i++) {
					scorer = 0;
					for(int j = 0; j < allTeams.get(i).getPokemon().size(); j++) {
						for(Map.Entry<String, Integer> entry : defAff.entrySet()) {
							if(entry.getKey().contains("weak") && entry.getValue() > 0) {
								//if pokemon from allTeams has type that hits weaknesses
								if(entry.getKey().contains(allTeams.get(i).getPokemon().get(j).getType1())) {
									scorer++;
								}
								
								if(entry.getKey().contains(allTeams.get(i).getPokemon().get(j).getType2())
										&& !allTeams.get(i).getPokemon().get(j).getType2().isEmpty()) {
									scorer++;
								}
							}
							
							if(entry.getKey().contains("immune") && entry.getValue() > 0) {
								if(entry.getKey().contains(allTeams.get(i).getPokemon().get(j).getType1())) {
									scorer--;
								}
								
								if(entry.getKey().contains(allTeams.get(i).getPokemon().get(j).getType2())
										&& !allTeams.get(i).getPokemon().get(j).getType2().isEmpty()) {
									scorer--;
								}
							}
						}
					}
					scoreList.add(scorer);
				}
				//iterate through scoreList and allTeams to get 1-3 counter teams and save it as a new list to the session
				//Verweis von Liste auf List => fehler durch transitivität von änderungen von liste a zu b
				//ScoreList wird auch sortiert obwohl nur sortedScoreList sortiert wird
				List<Integer> sortedScoreList = scoreList;
				List<Pokemonteam> counterTeamList = new ArrayList<Pokemonteam>();
				Collections.sort(sortedScoreList, Collections.reverseOrder());
				
				for(int i = 0; i < sortedScoreList.size(); i++) {
					for(int j = 0; j < scoreList.get(j); j++) {
						if(counterTeamList.size() < 2) {
							if(sortedScoreList.get(i) == scoreList.get(j)) {
								counterTeamList.add(allTeams.get(j));
							}
						}
					}
				}
				session.setAttribute("counterTeamList", counterTeamList);
			}
		}
		request.getRequestDispatcher("pokemonPrepareTeam.jsp").forward(request, response);
	}
}
