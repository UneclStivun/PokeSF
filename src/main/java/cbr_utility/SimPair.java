package cbr_utility;

public class SimPair {
private Double sim;
	
	private String simAtt;
	
	public SimPair(String simAtt, Double sim) {
		this.simAtt = simAtt;
		this.sim = sim;
	}
	
	//Getter Setter
	public Double getSim() {
		return sim;
	}

	public void setSim(Double sim) {
		this.sim = sim;
	}

	public String getSimAtt() {
		return simAtt;
	}

	public void setSimAtt(String simAtt) {
		this.simAtt = simAtt;
	}
}
