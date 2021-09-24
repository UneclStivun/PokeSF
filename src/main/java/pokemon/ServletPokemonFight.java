package pokemon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
			explanationMng.addExplanationSwitch(enemyTeam, agentAction.getInt("position"));
		} else if (userAction.getString("action").equals("forceSwitch")) {
			switchPokemon(userTeam.getPokemon(), userAction.getInt("position"));
			explanationMng.addExplanationSwitch(userTeam, userAction.getInt("position"));
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
		double damage = 1 * 100;
		double statusMod = 1.0;
		double typeMod = 1.0;
		boolean isSTAB= false;
		Map<String, Double> defendingPokemon = TypeTableSupport.checkDefenseAffinities(defendingTeam.get(0));
		List<String> typeKeys = new ArrayList<String>();
		String ail1 = attackingTeam.get(0).getAil1();
		String ail2 = attackingTeam.get(0).getAil2();
		String pokemonType1 = attackingTeam.get(0).getType1();
		String pokemonType2 = attackingTeam.get(0).getType2();
		String attackType = attackingTeam.get(0).getAttacks().get(attackPosition).getAttacktype();
		String attackClass = attackingTeam.get(0).getAttacks().get(attackPosition).getAttackclass();
		int attack = attackingTeam.get(0).getAttack();
		int spAttack = attackingTeam.get(0).getSpAttack();
		int defense = defendingTeam.get(0).getDefense();
		int spDefense = defendingTeam.get(0).getSpDefense();
		
		int rndNum = 0;		
		rndNum = 1 + (int)(Math.random() * ((10 - 1) + 1)); // [min:1, max:10]
		
		// 10% Chance, dass Feuer-, Elektro-, Eis-, Gift-Attacken einen Status auslösen
		if (rndNum == 10) {
			switch (attackType) {
			case "fire":
				defendingTeam.get(0).setAil1("BRN");
				break;
			case "electro":
				defendingTeam.get(0).setAil1("PAR");
				break;
			case "ice":
				defendingTeam.get(0).setAil1("FRZ");
				break;
			case "poison":
				defendingTeam.get(0).setAil1("PSN");
				break;
			default:
				break;
			}
		}
		
		// Leidet angreifendes Pokemon unter Status Frozen, Paralysis oder Sleep, greift es evtl. nicht an
		if(ail1 != null && ail1.equals("")) {
			rndNum = 1 + (int)(Math.random() * ((10 - 1) + 1)); // [min:1, max:10]
		}
		
		// Wenn angreifer brennt 0.5 Schaden und bei LichtSchild/Reflektor 0.5 Schaden
		if(ail1 != null && ail1.equals("BRN")) {
			statusMod *= 0.5;
			//  #### if Lichtschild statusMod *= 0.5;
		}
		
		// Unterscheide zwischen verschiedenen Angriffsklassen
		switch(attackClass) {
		case "physical":
			//damage = damage * (attack / (50 * defense) * mod + 2);
			damage = damage * attack / 50 / defense * statusMod + 2;
			break;
		case "special":
			//damage = damage * (spAttack / (50 * spDefense) * mod + 2);
			damage = damage * spAttack / 50 / spDefense * statusMod + 2;
			break;
		case "effect":
			break;
		default:
			break;
		}
		
		System.out.println(
				attackingTeam.get(0).getName()
				+ "\nattack: " + attackType
				+ "\ntype1: " + pokemonType1
				+ "\ntype2: " + pokemonType2);
		
		// Wenn Angriff STAB ist, dann Schaden um 1.5 erhöht
		// STAB = Same Type Attack Bonus
		if(attackType.equals(pokemonType1) || attackType.equals(pokemonType2)) {
			isSTAB = true;
			damage = damage * 1.5;
		}
		
		// Gebe Anfälligkeit/Resistenz gegen die Attacke wieder:		
		// Pokemontypen der Liste hinzufügen
		for (Map.Entry<String, Double> entry : defendingPokemon.entrySet()) {
			typeKeys.add(entry.getKey());
		}
		// Liste durchgehen und Modifikation aus TypeTableSupport ziehen
		for(int i = 0; i < typeKeys.size(); i++) {
			if(typeKeys.get(i).equals(attackType)) {
				typeMod = defendingPokemon.get(typeKeys.get(i));
			}
		}
		
		// Schaden mit Anfälligkeit/Resistenz multiplizieren
		// typeMod kann 0, 0.25, 0.5, 1, 2 oder 4 sein
		damage = damage * typeMod;
		
		System.out.println(
				"\nSchaden: " + damage
				+ "\nSTAB: " + isSTAB
				+ "\nAngriffsmod: " + typeMod);
		
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
