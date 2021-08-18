package pokemon;

import java.util.List;
import java.util.Map;

public class Pokemon {
	//Membervariables
	private String name;
	
	private String type1;
	
	private String type2;
	
	private Map<String, Double> attackAffinity;
	
	private Map<String, Double> defenseAffinity;
	
	private int hitpoints;
	
	private int attack;
	
	private int defense;
	
	private int spAttack;
	
	private int spDefense;
	
	private int initiative;
	
	private List<Attack> attacks;
	
	public Pokemon(String name, String type1, String type2,
			int hitpoints, int attack, int defense, int spAttack,
			int spDefense, int initiative) {
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.hitpoints = hitpoints;
		this.attack = attack;
		this.defense = defense;
		this.spDefense = spDefense;
		this.spAttack = spAttack;
		this.initiative = initiative;
	}
	
	//Getter Setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public Map<String, Double> getAttackAffinity() {
		return attackAffinity;
	}

	public void setAttackAffinity(Map<String, Double> attackAffinity) {
		this.attackAffinity = attackAffinity;
	}

	public Map<String, Double> getDefenseAffinity() {
		return defenseAffinity;
	}

	public void setDefenseAffinity(Map<String, Double> defenseAffinity) {
		this.defenseAffinity = defenseAffinity;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public void setHitpoints(int hitpoints) {
		this.hitpoints = hitpoints;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpAttack() {
		return spAttack;
	}

	public void setSpAttack(int spAttack) {
		this.spAttack = spAttack;
	}

	public int getSpDefense() {
		return spDefense;
	}

	public void setSpDefense(int spDefense) {
		this.spDefense = spDefense;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public List<Attack> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Attack> attacks) {
		this.attacks = attacks;
	}
}
