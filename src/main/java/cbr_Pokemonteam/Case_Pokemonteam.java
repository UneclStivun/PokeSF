package cbr_Pokemonteam;

import pokemon.Pokemonteam;

public class Case_Pokemonteam {
	//Membervariables
	String casename;
	
	double sim;
	
	Pokemonteam pokemonteam;
	
	public Case_Pokemonteam(String casename, double sim) {
		this.casename = casename;
		this.sim = sim;
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
}
