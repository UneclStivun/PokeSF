package pokemon;

	//	Burn 1/8 Dmg bis Heilung
	//	Poison 1/8 Dmg bis Heilung
	//	Heavy Poison x*1/16 x inkrementiert sich um 1 je Runde wo Pokemon draussen; Reset auf 1 bei Wechsel
	//	Confusion 1/3 selber Schaden (40 Physisch Normal) zufügen, anstelle Attacke (2-5 Runden)
	//	Egelsamen 1/8 Debuff + Heilung um gleichen Betrag
	
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
