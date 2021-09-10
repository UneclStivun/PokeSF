package agents;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class AgentLauncher extends Agent {

	public void launchAgents() {
		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);

		// Create a default profile
		Profile profile = new ProfileImpl(null, 1099, null);

		AgentContainer mainContainer = rt.createMainContainer(profile);
		//Properties secondaryContainerProperties = new Properties();

		// Set the default Profile to start a container
		ProfileImpl pContainer = new ProfileImpl(null, 1099, null);

		// Other Container
		// AgentContainer cont = rt.createAgentContainer(pContainer);
		//JadeGateway.init(Agent.class.getName(), null, secondaryContainerProperties);

		// Launching the agents on the main container
		AgentController rma;
		AgentController communicationAgent;
		AgentController planAgent;
		AgentController fightAgent;

		try {
			// Create agents
			communicationAgent = mainContainer.acceptNewAgent("Communicator", new UpdateAgent());
			planAgent = mainContainer.acceptNewAgent("Planer", new PlanAgent());
			fightAgent = mainContainer.acceptNewAgent("Fighter", new FightAgent());
			rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
			
			// Start agents
			communicationAgent.start();
			planAgent.start();
			fightAgent.start();
			//rma.start(); //starting JADE GUI
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
}
