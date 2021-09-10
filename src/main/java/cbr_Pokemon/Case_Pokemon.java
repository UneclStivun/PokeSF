package cbr_Pokemon;

import pokemon.Pokemon;

public class Case_Pokemon {
	//Membervariables
	private String casename;
	
	private double sim;
	
	private Pokemon pokemon;
	
	public Case_Pokemon(String casename, double sim, Pokemon pokemon) {
		this.casename = casename;
		this.sim = sim;
		this.pokemon = pokemon;
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

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
}
