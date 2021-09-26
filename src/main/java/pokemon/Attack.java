package pokemon;

public class Attack {
	// Membervariables
	private String attacktype;
	
	// Status, Special, Physical 
	private String attackclass;
	
	// Damage
	private int dmg;
	
	//Effect of Attack
	private String effect;
	
	public Attack() {}
	
	public Attack(String attacktype, String status, int dmg, String effect) {
		this.attacktype = attacktype;
		this.attackclass = status;
		this.dmg = dmg;
		this.effect = effect;
	}

	public String getAttacktype() {
		return attacktype;
	}

	public void setAttacktype(String attacktype) {
		this.attacktype = attacktype;
	}

	public String getAttackclass() {
		return attackclass;
	}

	public void setAttackclass(String attackclass) {
		this.attackclass = attackclass;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}
}
