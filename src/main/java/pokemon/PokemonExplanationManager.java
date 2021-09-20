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
	
	// Rundenanzahl in den Erkl�rungen darstellen
	public void addExplanationRound() {
		
		// Wenn Rundenanzahl in der Session gespeichert, setze diese, ansonsten ist round = 1
		int round = session.getAttribute("round") != null ? (Integer)session.getAttribute("round") : 1; 
		
		// Erweitere Erkl�rungen f�r den Nutzer
		explanationString += "<b><u>Round " + round + ":</u></b><br>";
		
		// Erh�he Rundenanzahl und speichere in der Session
		session.setAttribute("round", round+1);
		
		// F�ge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);
	}
	
	// Ausgetauschte Pokemon in den Erkl�rungen darstellen
	public void addExplanationSwitch(Pokemonteam poketeam, int position) {
		
		explanationString += poketeam.getPokemon().get(position).getName() 
				+ " switched to " + poketeam.getPokemon().get(0).getName();
		
		explanationString += " (from " + poketeam.getTeamname() + ").<br>";
				
		// F�ge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);
	}
	
	// Gebe Richtung der Schadensverteilung an
	public void addExplanationDamage(Pokemonteam attackingTeam, Pokemonteam defendingTeam, int position) {
		
		explanationString += attackingTeam.getPokemon().get(0).getName() + " (from " + attackingTeam.getTeamname() + ") "
				+ "attacked<br>" + defendingTeam.getPokemon().get(0).getName() + " (from " + defendingTeam.getTeamname() + ").<br>";
		
		// F�ge Erweiterung der Session hinzu
		session.setAttribute("explanation", explanationString);
	}
	
	// Notiere, falls Pokemon oder gesamtes Team besiegt wurde
	public void addExplanationDefeat(Pokemonteam poketeam, boolean isPokemonDefeated, boolean isTeamDefeated) {
		
		// Wenn aktuelles Pokemon besiegt, f�ge entsprechende Nachricht hinzu
		if(isPokemonDefeated) {
			explanationString += poketeam.getPokemon().get(0).getName() + " is K.O.";
			explanationString += " (from " + poketeam.getTeamname() + ").<br>";
		}
		
		// Ist das gesamte Team besiegt, f�ge entsprechende Nachricht hinzu
		if(isTeamDefeated) {
			explanationString += poketeam.getTeamname() + " has been defeated";
			explanationString += " (from " + poketeam.getTeamname() + ").<br>";
		}
		
		session.setAttribute("explanation", explanationString);
	}
}
