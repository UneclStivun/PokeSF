package pokemon;

public class ScorePair {
	//Membervariables
	private Pokemonteam team;
	
	private int score;
	
	public ScorePair(Pokemonteam team, int score) {
		this.team = team;
		this.score = score;
	}

	public Pokemonteam getTeam() {
		return team;
	}

	public void setTeam(Pokemonteam team) {
		this.team = team;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
