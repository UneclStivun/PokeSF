package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Pokemonteam {
	//Membervariables
	private List<Pokemon> pokemon;
	
	private String teamname;
	
	private int teamid;
	
	private List<Integer> sumUpVals;
	
	private List<String> resistances;
	
	private List<String> weaknesses;
	
	private List<String> immunities;
	
	public Pokemonteam(String teamname, int teamid) {
		this.teamname = teamname;
		this.teamid = teamid;
		this.pokemon = new ArrayList<Pokemon>();
		this.sumUpVals = new ArrayList<Integer>();
	}

	public Pokemonteam() {
		this.pokemon = new ArrayList<Pokemon>();
		this.sumUpVals = new ArrayList<Integer>();
	}
	
	public void pokemonAttsToString() {
		resistances = new ArrayList<String>();
		weaknesses = new ArrayList<String>();
		immunities = new ArrayList<String>();
		Pokemonteam team = new Pokemonteam();
		team.setPokemon(pokemon);
		Map<String, Integer> defAff = TypeTableSupport.checkTeamDefenseAffinities(team);
		
		for(Map.Entry<String, Integer> entry : defAff.entrySet()) {
			if(entry.getKey().contains("res") && entry.getValue() > 0) {
				resistances.add(entry.getKey());
			}
			if(entry.getKey().contains("weak") && entry.getValue() > 0) {
				weaknesses.add(entry.getKey());
			}
			if(entry.getKey().contains("immune") && entry.getValue() > 0) {
				immunities.add(entry.getKey());
			}
		}
		int counter = 1;
		String type = "";
		for(int i = 0; i < resistances.size(); i++) {
			type = resistances.get(i).replace("res", "");
			//iterating through the list to get all doubles
			for(int j = 0; j < resistances.size(); j++) {
				if(resistances.get(i).equals(resistances.get(j))) {
					resistances.remove(j);
					counter++;
				}
			}
			
			if(i < resistances.size()) {
				resistances.set(i, type + ": " + counter);
			}
		}
		
		for(int i = 0; i < weaknesses.size(); i++) {
			type = weaknesses.get(i).replace("weak", "");
			//iterating through the list to get all doubles
			for(int j = 0; j < weaknesses.size(); j++) {
				if(weaknesses.get(i).equals(weaknesses.get(j))) {
					weaknesses.remove(j);
					counter++;
				}
			}
			
			if(i < weaknesses.size()) {
			weaknesses.set(i, type + ": " + counter);
			}
		}
		
		for(int i = 0; i < immunities.size(); i++) {
			type = immunities.get(i).replace("immune", "");
			//iterating through the list to get all doubles
			for(int j = 0; j < immunities.size(); j++) {
				if(immunities.get(i).equals(immunities.get(j))) {
					immunities.remove(j);
					counter++;
				}
			}
			
			if(i < immunities.size()) {
			immunities.set(i, type + ": " + counter);
			}
		}
	}

	public List<Pokemon> getPokemon() {
		return pokemon;
	}

	public void setPokemon(List<Pokemon> pokemon) {
		this.pokemon = pokemon;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public int getTeamid() {
		return teamid;
	}

	public void setTeamid(int teamid) {
		this.teamid = teamid;
	}

	public List<Integer> getSumUpVals() {
		return sumUpVals;
	}

	public void setSumUpVals() {
		int hp = 0;
		int att = 0;
		int spAtt = 0;
		int def = 0;
		int spDef = 0;
		int ini = 0;
		for(int i = 0; i < pokemon.size(); i++) {
			hp += pokemon.get(i).getHitpoints();
			att += pokemon.get(i).getAttack();
			spAtt += pokemon.get(i).getSpAttack();
			def += pokemon.get(i).getDefense();
			spDef += pokemon.get(i).getSpDefense();
			ini +=	pokemon.get(i).getInitiative();
		}
		sumUpVals.add(hp);
		sumUpVals.add(att);
		sumUpVals.add(spAtt);
		sumUpVals.add(def);
		sumUpVals.add(spDef);
		sumUpVals.add(ini);
	}

	public List<String> getResistances() {
		return resistances;
	}

	public void setResistances(List<String> resistances) {
		this.resistances = resistances;
	}

	public List<String> getWeaknesses() {
		return weaknesses;
	}

	public void setWeaknesses(List<String> weaknesses) {
		this.weaknesses = weaknesses;
	}

	public List<String> getImmunities() {
		return immunities;
	}

	public void setImmunities(List<String> immunities) {
		this.immunities = immunities;
	}
}
