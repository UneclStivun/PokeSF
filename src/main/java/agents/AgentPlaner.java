package agents;

import org.json.JSONObject;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class AgentPlaner extends Agent {
	
	
	public AgentPlaner() {
	}
	
	protected void setup() {
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage msgSend = new ACLMessage(ACLMessage.INFORM);
				ACLMessage msgReceive = myAgent.receive();
				
				//Check if message is not null and contains a content or block
				if(msgReceive != null && msgReceive.getContent() != null) {
					
					// Get message of AgentFighter
					JSONObject pokeJson = new JSONObject(msgReceive.getContent());
					
					msgSend.setContent("");
					msgSend.addReceiver(new AID("Fighter", AID.ISLOCALNAME));
					send(msgSend);
				} else {
					block();
				}
			}
		});
	}
	
	protected void takeDown() {
	}
}

