package agents;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.Pokemonteam;
import pokemon.TypeTableSupport;


public class AgentPlaner extends Agent {
	
	private Pokemon actualUserPokemon;
	
	private Pokemon actualAgentPokemon;
	
	private List<Pokemon> agentPokemonList;
	
	private List<Pokemon> agentPokemonListMax;
	
	private Pokemonteam userTeam;
	
	private AgentHelper ah;
	
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
					
					if(!(agentPokemonListMax != null)) {
						agentPokemonListMax = new ArrayList<Pokemon>();
						agentPokemonListMax.add(agentPokemon1);
						agentPokemonListMax.add(agentPokemon2);
						agentPokemonListMax.add(agentPokemon3);
						agentPokemonListMax.add(agentPokemon4);
						agentPokemonListMax.add(agentPokemon5);
						agentPokemonListMax.add(agentPokemon6);
					}
					
					agentPokemonList = new ArrayList<Pokemon>();
                    agentPokemonList.add(agentPokemon1);
                    agentPokemonList.add(agentPokemon2);
                    agentPokemonList.add(agentPokemon3);
                    agentPokemonList.add(agentPokemon4);
                    agentPokemonList.add(agentPokemon5);
                    agentPokemonList.add(agentPokemon6);
                    
                  //extract attacks from JSON Object and parse it to agentPokemon atleast once
                    if(!(agentPokemonList.get(0).getAttacks().size() > 0)) {
        				extractAttacks(teamJson);
                    }
                  

					//Updating agent view on current enemy Pokemon
					statusUpdateUser(userPokemonList, action);
					statusUpdateAgent(agentPokemonList);
					
					if(action.equals("forceSwitch")) {
						// Wechsel das Pokemon
						msgSend.setContent(searchForSwitch(agentPokemonList));
					} else {
						// Wähle eine Aktion
						msgSend.setContent(generatePlan());
					}
					
					msgSend.addReceiver(new AID("Fighter", AID.ISLOCALNAME));
					send(msgSend);
				} else {
					block();
				}
			}
			
			//extract attacks for agent pokemon
			private void extractAttacks(JSONObject teamJson) {
				for(int i = 0; i < 6; i++) {
					//create a new List for each Pokemon
					List<Attack> attacks = new ArrayList<Attack>();
					JSONObject attack = teamJson.getJSONObject("enemyPokemon" + i).getJSONObject("attackList");
					for(int j = 1; j < 5; j++) {
						Attack att = new Attack();
						att.setAttacktype(attack.getString("attackType" + j));
						att.setAttackclass(attack.getString("attackClass" + j));
						if(!attack.getString("attackEffect" + j).equals("none")) {
							att.setEffect(attack.getString("attackEffect" + j));
						}
						//add extracted Attack to list
						attacks.add(att);
					}
					agentPokemonList.get(i).setAttacks(attacks);
				}
			}
		});
	}
	
	protected void takeDown() {
	}
	
	//update with actual user Pokemon
	private void statusUpdateUser(List<Pokemon> userPokemonList, String action) {
		//first round of Pokemon add User´s start Pokemon to recognized team
		if(!(userTeam != null)) {
			userTeam = new Pokemonteam();
			userTeam.getPokemon().add(userPokemonList.get(0));
		}
		
		if(actualUserPokemon != null) {
			if(actualUserPokemon.getName() != userPokemonList.get(0).getName()) {
				roundUs = 1;
			} else {
				roundUs++;
			}
		}
		// aktualisiere das aktuell kämpfende user Pokemon
		this.actualUserPokemon = userPokemonList.get(0);
		//update Agent world with "possible" new seen Pokemon
		ah.visiblePokemon(userPokemonList.get(0));
	}

	//update with actual Agent Pokemon
	private void statusUpdateAgent(List<Pokemon> agentPokemonList) {
		// aktualisiere das akutell kämpfende Agenten Pokemon
		this.actualAgentPokemon = agentPokemonList.get(0);
	}
	
	//forces pokemon switch
	private String searchForSwitch(List<Pokemon> agentPokemonList) {
		String action = "";
		action = getBestAction(); //get the best remaining Pokemon to switch into
		System.out.println("Bestes Pokemon: " + action);
		for(int i = 0; i < agentPokemonList.size(); i++) {
			if(action.contains(agentPokemonList.get(i).getName())) {
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
			plan.put("action", "switch");
		} else {
			int pos = getBestAttack();
			//pick best attack function
			plan.put("action", "attack");
			plan.put("position", pos);
		}
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
	
	//this method scores all attacks and returns the position of the attack with the highest score
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
				//prefer Effect Attack in first rounds over following rounds
				if(att.getEffect() != null && roundUs == 1) {
					score = score +4;
				}
				if(att.getEffect() != null && roundUs != 1) {
					score = score +2;
				}
			}
			//if user Pokemon has a weakness against agent attack type, the higher the weakness the higher the score
			if(defAttUser.get(att.getAttacktype()) > 1.0) {
				score = score + defAttUser.get(att.getAttacktype());
			}
			
			//if user pokemon is resistant to attack lower score
			if(defAttUser.get(att.getAttacktype()) == 0.5) {
				score--;
			}
			
			//if Pokemon is immune to attack no reason to use it
			if(defAttUser.get(att.getAttacktype()) < 0.5) {
				score = score - 10;
			}
			//check if Attack is a STAB
			if(att.getAttacktype().equals(actualAgentPokemon.getType1())) {
				score++;
			}
			//check for second type STAB
			if(actualAgentPokemon.getType2() != null && att.getAttacktype().equals(actualAgentPokemon.getType2())) {
				score++;
			}
			
			//check if heal is needed (below 40%)
			if(att.getEffect() != null ) {
				for(int j = 0; j < agentPokemonListMax.size(); j++) {
					if(actualAgentPokemon.getName().equals(agentPokemonListMax.get(j).getName())) {
						if(((Double.parseDouble(actualAgentPokemon.getHitpoints() + "") / Double.parseDouble("" + agentPokemonListMax.get(j).getMaxHp()) * 100.00) < 40 )
							&& att.getEffect().equals("heal") ) {
							score = score + 4;
						}
					}
				}
			}
			
			//if enemy pokemon is bound by status prefer buff
			if(actualUserPokemon.getAil1() != null && (actualUserPokemon.getAil1().equals("PAR") || actualUserPokemon.getAil1().equals("FRZ") || actualUserPokemon.getAil1().equals("SLP"))) {
				if(att.getEffect() != null) {
					String[] buffs = {"ab", "db", "sab", "sdb" , "sb" ,"ad", "dd", "sad", "sdd", "sd"};
					for(int j = 0; j < buffs.length; j++) {
						if(att.getEffect().equals(buffs[i])) {
							score = score + 8;
						}
					}
				}
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

