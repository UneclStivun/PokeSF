package cbr_utility;

import java.util.ArrayList;
import java.util.List;

public class ExplanationManager {
	//Membervariablen
	private double hpSim;
	
	private double attSim;
	
	private double spAttSim;
	
	private double defSim;
	
	private double spDefSim;
	
	private double iniSim;
	
	private double typeSim;
	
	private double nameSim;
	
	private double attacksSim;
	
	private List<SimPair> topSimAtt;
	
	public ExplanationManager () {
		topSimAtt = new ArrayList<SimPair>();
	}
	
	//Summarize all similarities
	public double sumSimilarities() {
		double sim = 0.0;
		double validAtts = 0.0;
		if(Double.compare(hpSim, 0.0) != 0) {
			sim += hpSim;
			validAtts++;
		}
		if(Double.compare(attSim, 0.0) != 0) {
			sim += attSim;
			validAtts++;
		}
		if(Double.compare(spAttSim, 0.0) != 0) {
			sim += spAttSim;
			validAtts++;
		}
		if(Double.compare(defSim, 0.0) != 0) {
			sim += defSim;
			validAtts++;
		}
		if(Double.compare(spDefSim, 0.0) != 0) {
			sim += spDefSim;
			validAtts++;
		}
		if(Double.compare(iniSim, 0.0) != 0) {
			sim += iniSim;
			validAtts++;
		}
		if(Double.compare(typeSim, 0.0) != 0) {
			sim += typeSim;
			validAtts++;
		}
		if(Double.compare(nameSim, 0.0) != 0) {
			sim += nameSim;
			validAtts++;
		}
		if(Double.compare(attacksSim, 0.0) != 0) {
			sim += attacksSim;
			validAtts++;
		}
		return sim/validAtts;
	}
	
	//sorting the attributes after highest similarity
	public void calculateHighestSim() {
		SimPair s1 = new SimPair("Hitpoints: ", hpSim);
		SimPair s2 = new SimPair("Attack: ", attSim);
		SimPair s3 = new SimPair("Special Attack: ", spAttSim);
		SimPair s4 = new SimPair("Defense: ", defSim);
		SimPair s5 = new SimPair("Special Defense: ", spDefSim);
		SimPair s6 = new SimPair("Initiative: ", iniSim);
		SimPair s7 = new SimPair("Type: ", typeSim);
		SimPair s8 = new SimPair("Name: ", nameSim);
		SimPair s9 = new SimPair("Attacks: ", attacksSim);
		
		topSimAtt.add(s1);
		topSimAtt.add(s2);
		topSimAtt.add(s3);
		topSimAtt.add(s4);
		topSimAtt.add(s5);
		topSimAtt.add(s6);
		topSimAtt.add(s7);
		topSimAtt.add(s8);
		topSimAtt.add(s9);
	}
	
	public void toConsole() {
		for(int i = 0; i < topSimAtt.size(); i++) {
			System.out.println(topSimAtt.get(i).getSimAtt() + topSimAtt.get(i).getSim());
		}
	}

	public double getHpSim() {
		return hpSim;
	}

	public void setHpSim(double hpSim) {
		this.hpSim = hpSim;
	}

	public double getAttSim() {
		return attSim;
	}

	public void setAttSim(double attSim) {
		this.attSim = attSim;
	}

	public double getSpAttSim() {
		return spAttSim;
	}

	public void setSpAttSim(double spAttSim) {
		this.spAttSim = spAttSim;
	}

	public double getDefSim() {
		return defSim;
	}

	public void setDefSim(double defSim) {
		this.defSim = defSim;
	}

	public double getSpDefSim() {
		return spDefSim;
	}

	public void setSpDefSim(double spDefSim) {
		spDefSim = spDefSim;
	}

	public double getIniSim() {
		return iniSim;
	}

	public void setIniSim(double iniSim) {
		this.iniSim = iniSim;
	}

	public double getTypeSim() {
		return typeSim;
	}

	public void setTypeSim(double typeSim) {
		this.typeSim = typeSim;
	}

	public double getNameSim() {
		return nameSim;
	}

	public void setNameSim(double nameSim) {
		this.nameSim = nameSim;
	}

	public double getAttacksSim() {
		return attacksSim;
	}

	public void setAttacksSim(double attacksSim) {
		this.attacksSim = attacksSim;
	}

	public List<SimPair> getTopSimAtt() {
		return topSimAtt;
	}

	public void setTopSimAtt(List<SimPair> topSimAtt) {
		this.topSimAtt = topSimAtt;
	}
}
