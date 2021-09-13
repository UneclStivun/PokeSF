package agents;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class AgentLauncher extends Agent {

	private Runtime rt;
	private Profile profile;
	private AgentContainer mainContainer;
	private ProfileImpl pContainer;
	
	public AgentLauncher() {
		// Get a hold on JADE runtime
		this.rt = Runtime.instance();
		
		// Exit the JVM when there are no more containers around
		this.rt.setCloseVM(true);
		
		// Create a default profile
		this.profile = new ProfileImpl(null, 1099, null);
		
		this.mainContainer = rt.createMainContainer(profile);
		
		// Set the default Profile to start a container
		this.pContainer = new ProfileImpl(null, 1099, null);
	}
	
	// Method to initialize agents
	public void launchAgents() {
		
		// Launching the agents on the main container
		AgentController planAgent;
		AgentController fightAgent;
		AgentController rma;

		try {
			// Create agents
			planAgent = mainContainer.acceptNewAgent("Planer", new AgentPlaner());
			fightAgent = mainContainer.acceptNewAgent("Fighter", new AgentFighter());
			rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
			
			// Start agents
			planAgent.start();
			fightAgent.start();
				//rma.start(); //starting JADE GUI
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
	// Method to end agents
	public void terminateAgents() {
		try {
			mainContainer.suspend();
			mainContainer.kill();
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
}
