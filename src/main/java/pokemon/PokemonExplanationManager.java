package pokemon;

import jakarta.servlet.http.HttpSession;

public class PokemonExplanationManager {
	
	/*=== Klassenvariablen ===*/
	private HttpSession session;
	private String explanationString;

	/*=== Konstruktor ===*/
	public PokemonExplanationManager(HttpSession session) {
		this.session = session;
		this.explanationString = session.getAttribute("explanation") != null ? (String) session.getAttribute("explanation") : "";
	}
	
	/*=== Methoden ===*/
	
	// Rundenanzahl in den Erklärungen darstellen
	public void addExplanationRound() {
		
		// Wenn Rundenanzahl in der Session gespeichert, setze diese, ansonsten ist round = 1
		int round = session.getAttribute("round") != null ? (Integer)session.getAttribute("round") : 1; 
		
		// Erweitere Erklärungen für den Nutzer
		explanationString += "<b><u>Round " + round + ":</u></b><br>";
		
		// Erhöhe Rundenanzahl und speichere in der Session
		session.setAttribute("round", round+1);
		
		// Füge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);
	}
	
	// Ausgetauschte Pokemon in den Erklärungen darstellen
	public void addExplanationSwitch(Pokemonteam poketeam, int position) {
		
		explanationString += poketeam.getPokemon().get(position).getName() 
				+ " switched to " + poketeam.getPokemon().get(0).getName();
		
		explanationString += " (from " + poketeam.getTeamname() + ").<br>";
				
		// Füge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);
	}
	
	public void addExplanationBeforeDamage(String addition) {
		
		explanationString += addition;
		
		// Füge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);	}
	
	// Gebe Richtung der Schadensverteilung an
	public void addExplanationDamage(Pokemonteam attackingTeam, Pokemonteam defendingTeam, Attack pokemonAttack, boolean isSTAB, double typeMod, double damage) {
		
		Pokemon attackingPokemon = attackingTeam.getPokemon().get(0);
		Pokemon defendingPokemon = defendingTeam.getPokemon().get(0);
		
		String defendingType2 = defendingPokemon.getType2() == "null" ? "" : defendingPokemon.getType2();
		
		String titleExplanation = "";
		
		// Prüfe ob Attacke kein Statusangriff war
		if(!pokemonAttack.getAttackclass().equals("status")) {
			if(pokemonAttack.getAttackclass().equals("physical")) {
				
				titleExplanation += "Attack is physical, therefore the attributes"
						+ " attack (" + attackingPokemon.getAttack() + ") of " + attackingPokemon.getName() 
						+ " and defense (" + defendingPokemon.getDefense() + ") of " + defendingPokemon.getName()
						+ " are used.\n";
				
			} else if(pokemonAttack.getAttackclass().equals("special")) {
				
				titleExplanation += "Attack is special, therefore the attributes"
						+ " special attack (" + attackingPokemon.getSpAttack() + ") of " + attackingPokemon.getName() 
						+ " and special defense (" + defendingPokemon.getSpDefense() + ") of " + defendingPokemon.getName()
						+ " are used.\n";
			} else {
				return;
			}
			
			if(isSTAB) {
				titleExplanation += "Attack was same element as pokemon type. Therefore damage was increased by x1.5\n";
			}
			
			titleExplanation += "Damage is multiplicated by x" + typeMod + " because attack type is " + pokemonAttack.getAttacktype()
					+ " and defending pokemon type is " + defendingPokemon.getType1() + " " + defendingType2 + ".\n";
			
			titleExplanation += "Damge was " + (int)damage + ".";
			
			
			explanationString += attackingTeam.getPokemon().get(0).getName() + " (from " + attackingTeam.getTeamname() + ") "
					+ "attacked " + defendingTeam.getPokemon().get(0).getName() + " (from " + defendingTeam.getTeamname() + "). ";
			
			explanationString += "<span title='"
					+ titleExplanation
					+ "'><u style='color:blue;'>Explanation</u></span><br>";
			
			// Füge Erweiterung der Session hinzu
			session.setAttribute("explanation", explanationString);
		}
	}
	
	// Notiere, falls Pokemon oder gesamtes Team besiegt wurde
	public void addExplanationDefeat(Pokemonteam poketeam, boolean isPokemonDefeated, boolean isTeamDefeated) {
		
		// Wenn aktuelles Pokemon besiegt, füge entsprechende Nachricht hinzu
		if(isPokemonDefeated) {
			explanationString += poketeam.getPokemon().get(0).getName() + " is K.O.";
			explanationString += " (from " + poketeam.getTeamname() + ").<br>";
		}
		
		// Ist das gesamte Team besiegt, füge entsprechende Nachricht hinzu
		if(isTeamDefeated) {
			explanationString += poketeam.getTeamname() + " has been defeated";
			explanationString += " (from " + poketeam.getTeamname() + ").<br>";
		}
		
		session.setAttribute("explanation", explanationString);
	}
}
