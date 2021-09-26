package pokemon;

public class ScorePair {
	//Membervariables
	private Pokemonteam team;
	
	private Pokemon pokemon;
	
	private int score;
	
	public ScorePair(Pokemonteam team, int score) {
		this.team = team;
		this.score = score;
	}
	
	public ScorePair(Pokemon pokemon, int score) {
		this.pokemon = pokemon;
		this.score= score;
	}

	public Pokemonteam getTeam() {
		return team;
	}

	public void setTeam(Pokemonteam team) {
		this.team = team;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
