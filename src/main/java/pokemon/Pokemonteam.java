package pokemon;

import java.util.ArrayList;
import java.util.List;

public class Pokemonteam {
	//Membervariables
	private List<Pokemon> pokemon;
	
	private String teamname;
	
	private int teamid;
	
	private List<Integer> sumUpVals;
	
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
	
	
}
