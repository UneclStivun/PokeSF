package agents;

import java.io.IOException;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class UpdateAgent extends Agent {

	protected void setup() {

		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
//				final ACLMessage msgSend = new ACLMessage(ACLMessage.INFORM); // sending message
//				final ACLMessage msgReceive = receive(); // receiving message
//				Player agentPlayer1 = null;
//				Player agentPlayer2 = null;
//
//				// check if message is not null
//				if (msgReceive != null && msgReceive.getContent() != null) {
//
//					try {
//						// Check for which player the information will be updated and send
//						if (obj.getJSONObject("player").getInt("playerID") == 1) {
//							msgSend.setContentObject(updatePlayer(agentPlayer1, obj));
//						} else {
//							msgSend.setContentObject(updatePlayer(agentPlayer2, obj));
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					
//					// Send message to other agent
//					msgSend.addReceiver(new AID("Planer", AID.ISLOCALNAME));
//					send(msgSend);
//					// if message is null, it will be blocked
//				} else {
//					block();
//				}
			}
		});
	}
	
	protected void takeDown() {
		doSuspend();
		doActivate();
	}
}
