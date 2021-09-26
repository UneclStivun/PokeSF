package cbr_Pokemonteam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cbr_utility.SimPair;

/**Klasse um die Ergebnisse (Similarities) nachvollziehbar zusammenzurechnen und vergleichbar zu machen
 * @author Steven Oberle
 * */

public class CBRExplanationManagerPokemonteam {
	//Membervariables
	private double hpSim;
	
	private double attSim;
	
	private double spAttSim;
	
	private double defSim;
	
	private double spDefSim;
	
	private double iniSim;
	
	private double typeSim;
	
	private List<SimPair> topSimAtt;
	
	public CBRExplanationManagerPokemonteam() {
		topSimAtt = new ArrayList<SimPair>();
	}
	
	//this methods sums up all valid similarites of the CBR process
	public double sumUpSimilarities() {
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
		//divide similarity 
		sim = sim/validAtts;
		if (Double.isNaN(sim)) {
			sim = 0.0;
		}
		return sim;
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
		
		topSimAtt.add(s1);
		topSimAtt.add(s2);
		topSimAtt.add(s3);
		topSimAtt.add(s4);
		topSimAtt.add(s5);
		topSimAtt.add(s6);
		topSimAtt.add(s7);
		
		//Comparator method in order to sort from highest to lowest
		topSimAtt.sort(Comparator.comparingDouble(SimPair::getSim).reversed());
	}
	
	//Getter Setter methods
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
		this.spDefSim = spDefSim;
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

	public List<SimPair> getTopSimAtt() {
		return topSimAtt;
	}

	public void setTopSimAtt(List<SimPair> topSimAtt) {
		this.topSimAtt = topSimAtt;
	}
}
