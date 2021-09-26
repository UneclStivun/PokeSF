package agents;

import java.io.IOException;

import org.json.JSONObject;

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

/**Klasse als Schnittstelle zwischen AgentPlaner und JSP
 * @author Tobias Brakel
 * */

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
					
					JSONObject plan = new JSONObject(msgReceive.getContent());
					
					String action = plan.getString("action");
					
					// Send message to webSocket eventListener
					try {
						if(action.equals("attack")) {
							chooseAttack();
						}
						
						session.getBasicRemote().sendText(plan.toString());
						
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
	
	private void chooseAttack() {
		
	}
}

