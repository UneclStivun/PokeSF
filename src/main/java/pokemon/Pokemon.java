package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	private String ail1;
	
	private String ail2;
	
	public Pokemon(){this.attacks = new ArrayList<Attack>();}
	
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
		this.attacks = new ArrayList<Attack>();
	}
	
	// Add attack objects to List
	public void addAttacks(Attack attack1, Attack attack2, Attack attack3, Attack attack4) {
		attacks.add(attack1);
		attacks.add(attack2);
		attacks.add(attack3);
		attacks.add(attack4);
	}
	
	//translate DB result to Attacklist
	public void translateAttacksFromDB(String attacksS) {
		//Split all attacks into their own list entries
		List<String> attackList = new ArrayList<>(Arrays.asList(attacksS.split(";")));
		//Iterate through each attack to split into components of attack
	      for(int i = 0; i < attackList.size(); i++) {
	          List<String> att = new ArrayList<>(Arrays.asList(attackList.get(i)
	        		  .substring(1, attackList.get(i).length() - 1).replaceAll(" ","").split(",")));
	          //create Attack object and add to Pokemon // might be unstable further checks needed
	          Attack attack = new Attack();
	          attack.setAttacktype(att.get(0));
	          attack.setAttackclass(att.get(1));
	          attack.setDmg(Integer.parseInt(att.get(2)));
	          if(att.size() > 3) {
	        	  attack.setEffect(att.get(3));
	          }
	          attacks.add(attack);
	      }
	}
	
	//Transform attacklist into string for casebase and database
	public String attackListToString() {
		String attacksS = "";
		if(attacks.size() > 0) {
			for(int i = 0; i < attacks.size(); i++) {
				attacksS += "{";
				attacksS += attacks.get(i).getAttacktype() + ",";
				attacksS += attacks.get(i).getAttackclass() + ",";
				attacksS += attacks.get(i).getDmg() + ",";
				attacksS += attacks.get(i).getEffect() + "};";
			}
			//return without the last commata
			attacksS = attacksS.substring(0, attacksS.length() -1);
		}
		return attacksS;
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
