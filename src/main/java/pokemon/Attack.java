package pokemon;

//Funktion use Attack on Pokemon to calculate effects

//Bei Attacken 10% Feuer, Elektro, Gift, Eis
	//	Paralysis 90% Wahrscheinlichkeit, Initative 1/2, 25% Chance dass Pokemon nicht angreifen kann
	//	Frozen kann nur ausgewechselt werden ,20% Wahrscheinlichkeit Status entfernen 
	//	Burn 1/8 Dmg bis Heilung
	//	Poison 1/8 Dmg bis Heilung
	//	Heavy Poison x*1/16 x inkrementiert sich um 1 je Runde wo Pokemon draussen; Reset auf 1 bei Wechsel
	//	Sleep Pokemon schläft ein (1-3 Runden) (70% Wahrscheinlichkeit)
	//	Confusion 1/3 selber Schaden (40 Physisch Normal) zufügen, anstelle Attacke (2-5 Runden)
	//	Egelsamen 1/8 Debuff + Heilung um gleichen Betrag
	//Neue Idee: Status bei Pokemon festhalten (Status beibehalten bei Wechsel etc.), Aktuelles Pokemon auf Active setzen weil
	//nur bei diesem auch die Statüsschen wirken
	//Attacke mit flat Schaden [Fire,100]
	//Status Attacke ohne Schaden aber Debuff (verhindert gegnerischen Schaden)
	//Attacke mit flat Schaden plus Debuff (Verbrennung macht je Runde Tick Dmg)
	//Attacke mit flat Schaden plus Debuff (Attacken verhindert vom Gegner (Paralyse, Eingefroren))
	//Attacke mit flat schaden aber mit Buff (attacken werden stärker)
	//Attacke ohne flat Schaden aber mit Debuff (Dmg over time, Dmg & Heal over Time)
	//Attacken ohne flat Schaden aber mit Debuff (Attzacken verhindern, Einschlafen)
	//Attacken ohne flat Schaden mit Buff (Attacken werden stärker)
	//Attacken ohne flat Schaden mit Debuff plus Buff (Egelsamen)
	//Attacken ohne flat Schaden aber mit Heilung
	
	//Anforderungen an Berechnung: Vergleich der Diffferenzänderung zwischen beiden Pokemon durch Anwendung der Attacke
	//Attacken mit Debuff (Feuer, Eis, Gift, Elektro) geben mit wahrscheinlichkeit Debuff plus flat schaden

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
