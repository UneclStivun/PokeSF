package pokemon;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPokemonFight
 */
public class ServletPokemonFight extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletPokemonFight() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
		
		// In der Session gespeichertes Pokemonteam aufrufen
		Pokemonteam userTeam = (Pokemonteam)session.getAttribute("userTeam");
		Pokemonteam enemyTeam = (Pokemonteam)session.getAttribute("enemyTeam");
		
		// Geschwindigkeiten der aktuell kämpfenden Pokemon
		int userSpeed = userTeam.getPokemon().get(0).getInitiative();
		int enemySpeed = enemyTeam.getPokemon().get(0).getInitiative();
		
		// Erweitere Erklärungen der in der Runde durchgeführten Aktionen 
		PokemonExplanationManager explanationMng = new PokemonExplanationManager(session);
		explanationMng.addExplanationRound();
		
		// Ziehe Jsonobjekte aus der Ajax-Funktion heran
		JSONObject agentAction = new JSONObject(request.getParameter("agentAction"));
		JSONObject userAction = new JSONObject(request.getParameter("userAction"));
		
		// Führe Aktionsentscheidungen für die jeweiligen Spieler aus:
		// Wenn nur ein Team das Pokemon austauscht, wird die Aktion vorgezogen
		// Ansonsten agiert das schnellere Pokemon als erstes
		if(agentAction.getString("action").equals("switch") && userAction.getString("action").equals("attack")) {
			// enemyTeam wechselt Pokemon
			executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
			executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
		} else if(userAction.getString("action").equals("switch") && agentAction.getString("action").equals("attack")) {
			// userTeam wechselt Pokemon
			executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
			executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
		} else if (userSpeed > enemySpeed) {
			// userTeam agiert schneller
			executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
			executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
		} else {
			// enemyTeam agiert schneller
			executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
			executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
		}
		
		session.setAttribute("isUserTeamDefeated", checkTeamDefeated(userTeam.getPokemon()));
		session.setAttribute("isEnemyTeamDefeated", checkTeamDefeated(enemyTeam.getPokemon()));
		
		response.sendRedirect("pokemonFight.jsp");
	}
	
	// Methode zum Ausführen der Ereignisse in der Runde
	public void executeRound(HttpSession session, Pokemonteam activeTeam, Pokemonteam passiveTeam, PokemonExplanationManager explanationMng, String action, int position) {
		
		switch(action) {
		case "switch":
			switchPokemon(activeTeam.getPokemon(), position);
			explanationMng.addExplanationSwitch(activeTeam, position);
			break;
		case "attack":
				calculateDamage(activeTeam.getPokemon(), passiveTeam.getPokemon(), position);
				explanationMng.addExplanationDamage(activeTeam, passiveTeam, position);
				explanationMng.addExplanationDefeat(passiveTeam, checkPokemonDefeated(passiveTeam.getPokemon()), checkTeamDefeated(passiveTeam.getPokemon()));
				break;
		default:
			break;
		}
	}
	
	// Methode zum Austauschen der Pokemon.
	// Zu tauschende Pokemon tauschen den Platz in der Liste
	private void switchPokemon(List<Pokemon> poketeam, int position) {
		Pokemon pokemonPlaceholder = poketeam.get(0);
		poketeam.set(0, poketeam.get(position));
		poketeam.set(position, pokemonPlaceholder);
	}
	
	// Methode für die Schadensberechnung
	// Gibt einen boolean zurück, der angibt, ob das Team besiegt wurde
	private void calculateDamage(List<Pokemon> attackingTeam, List<Pokemon> defendingTeam, int attackPosition) {

		// Schadensberechnung: (42) * Basis * (Angriffswert / (50 * Verteidigungswert) * MOD1 + 2) * STAB * Typ1 * Typ2
		defendingTeam.get(0).setHitpoints(defendingTeam.get(0).getHitpoints()-10);
		
	}
	
	// Methode zum Prüfen ob ein Pokemon besiegt wurde
	private boolean checkPokemonDefeated(List<Pokemon> team) {
		boolean isDefeated = false;
		
		// Wenn Hitpoints unter 1 fallen, setze sie auf 0 und vergebe den Status 'Besiegt'
		if(team.get(0).getHitpoints() < 1) {
			team.get(0).setHitpoints(0);
			team.get(0).setAil1("K.O.");
			isDefeated = true;
		}
		return isDefeated;
	}
	
	// Methode zum Prüfen ob das Team besiegt wurde
	private boolean checkTeamDefeated(List<Pokemon> team) {
		boolean isDefeated = false;
		int countDefeated = 0;
		
		// Prüfe für jedes Pokemon im Team, ob Pokemon besiegt wurde
		for (int i = 0; i < team.size(); i++) {
			if (team.get(i).getAil1() != null) {
				if (team.get(i).getAil1().equals("K.O.")) {
					countDefeated++;
				}
			}
		}

		// Wurden soviele Pokemon besiegt, wie im Team sind, wurde der Spieler besiegt
		if (countDefeated == team.size()) {
			isDefeated = true;
		}
		return isDefeated;
	}
}
