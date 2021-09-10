package agents;

import java.text.ParseException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;


public class PlanAgent extends Agent {
	
	
	public PlanAgent() {
	}
	
	protected void setup() {
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
//				ACLMessage msgSend = new ACLMessage(ACLMessage.INFORM);
//				ACLMessage msgReceive = myAgent.receive();
//				Player agentPlayer = null;
//				
//				//Check if message is not null and contains a content block
//				if(msgReceive != null && msgReceive.getContent() != null) {
//					//JOptionPane.showMessageDialog(null, getLocalName() + " received: " + msg.getContent());
//					System.out.println(getLocalName() + " received message from: " + msgReceive.getSender().getLocalName());
//					
//					try {
//						//Get sent object
//						agentPlayer = ((Player) msgReceive.getContentObject());
//						
//					} catch (UnreadableException e) {
//						e.printStackTrace();
//					}
//					
//					//Send generated plan
//					msgSend.setContent(generatePlan(agentPlayer).toString());
//					msgSend.addReceiver(new AID("Gamer", AID.ISLOCALNAME));
//					send(msgSend);
//				} else {
//					block();
//				}
			}
		});
	}
	
	protected void takeDown() {
	}
}

