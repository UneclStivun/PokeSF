package cbr_Pokemonteam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pokemon.Pokemonteam;
import pokemon.TypeTableSupport;

public class Case_Pokemonteam {
	//Membervariables
	private String casename;
	
	private double sim;
	
	private Pokemonteam pokemonteam;
	
	private List<String> resistances;
	
	private List<String> weaknesses;
	
	private List<String> immunities;
	
	public Case_Pokemonteam(String casename, double sim, Pokemonteam pokemonteam) {
		this.casename = casename;
		this.sim = sim;
		this.pokemonteam = pokemonteam;
	}
	
	public void pokemonAttsToString() {
		resistances = new ArrayList<String>();
		weaknesses = new ArrayList<String>();
		immunities = new ArrayList<String>();
		Map<String, Integer> defAff = TypeTableSupport.checkTeamDefenseAffinities(pokemonteam);
		
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
	
	
	//Getter, Setter functions
	public String getCasename() {
		return casename;
	}

	public void setCasename(String casename) {
		this.casename = casename;
	}

	public double getSim() {
		return sim;
	}

	public void setSim(double sim) {
		this.sim = sim;
	}

	public Pokemonteam getPokemonteam() {
		return pokemonteam;
	}

	public void setPokemonteam(Pokemonteam pokemonteam) {
		this.pokemonteam = pokemonteam;
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
