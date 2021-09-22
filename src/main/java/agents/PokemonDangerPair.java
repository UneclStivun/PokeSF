package agents;

import pokemon.Pokemon;

public class PokemonDangerPair {
	private Pokemon pokemon;
	
	private double danger;
	
	public PokemonDangerPair(Pokemon pokemon, double danger) {
		this.pokemon = pokemon;
		this.danger = danger;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public double getDanger() {
		return danger;
	}

	public void setDanger(double danger) {
		this.danger = danger;
	}
}
