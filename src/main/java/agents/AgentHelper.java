package agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import database.DatabaseManipulator;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.Pokemonteam;
import pokemon.TypeTableSupport;

/**Klasse um die Agenteninteraktion zu unterstützen und Funktionen auszulagern
 * @author Steven Oberle
 * */

public class AgentHelper {
	//Membervariables
	private Pokemonteam enemyteam;
	
	private List<List<Pokemon>> similarPokemon; 
	
	
	public AgentHelper() {
		this.similarPokemon = new ArrayList<List<Pokemon>>();
		this.enemyteam = new Pokemonteam();
	}
	
	//adds the userPokemon to a Pokemonteam which visualizes the agents world 
	public void visiblePokemon(Pokemon pokemon) {
		boolean unique = true;
		//check if agent has already seen the current User Pokemon before
		for(int i = 0; i < similarPokemon.size(); i++) {
			if(similarPokemon.get(i).get(0).getName().equals(pokemon.getName())) {
				unique = false;
			}
		}
		
		if(unique) {
			//get all similar Pokemon with the same name
			DatabaseManipulator dbm = new DatabaseManipulator();
			//add them to a list
			similarPokemon.add(dbm.getAllSimilarPokemon(pokemon));
			//get Pokemon with all known stats but null the List of Attackss
			Pokemon pk = pokemon;
			pk.setAttacks(new ArrayList<Attack>());
			//add to enemyTeam
			enemyteam.getPokemon().add(pk);
		}
	}
	
	//this method assesses a Pokemon with a Danger Level to a specific userPokemon
	public double assessPokemonDangerLevel(Pokemon userPoke, Pokemon agentPoke) {
		double danger = 0.0;
		//compare Types => Advantage / Disadvantage
		Map<String, Double> defAffAgent = TypeTableSupport.checkDefenseAffinities(agentPoke);
		Map<String, Double> defAffUser = TypeTableSupport.checkDefenseAffinities(userPoke);
		
		//check if user Pokemon has Type advantage against our defense
		if(defAffAgent.get(userPoke.getType1()) == 2.0){
			danger++;
		}
		if(userPoke.getType2() != null) {
			if(defAffAgent.get(userPoke.getType2()) == 2.0) {
				danger++;
			}
		}
		//check if agent Pokemon has Type advantage regarding defense
		if(defAffAgent.get(userPoke.getType1()) == 0.5){
			danger--;
		}
		if(userPoke.getType2() != null) {
			if(defAffAgent.get(userPoke.getType2()) == 0.5) {
				danger--;
			}
		}
		
		int pos = 0;
		//iterate through all similar Pokemon
		for(int i = 0; i < similarPokemon.size(); i++) {
			//until one of the Pokemon with the same name was found
			if(userPoke.getName().equals(similarPokemon.get(i).get(0).getName())) {
				pos = i;
				i = similarPokemon.size();
			}
		}
		//weakC determines a score between a userPokemon and the currengt enemy Pokemon
		double weakC = 0.0;
		//amountAtt determines the amount of all Attacks regarded to get the weakC in order to normalize it
		double amountAtt = 0.0;
		//iterate through all similar Pokemon to the current user Pokemon and list their attack types
		for(int i = 0; i < similarPokemon.get(pos).size();i++) {
			amountAtt += similarPokemon.get(pos).get(i).getAttacks().size();
			for(int j = 0; j < similarPokemon.get(pos).get(i).getAttacks().size(); j++) {
				if(defAffAgent.get(similarPokemon.get(pos).get(i).getAttacks().get(j).getAttacktype()) == 2.0) {
					weakC++;
				}
			}
		}
		
		//check if own pokemon attacks are strong against user Pokemon to lower danger level
		if(defAffUser.get(agentPoke.getType1()) == 2.0) {
			danger--;
		}
		if(agentPoke.getType2() != null) {
			if(defAffUser.get(agentPoke.getType2()) == 2.0) {
				danger--;
			}
		}
		for(int i = 0; i < agentPoke.getAttacks().size(); i++) {
			amountAtt++;
			if(defAffUser.get(agentPoke.getAttacks().get(i).getAttacktype()) == 2.0) {
				weakC--;
			}
		}
		weakC = weakC / amountAtt;
		if(Double.isNaN(weakC)) {
			weakC = 0.0;
		}
		return danger + weakC;
	}

	public Pokemonteam getEnemyteam() {
		return enemyteam;
	}

	public void setEnemyteam(Pokemonteam enemyteam) {
		this.enemyteam = enemyteam;
	}

	public List<List<Pokemon>> getSimilarPokemon() {
		return similarPokemon;
	}

	public void setSimilarPokemon(List<List<Pokemon>> similarPokemon) {
		this.similarPokemon = similarPokemon;
	}
}
