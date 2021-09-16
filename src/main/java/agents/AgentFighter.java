package agents;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.gateway.JadeGateway;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/websocketendpoint")
public class AgentFighter extends Agent {
	
	
	public AgentFighter() {
	}
	
private static Session session;
	
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
		System.out.println("websocket error\nCheck if websocket refers to project name in websocket.js.");
	}
	
	@OnOpen
	public void onOpen(Session session){
		System.out.println("Open Connection");
		AgentFighter.session = session;
	}
	
	@OnClose
	public void onClose(){
		System.out.println("Close Connection");
	}
	
	@OnMessage
	public void onMessage(String message) throws InterruptedException, IOException {
		
		try {
			// Agent action after receiving pokemon data
			// Transfers data to AgentPlaner
			JadeGateway.execute(new OneShotBehaviour() {
				public void action() {
					ACLMessage msgSend = new ACLMessage(ACLMessage.INFORM);
					msgSend.setContent(message);
					msgSend.addReceiver(new AID("Planer", AID.ISLOCALNAME));
					myAgent.send(msgSend);
  				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void setup() {
		
		addBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage msgReceive = myAgent.receive();
				
				// Check if message is not null and contains a content or block
				if(msgReceive != null && msgReceive.getContent() != null) {
					
					// Send message to webSocket eventlistener
					try {
						session.getBasicRemote().sendText("Kommt das durch?");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					block();
				}
			}
		});
	}
	
	protected void takeDown() {
	}
}

