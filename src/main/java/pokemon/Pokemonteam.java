package pokemon;

import java.util.ArrayList;
import java.util.Comparator;
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
	
	//this method calculates the affinities of the object Team
	public void pokemonAttsToString() {
		resistances = new ArrayList<String>();
		weaknesses = new ArrayList<String>();
		immunities = new ArrayList<String>();
		Pokemonteam team = new Pokemonteam();
		team.setPokemon(pokemon);
		Map<String, Integer> defAff = TypeTableSupport.checkTeamDefenseAffinities(team);
		
		for(Map.Entry<String, Integer> entry : defAff.entrySet()) {
			if(entry.getKey().contains("res") && entry.getValue() > 0) {
				resistances.add(entry.getKey() + ": " + entry.getValue());
			}
			if(entry.getKey().contains("weak") && entry.getValue() > 0) {
				weaknesses.add(entry.getKey() + ": " + entry.getValue());
			}
			if(entry.getKey().contains("immune") && entry.getValue() > 0) {
				immunities.add(entry.getKey() + ": " + entry.getValue());
			}
		}
		
		for(int i = 0; i < resistances.size(); i++) {
			resistances.set(i, resistances.get(i).replace("res", ""));
		}
		for(int i = 0; i < weaknesses.size(); i++) {
			weaknesses.set(i, weaknesses.get(i).replace("weak", ""));
		}
		for(int i = 0; i < immunities.size(); i++) {
			immunities.set(i, immunities.get(i).replace("immune", ""));
		}
		resistances.sort(Comparator.naturalOrder());
		weaknesses.sort(Comparator.naturalOrder());
		immunities.sort(Comparator.naturalOrder());
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
