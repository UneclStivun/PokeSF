package pokemon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import de.dfki.mycbr.core.explanation.ExplanationManager;
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
				calculateStatusDamage(enemyTeam, userTeam, explanationMng);
				calculateStatusDamage(userTeam, enemyTeam, explanationMng);
			} else if((userAction.getString("action").equals("switch") && agentAction.getString("action").equals("attack")) || (userSpeed > enemySpeed)) {
				// userTeam wechselt Pokemon oder agiert schneller
				executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
				executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
				calculateStatusDamage(userTeam, enemyTeam, explanationMng);
				calculateStatusDamage(enemyTeam, userTeam, explanationMng);
			} else {
				// enemyTeam agiert schneller
				executeRound(session, enemyTeam, userTeam, explanationMng, agentAction.getString("action"), agentAction.getInt("position"));
				executeRound(session, userTeam, enemyTeam, explanationMng, userAction.getString("action"), userAction.getInt("position"));
				calculateStatusDamage(enemyTeam, userTeam, explanationMng);
				calculateStatusDamage(userTeam, enemyTeam, explanationMng);
			}
		}
		
		session.setAttribute("isUserTeamDefeated", checkTeamDefeated(userTeam.getPokemon()));
		session.setAttribute("isEnemyTeamDefeated", checkTeamDefeated(enemyTeam.getPokemon()));
		
		response.sendRedirect("pokemonFight.jsp");
	}
	
/* ####################################################################################### */
	
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
				calculateFightingDamage(activeTeam, passiveTeam, position, explanationMng);
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
	private void calculateFightingDamage(Pokemonteam attackingTeam, Pokemonteam defendingTeam, int attackPosition, PokemonExplanationManager explanationMng) {
		
		// Notwendige Werte der Pokemon und der Attacke
		double damage = 4 * 100;
		double statusMod = 1.0;
		double typeMod = 1.0;
		boolean isSTAB= false;
		/* Angreifendes Pokemon */
		Pokemon attackingPokemon = attackingTeam.getPokemon().get(0);
		String nameAttacker = attackingPokemon.getName();
		String pokemonType1 = attackingPokemon.getType1();
		String pokemonType2 = attackingPokemon.getType2();
		Attack pokemonAttack = attackingPokemon.getAttacks().get(attackPosition);
		String attackType = pokemonAttack.getAttacktype();
		String attackClass = pokemonAttack.getAttackclass();
		String attackEffect = pokemonAttack.getEffect();
		int attack = attackingPokemon.getAttack();
		int spAttack = attackingPokemon.getSpAttack();
		/* Verteidigendes Pokemon */
		Pokemon defendingPokemon = defendingTeam.getPokemon().get(0);
		Map<String, Double> defendingPokemonAffinities = TypeTableSupport.checkDefenseAffinities(defendingPokemon);
		List<String> typeKeys = new ArrayList<String>();
		String nameDefender = defendingPokemon.getName();
		int defense = defendingPokemon.getDefense();
		int spDefense = defendingPokemon.getSpDefense();
		
		int rndNum = 0;
		
		// Gebe Anfälligkeit/Resistenz gegen die Attacke wieder:		
		// Pokemontypen der Liste hinzufügen
		for (Map.Entry<String, Double> entry : defendingPokemonAffinities.entrySet()) {
			typeKeys.add(entry.getKey());
		}
		// Liste durchgehen und Modifikation aus TypeTableSupport ziehen
		for (int i = 0; i < typeKeys.size(); i++) {
			if (typeKeys.get(i).equals(attackType)) {
				typeMod = defendingPokemonAffinities.get(typeKeys.get(i));
			}
		}
		
		// Prüfe ob Pokemon einen Status besitzt
		if(attackingPokemon.getAil1() != null) {
			
			rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
			
			// 25% Chance durch Paralyse nicht angreifen zu können
			if(attackingPokemon.getAil1().equals("PAR") && rndNum >= 75) {
				explanationMng.addExplanationBeforeDamage(nameAttacker + " can't attack due to status PAR.<br>");
				return;
			}
			
			rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
			
			// 20% Chance den Gefroren-Status des Angreifers zu entfernen und Angriff fortzusetzen
			// Ansonsten kann nicht angegriffen werden
			if(attackingPokemon.getAil1().equals("FRZ") && rndNum >= 80) {
				attackingPokemon.setAil1(null);
				explanationMng.addExplanationBeforeDamage(nameAttacker + " healed from status FRZ.<br>");
			} else if(attackingPokemon.getAil1().equals("FRZ")) {
				explanationMng.addExplanationBeforeDamage(nameAttacker + " can't attack due to status FRZ.<br>");
				return;
			}
			
			rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
			
			// 33% Chance Schlafen-Status des Angreifers zu entfernen und ANgriff fortzusetzen
			// Ansonsten kann nicht angegriffen werden
			if(attackingPokemon.getAil1() != null && attackingPokemon.getAil1().equals("SLP") && rndNum > 66) {
				attackingPokemon.setAil1(null);
				explanationMng.addExplanationBeforeDamage(nameAttacker + " healed from status SLP.<br>");
			} else if(attackingPokemon.getAil1() != null && attackingPokemon.getAil1().equals("SLP")) {
				explanationMng.addExplanationBeforeDamage(nameAttacker + " can't attack due to status SLP.<br>");
				return;
			}
		}
		
		rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
		
		// 40% Chance Verwirrung-Status aufheben
		if(attackingPokemon.getAil2() != null && attackingPokemon.getAil2().contains("conf") && rndNum >= 60){
			attackingPokemon.setAil2(attackingPokemon.getAil2().replace("conf", ""));
			explanationMng.addExplanationBeforeDamage(nameAttacker + " is no longer confused.<br>");
		} 
		
		rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
		
		// 33% Chance bei Verwirrung-Status sich selbst zu verletzen anstatt anzugreifen
		if(attackingPokemon.getAil2() != null && attackingPokemon.getAil2().contains("conf") && rndNum > 66){
			explanationMng.addExplanationBeforeDamage(nameAttacker + " damaged itself due to confusion.<br>");
			damage = 2 * 40 * attack / 50 / attackingPokemon.getDefense() * statusMod + 2; 
			attackingPokemon.setHitpoints(attackingPokemon.getHitpoints() - (int)damage);
			return;
		} 
		
		rndNum = 1 + (int)(Math.random() * ((10 - 1) + 1)); // [min:1, max:10]
		
		// 10% Chance, dass Feuer-, Elektro-, Eis-, Gift-Attacken einen Status auslösen
		// Nur wenn das Pokemon bisher keinen primären Status hat und keine Immunität gegen die Attacke
		if (rndNum == 10 && typeMod != 0 && defendingPokemon.getAil1() == null) {
			switch (attackType) {
			case "fire":
				defendingPokemon.setAil1("BRN");
				break;
			case "electric":
				defendingPokemon.setAil1("PAR");
				defendingPokemon.setInitiative((int)(defendingPokemon.getInitiative() * 0.5));
				break;
			case "ice":
				defendingPokemon.setAil1("FRZ");
				break;
			case "poison":
				defendingPokemon.setAil1("PSN");
				break;
			default:
				break;
			}
		}
		
		// Wenn angreifer brennt verursacht er halben Schaden und bei LichtSchild/Reflektor zusätzlich halben Schaden
		if(attackingPokemon.getAil1() != null && attackingPokemon.getAil1().equals("BRN")) {
			statusMod *= 0.5;
			//  #### if Lichtschild statusMod *= 0.5;
		}
		
		// Wenn Angriff STAB ist, dann Schaden um 1.5 erhöht
		// STAB = Same Type Attack Bonus
		if(attackType.equals(pokemonType1) || attackType.equals(pokemonType2)) {
			isSTAB = true;
			damage = damage * 1.5;
		}
		
		// Unterscheide zwischen verschiedenen Angriffsklassen
		switch (attackClass) {
		case "physical":
			damage = damage * attack / 50 / defense * statusMod + 2;
			break;
		case "special":
			damage = damage * spAttack / 50 / spDefense * statusMod + 2;
			break;
		case "status":
			explanationMng.addExplanationBeforeDamage(attackingPokemon.getName() + " used a status attack:<br>");
			damage = 0;
			switch (attackEffect) {
			case "brn":
				if(defendingPokemon.getAil1() == null) {
					defendingPokemon.setAil1("BRN");
				}
				explanationMng.addExplanationBeforeDamage(nameDefender + " was burned.<br>");
				break;
			case "par":
				rndNum = 1 + (int)(Math.random() * ((100 - 1) + 1)); // [min:1, max:100]
				if(rndNum >= 10 && defendingPokemon.getAil1() == null) {
					defendingPokemon.setAil1("PAR");
					defendingPokemon.setInitiative((int) (defendingPokemon.getInitiative() * 0.5));
					explanationMng.addExplanationBeforeDamage(nameDefender + " was paralysed.<br>");
				}
				break;
			case "psn":
				if(defendingPokemon.getAil1() == null) {
					defendingPokemon.setAil1("PSN");
					explanationMng.addExplanationBeforeDamage(nameDefender + " was poisoned.<br>");
				}
				break;
			case "psn2":
				if(defendingPokemon.getAil1() == null) {
					defendingPokemon.setAil1("PSN2");
					explanationMng.addExplanationBeforeDamage(nameDefender + " was badly poisoned.<br>");
				}
				break;
			case "slp":
				if(defendingPokemon.getAil1() == null) {
					defendingPokemon.setAil1("SLP");
					explanationMng.addExplanationBeforeDamage(nameDefender + " falls asleep.<br>");
				}
				break;
			case "conf":
				if(defendingPokemon.getAil2() == null || !defendingPokemon.getAil2().contains("conf")) {
					defendingPokemon.setAil2(defendingPokemon.getAil2() + "conf");
					explanationMng.addExplanationBeforeDamage(nameDefender + " was confused.<br>");
				}
				break;
			case "leech":
				if(defendingPokemon.getAil2() != null && !defendingPokemon.getAil2().contains("leech")) {
					defendingPokemon.setAil2(defendingPokemon.getAil2() + "leech");
					explanationMng.addExplanationBeforeDamage("A seed was planted on " + nameAttacker + ".<br>");
				}
				break;
			case "heal":
				attackingPokemon.setHitpoints((int) (attackingPokemon.getHitpoints() + (attackingPokemon.getMaxHitpoints() * 0.4)));
				if(attackingPokemon.getMaxHitpoints() < attackingPokemon.getHitpoints()) {
					attackingPokemon.setHitpoints(attackingPokemon.getMaxHitpoints());
				}
				explanationMng.addExplanationBeforeDamage(nameAttacker + " healed itself by " + (int)(attackingPokemon.getMaxHitpoints() * 0.4) + ".<br>");
				break;
			case "ref":
				//Reflektor
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s set up a reflector (not implemented).<br>");
				break;
			case "ls":
				//Lichtschild
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s set up a light screen (not implemented).<br>");
				break;
			case "ab":
				attackingPokemon.setAttack((int) (attackingPokemon.getAttack() * 1.5));
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s attack was increased.<br>");
				break;
			case "db":
				attackingPokemon.setDefense((int) (attackingPokemon.getDefense() * 1.5));
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s defense was increased.<br>");
				break;
			case "sab":
				attackingPokemon.setSpAttack((int) (attackingPokemon.getSpAttack() * 1.5));
				explanationMng.addExplanationBeforeDamage(nameAttacker + " special attack was increased.<br>");
				break;
			case "sdb":
				attackingPokemon.setSpDefense((int) (attackingPokemon.getSpDefense() * 1.5));
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s special defense was increased.<br>");
				break;
			case "sb":
				attackingPokemon.setInitiative((int) (attackingPokemon.getInitiative() * 1.5));
				explanationMng.addExplanationBeforeDamage(nameAttacker + "'s speed was increased.<br>");
				break;
			case "ad":
				defendingPokemon.setAttack((int) (defendingPokemon.getAttack() * 0.5));
				explanationMng.addExplanationBeforeDamage(nameDefender + "'s attack was decreased.<br>");
				break;
			case "dd":
				defendingPokemon.setDefense((int) (defendingPokemon.getDefense() * 0.5));
				explanationMng.addExplanationBeforeDamage(nameDefender + "'s defense was decreased.<br>");
				break;
			case "sad":
				defendingPokemon.setSpAttack((int) (defendingPokemon.getSpAttack() * 0.5));
				explanationMng.addExplanationBeforeDamage(nameDefender + "'s special attack was decreased.<br>");
				break;
			case "sdd":
				defendingPokemon.setSpDefense((int) (defendingPokemon.getSpDefense() * 0.5));
				explanationMng.addExplanationBeforeDamage(nameDefender + "'s special defense was decreased.<br>");
				break;
			case "sd":
				defendingPokemon.setInitiative((int) (defendingPokemon.getInitiative() * 0.5));
				explanationMng.addExplanationBeforeDamage(nameDefender + "'s speed was decreased.<br>");
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
				
		// Schaden mit Anfälligkeit/Resistenz multiplizieren
		// typeMod kann 0, 0.25, 0.5, 1, 2 oder 4 sein
		damage = damage * typeMod;
		
		defendingPokemon.setHitpoints(defendingPokemon.getHitpoints() - (int)damage);
		
		explanationMng.addExplanationDamage(attackingTeam, defendingTeam, pokemonAttack, isSTAB, typeMod, damage);
	}
	
	// Methode für die Berechnung des Schadens durch einen Status
	private void calculateStatusDamage(Pokemonteam activeTeam, Pokemonteam passiveTeam, PokemonExplanationManager explanationMng) {
		
		Pokemon activePokemon = activeTeam.getPokemon().get(0);
		Pokemon passivePokemon = passiveTeam.getPokemon().get(0);
		
		int maxHp = activePokemon.getMaxHitpoints();
		double hpFracture = maxHp * (1.0 / 8.0);
		String ail1 = activePokemon.getAil1();
		String ail2 = activePokemon.getAil2();
		
		// Prüfe ob Pokemon einen Status hat
		if(ail1 != null) {
			
			// Wenn Pokemon an Verbrennung-Status leidet, füge Schaden in Höhe von 1/8 max. HP
			if(ail1.equals("BRN")) {
				activePokemon.setHitpoints((int) (activePokemon.getHitpoints() - hpFracture));
				explanationMng.addExplanationBeforeDamage(activePokemon.getName() + " took damage due to burning.<br>");
			}
			
			// Berechne Schaden Schweres Gift
			//	Heavy Poison x*1/16 x inkrementiert sich um 1 je Runde wo Pokemon draussen; Reset auf 1 bei Wechsel
			if(ail1.equals("PSN2") || ail1.equals("PSN")) {
				activePokemon.setHitpoints((int) (activePokemon.getHitpoints() - hpFracture));
				explanationMng.addExplanationBeforeDamage(activePokemon.getName() + " took damage due to poison.<br>");
			}
		}
		
		// Wenn Egelsamen-Status, dann Schaden in Höhe von 1/8 max. HP
		// Gegner wird um Betrag des Schadens geheilt
		if(ail2 != null &&  ail2.contains("leech")) {
			activePokemon.setHitpoints((int) (activePokemon.getHitpoints() - hpFracture));
			passivePokemon.setHitpoints((int) (passivePokemon.getHitpoints() + hpFracture));
			explanationMng.addExplanationBeforeDamage(activePokemon.getName() + " took damage due to leech seed.<br>"
					+ passivePokemon.getName() + " was healed a bit due to leech seed.");
		}
		explanationMng.addExplanationDefeat(passiveTeam, checkPokemonDefeated(passiveTeam.getPokemon()), checkTeamDefeated(passiveTeam.getPokemon()));
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
