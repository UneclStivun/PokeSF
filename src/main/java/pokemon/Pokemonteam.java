package pokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokemonteam {
	//Membervariables
	private List<Pokemon> pokemon;
	
	private String teamname;
	
	private int teamid;
	
	public Pokemonteam(String teamname, int teamid) {
		this.teamname = teamname;
		this.teamid = teamid;
		this.pokemon = new ArrayList<Pokemon>();
	}

	public Pokemonteam() {
		this.pokemon = new ArrayList<Pokemon>();
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
	
	
}
