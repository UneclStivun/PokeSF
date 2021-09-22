package agents;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import pokemon.Pokemon;


public class AgentPlaner extends Agent {
	
	private Pokemon actualUserPokemon;
	
	private Pokemon actualAgentPokemon;
	
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
					JSONObject teamJson = new JSONObject(msgReceive.getContent());
					String action = teamJson.getString("action");
					
					Pokemon userPokemon1 = new Gson().fromJson(teamJson.getJSONObject("userPokemon0").toString(), Pokemon.class);
					Pokemon userPokemon2 = new Gson().fromJson(teamJson.getJSONObject("userPokemon1").toString(), Pokemon.class);
					Pokemon userPokemon3 = new Gson().fromJson(teamJson.getJSONObject("userPokemon2").toString(), Pokemon.class);
					Pokemon userPokemon4 = new Gson().fromJson(teamJson.getJSONObject("userPokemon3").toString(), Pokemon.class);
					Pokemon userPokemon5 = new Gson().fromJson(teamJson.getJSONObject("userPokemon4").toString(), Pokemon.class);
					Pokemon userPokemon6 = new Gson().fromJson(teamJson.getJSONObject("userPokemon5").toString(), Pokemon.class);
					Pokemon agentPokemon1 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon0").toString(), Pokemon.class);
					Pokemon agentPokemon2 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon1").toString(), Pokemon.class);
					Pokemon agentPokemon3 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon2").toString(), Pokemon.class);
					Pokemon agentPokemon4 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon3").toString(), Pokemon.class);
					Pokemon agentPokemon5 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon4").toString(), Pokemon.class);
					Pokemon agentPokemon6 = new Gson().fromJson(teamJson.getJSONObject("enemyPokemon5").toString(), Pokemon.class);
					
					List<Pokemon> agentPokemonList = new ArrayList<Pokemon>();
					agentPokemonList.add(agentPokemon1);
					agentPokemonList.add(agentPokemon2);
					agentPokemonList.add(agentPokemon3);
					agentPokemonList.add(agentPokemon4);
					agentPokemonList.add(agentPokemon5);
					agentPokemonList.add(agentPokemon6);
					
					List<Pokemon> userPokemonList = new ArrayList<Pokemon>();
					userPokemonList.add(userPokemon1);
					userPokemonList.add(userPokemon2);
					userPokemonList.add(userPokemon3);
					userPokemonList.add(userPokemon4);
					userPokemonList.add(userPokemon5);
					userPokemonList.add(userPokemon6);
					
					if(action.equals("forceSwitch")) {
						// Wechsel das Pokemon
						System.out.println("Agent forceSwitch mit: " + searchForSwitch(agentPokemonList));
						msgSend.setContent(searchForSwitch(agentPokemonList));
					} else {
						// Wähle eine Aktion
						statusUpdate(agentPokemonList);
						
						msgSend.setContent(generatePlan());
					}

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
	
	private void statusUpdate(List<Pokemon> agentPokemonList) {
		
		// aktualisiere das aktuell käpfende Pokemon
		this.actualAgentPokemon = agentPokemonList.get(0);
		
	}
	
	private String searchForSwitch(List<Pokemon> agentPokemonList) {
		String action = "";
		
		for(int i = 1; i < agentPokemonList.size(); i++) {
			if(agentPokemonList.get(i).getHitpoints() > 0) {
				action = "{action:forceSwitch,position:" + i + "}";
			}
		}
		return action;
	}
	
	private String generatePlan() {
		JSONObject plan = new JSONObject();
		
		String action = "";
		
		if(false) {
			action = "switch";
		} else {
			action = "attack";
		}
		
		plan.put("action", action);
		plan.put("position", 1);
		
		return plan.toString();
	}
}

