package pokemon;

import java.io.IOException;
import java.io.PrintWriter;
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
		
		int checkPosition = 0;
		
		// Prüfe, ob Angabe zur Durchführung einer Prüfung gesetzt ist
		if(request.getParameter("check") != null && !request.getParameter("check").equals("")) {
			PrintWriter out = response.getWriter();
			JSONObject check = new JSONObject();
			
			checkPosition = Integer.parseInt(request.getParameter("check"));
			
			// prüfe, ob einwechselndes Pokemon bereits besiegt
			if(userTeam.getPokemon().get(checkPosition).getHitpoints() < 1) {
		        check.put("check", false);
			} else {
				check.put("check", true);
			}

			out.print(check);
		    return;
		}
		
		// Erweitere Erklärungen der in der Runde durchgeführten Aktionen 
		PokemonExplanationManager explanationMng = new PokemonExplanationManager(session);
		
		// Ziehe Jsonobjekte aus der Ajax-Funktion heran
		JSONObject agentAction = new JSONObject(request.getParameter("agentAction"));
		JSONObject userAction = new JSONObject(request.getParameter("userAction"));
		
		// Prüfe ob Pokemon durch K.O. gewechselt werden müssen
		// Ansonsten wird die Runde normal ausgeführt
		if(agentAction.getString("action").equals("forceSwitch")) {
			switchPokemon(enemyTeam.getPokemon(), agentAction.getInt("position"));
		} else if (userAction.getString("action").equals("forceSwitch")) {
			switchPokemon(userTeam.getPokemon(), userAction.getInt("position"));
		} else {
			explanationMng.addExplanationRound();
			
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
		}
		
		session.setAttribute("isUserTeamDefeated", checkTeamDefeated(userTeam.getPokemon()));
		session.setAttribute("isEnemyTeamDefeated", checkTeamDefeated(enemyTeam.getPokemon()));
		
		response.sendRedirect("pokemonFight.jsp");
	}
	
	// Methode zum Ausführen der Ereignisse in der Runde
	public void executeRound(HttpSession session, Pokemonteam activeTeam, Pokemonteam passiveTeam, PokemonExplanationManager explanationMng, String action, int position) {
		
		switch(action) {
		case "switch":
			// Wechsel Pokemon mit der angegebenen Position im Team aus
			switchPokemon(activeTeam.getPokemon(), position);
			explanationMng.addExplanationSwitch(activeTeam, position);
			break;
		case "attack":
			// Prüfe ob beide Pokemon noch Lebenspunkte haben und fahre entsprechend mit Angriff fort
			if(activeTeam.getPokemon().get(0).getHitpoints() > 0 && passiveTeam.getPokemon().get(0).getHitpoints() > 0) {
				calculateDamage(activeTeam.getPokemon(), passiveTeam.getPokemon(), position);
				explanationMng.addExplanationDamage(activeTeam, passiveTeam, position);
				explanationMng.addExplanationDefeat(passiveTeam, checkPokemonDefeated(passiveTeam.getPokemon()), checkTeamDefeated(passiveTeam.getPokemon()));
				break;
			}
		default:
			break;
		}
	}
	
	// Methode zum Austauschen der Pokemon.
	// Zu tauschende Pokemon tauschen den Platz in der Liste
	private void switchPokemon(List<Pokemon> poketeam, int position) {
		
		// Prüfe ob gültiges Pokemon gewählt wurde
		if(poketeam.get(position).getHitpoints() > 0) {
			Pokemon pokemonPlaceholder = poketeam.get(0);
			poketeam.set(0, poketeam.get(position));
			poketeam.set(position, pokemonPlaceholder);
		}
	}
	
	// Methode für die Schadensberechnung
	// Gibt einen boolean zurück, der angibt, ob das Team besiegt wurde
	//Schadensberechnung: Basis * (Angriffswert / (50 * Verteidigungswert) * MOD1 + 2) * STAB * Typ1 * Typ2
	private void calculateDamage(List<Pokemon> attackingTeam, List<Pokemon> defendingTeam, int attackPosition) {
		
		// Notwendige Werte der Pokemon und der Attacke
		double damage = 10 * 100;
		double mod = 1;
		String pokemonType1 = attackingTeam.get(0).getType1();
		String pokemonType2 = attackingTeam.get(0).getType2();
		String attackType = attackingTeam.get(0).getAttacks().get(attackPosition).getAttacktype();
		String attackClass = attackingTeam.get(0).getAttacks().get(attackPosition).getAttackclass();
		int attack = attackingTeam.get(0).getAttack();
		int spAttack = attackingTeam.get(0).getSpAttack();
		int defense = defendingTeam.get(0).getDefense();
		int spDefense = defendingTeam.get(0).getSpDefense();
		
		// Wenn angreifer brennt 0.5 Schaden und bei LichtSchild/Reflektor 0.5 Schaden
		if(attackingTeam.get(0).getAil1() != null && attackingTeam.get(0).getAil1().equals("BRN")) {
			mod = 0.5;
			//  #### Lichtschild einberechnen ####
		}
		
		// Unterscheide zwischen verschiedenen Angriffsklassen
		switch(attackClass) {
		case "physical":
			//damage = damage * (attack / (50 * defense) * mod + 2);
			damage = damage * attack / 50 / defense * mod + 2;
			break;
		case "special":
			//damage = damage * (spAttack / (50 * spDefense) * mod + 2);
			damage = damage * spAttack / 50 / spDefense * mod + 2;
			break;
		case "effect":
			break;
		default:
			break;
		}
		
		// Wenn Angriff STAB ist, dann Schaden um 1.5 erhöht
		if(attackType.equals(pokemonType1) || attackType.equals(pokemonType2)) {
			damage = damage * 1.5;
		}
		
		//  #### Typ1 anfällig gegen Attacke  ####
		//TypeTableSupport.checkDefenseAffinities(defendingTeam.get(0));
		
		//  #### Typ2 anfällig gegen Attacke ####
		
		System.out.println("Schaden: " + damage);
		
		defendingTeam.get(0).setHitpoints(defendingTeam.get(0).getHitpoints() - (int)damage);
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
