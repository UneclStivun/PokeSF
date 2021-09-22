package agents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;

import database.DatabaseManipulator;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.TypeTableSupport;


public class AgentPlaner extends Agent {
	
	private Pokemon actualUserPokemon;
	
	private Pokemon actualAgentPokemon;
	
	private List<Pokemon> agentPokemonList;
	
	private AgentHelper ah;
	
	private static int roundAg = 1;
	
	private static int roundUs = 1;
	
	public AgentPlaner() {
		ah = new AgentHelper();
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

					List<Pokemon> userPokemonList = new ArrayList<Pokemon>();
					userPokemonList.add(userPokemon1);
					userPokemonList.add(userPokemon2);
					userPokemonList.add(userPokemon3);
					userPokemonList.add(userPokemon4);
					userPokemonList.add(userPokemon5);
					userPokemonList.add(userPokemon6);

					agentPokemonList = new ArrayList<Pokemon>();
                    agentPokemonList.add(agentPokemon1);
                    agentPokemonList.add(agentPokemon2);
                    agentPokemonList.add(agentPokemon3);
                    agentPokemonList.add(agentPokemon4);
                    agentPokemonList.add(agentPokemon5);
                    agentPokemonList.add(agentPokemon6);

					//Updating agent view on current enemy Pokemon
					statusUpdateUser(userPokemonList);
					statusUpdateAgent(agentPokemonList);
					
					if(action.equals("forceSwitch")) {
						// Wechsel das Pokemon
						System.out.println("Agent forceSwitch mit: " + searchForSwitch(agentPokemonList));
						msgSend.setContent(searchForSwitch(agentPokemonList));
					} else {
						// W�hle eine Aktion
						statusUpdateAgent(agentPokemonList);
						statusUpdateUser(userPokemonList);
						
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
	
	//update with actual user Pokemon
	private void statusUpdateUser(List<Pokemon> userPokemonList) {
		if(actualUserPokemon != null) {
			if(actualUserPokemon.getName() != userPokemonList.get(0).getName()) {
				roundUs = 1;
			} else {
				roundUs++;
			}
		}
		// aktualisiere das aktuell k�mpfende user Pokemon
		this.actualUserPokemon = userPokemonList.get(0);
		//update Agent world with "possible" new seen Pokemon
		ah.visiblePokemon(userPokemonList.get(0));
	}
	
	//update with actual Agent Pokemon
	private void statusUpdateAgent(List<Pokemon> agentPokemonList) {
		// aktualisiere das akutell k�mpfende Agenten Pokemon
		this.actualAgentPokemon = agentPokemonList.get(0);
	}
	
	//forces pokemon switch
	private String searchForSwitch(List<Pokemon> agentPokemonList) {
		String action = "";
		
		for(int i = 1; i < agentPokemonList.size(); i++) {
			if(agentPokemonList.get(i).getHitpoints() > 0) {
				action = "{action:forceSwitch,position:" + i + "}";
			}
		}
		return action;
	}
	
	//this method checks the current situation and decides on the best course of action
	private String generatePlan() {
		JSONObject plan = new JSONObject();
		String action = "";
		//determine if other actions besides switch are even possible (Pokemon dead)
		action = getBestAction();	
		//if Planner decided on switching Pokemons out
		if(action.contains("switch")) {
			//switch out pokemon function
			for(int i = 0; i < agentPokemonList.size(); i++) {
				if(action.contains(agentPokemonList.get(i).getName())) {
					plan.put("position", i);
				}
			}
			roundAg = 0;
			plan.put("action", "switch");
		} else {
			int pos = getBestAttack();
			//pick best attack function
			plan.put("action", "attack");
			plan.put("position", pos);
			System.out.println(actualAgentPokemon.getAttacks().size());  
		}
		roundAg++;
		return plan.toString();
	}
	//Plan to get the enemy Pokemon and the user Pokemon
	//check if any attack is useful or rather switch to better Pokemon (check for Types Then Attacks)
	private String getBestAction() {
		double danger = ah.assessPokemonDangerLevel(actualUserPokemon, actualAgentPokemon);
		String action = "attack";
		//List with a danger Assessment 
		List<PokemonDangerPair> dangerList = new ArrayList<PokemonDangerPair>();
		for(int i = 0; i < agentPokemonList.size(); i++) {
			if(agentPokemonList.get(i).getHitpoints() > 0) {
				//get Danger Level for each Pokemon
				 dangerList.add(new PokemonDangerPair(agentPokemonList.get(i),ah.assessPokemonDangerLevel(actualUserPokemon, agentPokemonList.get(i)))); 
			}
		}
		//compare to current Pokemon outside and only switch out if there is enough merit in it
		dangerList.sort(Comparator.comparingDouble(PokemonDangerPair::getDanger));
		for(int i = 0; i < dangerList.size(); i++) {
			//check if pokemon is alive and compare
			if(actualAgentPokemon.getHitpoints() > 0) {
				if((danger - 0.5) > dangerList.get(i).getDanger()) {
					danger = dangerList.get(i).getDanger();
					action = "switch" + dangerList.get(i).getPokemon().getName();
				}
			} else {
				action = "switch" + dangerList.get(0).getPokemon().getName();
			}
		}
		return action;
	}
	
	private int getBestAttack() {
		int pos = 0;
		double hiScore = 0;
		Map<String, Double> defAttUser = TypeTableSupport.checkDefenseAffinities(actualUserPokemon);
		//iterate through all attacks to score them depending on the situation
		for(int i = 0; i < actualAgentPokemon.getAttacks().size(); i++) {
			double score = 0;
			//set Attack for better code visualisation
			Attack att = actualAgentPokemon.getAttacks().get(i);
			//if user Pokemon has no ailment and has its first round
			if(!(actualUserPokemon.getAil1() != null)) {
				if(att.getEffect() != null && roundUs == 1) {
					score++;
				}
			}
			//if user Pokemon has a weakness against agent attack type, the higher the weakness the higher the score
			if(defAttUser.get(att.getAttacktype()) > 1.0) {
				score = score + defAttUser.get(att.getAttacktype());
			}
			//get Attack position with highest score
			if(score > hiScore) {
				hiScore = score;
				pos = i;
			}
		}
		return pos;
	}
}

