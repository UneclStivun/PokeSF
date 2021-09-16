package cbr_Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cbr_utility.ExplanationManager;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.TypeTableSupport;

public class Retrieval_Pokemon {
	
	//static method to set the query attributes for retrieval and get all result
	//cases for a single Pokemon
	public static ArrayList<Case_Pokemon> retrieveSimCases(CaseBaseLoader_Pokemon cbl, Pokemon pokemon) {
		//List that saves all similar cases related to current Pokemon
		ArrayList<Case_Pokemon> resultCasesPokemon = new ArrayList<Case_Pokemon>();
		try {
			//Set Retrieval method and Instance
			Retrieval ret = new Retrieval(cbl.getConcept(), cbl.getCb());
			ret.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
			Instance query = ret.getQueryInstance();
			
			//Query from Casebase
			//Add Attributes to Retrieval Query for preparation
			IntegerDesc hp = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Hitpoints");
			query.addAttribute(hp, hp.getAttribute(pokemon.getHitpoints()));

			IntegerDesc att = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Attack");
			query.addAttribute(att, att.getAttribute(pokemon.getAttack()));
			
			IntegerDesc spAtt = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("SpecialAttack");
			query.addAttribute(spAtt, spAtt.getAttribute(pokemon.getSpAttack()));

			IntegerDesc def = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Defense");
			query.addAttribute(def, def.getAttribute(pokemon.getDefense()));
			
			IntegerDesc spDef = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("SpecialDefense");
			query.addAttribute(spDef, spDef.getAttribute(pokemon.getSpDefense()));
			
			IntegerDesc ini = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Initiative");
			query.addAttribute(ini, ini.getAttribute(pokemon.getInitiative()));

			StringDesc pkType1 = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("Pokemontype1");
			
			StringDesc pkType2 = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("Pokemontype2");
			
			StringDesc pkName = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("Pokemonname");
			
			StringDesc pkAttacks = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("PokemonAttacks");
			
			StringDesc pkDBID = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("DatabaseID");
			
			//calculate similarity for already defined similarity functions to current Pokemon
			List<Pair<Instance, Similarity>> result;
			try {
				//Casefetching and saving to ArrayList<Pokemon>
				result = ret.getRetrievalEngine().retrieve(cbl.getCb(), query);
				Collection<Instance> cases = cbl.getCb().getCases();
				ArrayList<Pokemon> pokemonList = new ArrayList<Pokemon>();
				
				//Create Pokemoninstances from cases of the Casebase
				for(Instance i : cases) {
					Pokemon poke = new Pokemon();
					poke.setName(i.getAttributes().get(pkName).getValueAsString());
					poke.setType1(i.getAttributes().get(pkType1).getValueAsString());
					poke.setType2(i.getAttributes().get(pkType2).getValueAsString());
					poke.setHitpoints(Integer.parseInt(i.getAttributes().get(hp).getValueAsString()));
					poke.setAttack(Integer.parseInt(i.getAttributes().get(att).getValueAsString()));
					poke.setSpAttack(Integer.parseInt(i.getAttributes().get(spAtt).getValueAsString()));
					poke.setDefense(Integer.parseInt(i.getAttributes().get(def).getValueAsString()));
					poke.setSpDefense(Integer.parseInt(i.getAttributes().get(spDef).getValueAsString()));
					poke.setInitiative(Integer.parseInt(i.getAttributes().get(ini).getValueAsString()));
					poke.translateAttacksFromDB(i.getAttributes().get(pkAttacks).getValueAsString());
					poke.setDatabaseID(Integer.parseInt(i.getAttributes().get(pkDBID).getValueAsString()));
					pokemonList.add(poke);
				}
				
				//Check if there are any results and then get the similarity from all cases
				//Create new ExplanationManager object and save each calculated similarity 
				//in order to create more explanations
				if(result.size() > 0) {
					for(int i = 0; i < pokemonList.size();i++) {					
						//sum up similarities from functions with self written String functions
						ExplanationManager expMan = new ExplanationManager();
						
						//check if HP Similarity is lower than 0
						if(cbl.getHpFct()
								.calculateSimilarity(pokemon.getHitpoints(), pokemonList.get(i).getHitpoints()).getValue() >= 0) {
							expMan.setHpSim(cbl.getHpFct()
									.calculateSimilarity(pokemon.getHitpoints(), pokemonList.get(i).getHitpoints()).getValue());
						} else {
							expMan.setHpSim(0.0);
						}
						
						//check if Attack Similarity is lower than 0
						if(cbl.getAttFct()
								.calculateSimilarity(pokemon.getAttack(), pokemonList.get(i).getAttack()).getValue() >= 0) {
							expMan.setAttSim(cbl.getAttFct()
									.calculateSimilarity(pokemon.getAttack(), pokemonList.get(i).getAttack()).getValue());
						}else {
							expMan.setAttSim(0.0);
						}
						
						//check if Special Attack Similarity is lower than 0
						if(cbl.getSpAttFct()
								.calculateSimilarity(pokemon.getSpAttack(), pokemonList.get(i).getSpAttack()).getValue() >= 0) {
							expMan.setSpAttSim(cbl.getSpAttFct()
									.calculateSimilarity(pokemon.getSpAttack(), pokemonList.get(i).getSpAttack()).getValue());
						} else {
							expMan.setSpAttSim(0.0);
						}
						
						
						//check if Defense Similarity is lower than 0
						if(cbl.getDefFct()
								.calculateSimilarity(pokemon.getDefense(), pokemonList.get(i).getDefense()).getValue() >= 0) {
							expMan.setDefSim(cbl.getDefFct()
									.calculateSimilarity(pokemon.getDefense(), pokemonList.get(i).getDefense()).getValue());
						} else {
							expMan.setDefSim(0.0);
						}
						
						//check if Special Defense Similarity is lower than 0
						if(cbl.getSpDefFct()
								.calculateSimilarity(pokemon.getSpDefense(), pokemonList.get(i).getSpDefense()).getValue() >= 0) {
							expMan.setSpDefSim(cbl.getSpDefFct()
									.calculateSimilarity(pokemon.getSpDefense(), pokemonList.get(i).getSpDefense()).getValue());
						} else {
							expMan.setSpDefSim(0.0);
						}
						
						//check if Initiative Similarity is lower than 0
						if(cbl.getIniFct()
								.calculateSimilarity(pokemon.getInitiative(), pokemonList.get(i).getInitiative()).getValue() >= 0) {
							expMan.setIniSim(cbl.getIniFct()
									.calculateSimilarity(pokemon.getInitiative(), pokemonList.get(i).getInitiative()).getValue());
						} else {
							expMan.setIniSim(0.0);
						}
						
						//selfwritten Similarity functions
						//Attacks
						expMan.setAttacksSim(compareAttacks(pokemon, pokemonList.get(i)) / 9.0);
						//Pokemonname
						if(pokemon.getName() != null) {
							expMan.setNameSim(compareNames(pokemon.getName(), pokemonList.get(i).getName()));
						} else {
							expMan.setNameSim(0.0);
						}
						//Pokemontype
						expMan.setTypeSim(compareTypes(pokemon, pokemonList.get(i)));
						//Then add result to case instance and save as ArrayList
						expMan.calculateHighestSim();
						//System.out.println("The current Similarity for Case " + (i+1) + " is: " + currSim);
						Case_Pokemon cp = new Case_Pokemon(pokemonList.get(i).getName(), expMan.sumSimilarities(), pokemonList.get(i));
						cp.setPokemon(pokemonList.get(i));
						resultCasesPokemon.add(cp);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Fail1 " + e.getMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Fail2 " + e.getMessage());
		}
		//sort List descending by Similarity
		sortResultCases(resultCasesPokemon);
		//Return value
		return resultCasesPokemon;
	}

	//compare Pokemonattacks
	public static double compareAttacks(Pokemon userP, Pokemon cbrP) {
		double sim = 0.0;
		//if attacks are not empty get attacks with highest sim and summarize it
		if(userP.getAttacks().size() > 0) {
			for(int i = 0; i < userP.getAttacks().size(); i++) {
				sim += getMostSimilarAttack(userP.getAttacks().get(i), cbrP.getAttacks());
			}
			//divide all sim points with the amount of searched attacks multiplied by 4
			sim = sim / (userP.getAttacks().size() * 4) + 0.01;
		}
		return sim;
	}
	
	public static double getMostSimilarAttack(Attack userAtt, List<Attack> cbrPList) {
		double sim = 0.0;
		double highestSim = 0.0;
		Map<Double, Integer> simAttList = new HashMap<Double, Integer>();
		for(int i = 0; i < cbrPList.size(); i++) {
			sim = 0.0;
			if(userAtt.getAttacktype().equals(cbrPList.get(i).getAttacktype())) {
				sim+= 2;
			}
			if(userAtt.getAttackclass().equals(cbrPList.get(i).getAttackclass())) {
				sim+= 1;
			}
			try {
				if(userAtt.getEffect().equals(cbrPList.get(i).getEffect())) {
					sim+= 1;
				}
			} catch (Exception e) {
				//Either UserAttack has no Effect or CBRPokemonAttack
			}
			simAttList.put(sim, i);
			if(highestSim < sim) {
				highestSim = sim;
			}
		}	
		return highestSim;
	}
	
	//compare Pokemonnames with a simple Check
	public static double compareNames(String name, String valueAsString) {
		return name.equals(valueAsString) ? 1.0 : 0.0;
	}
	
	//compare Pokemontypes via the TypeSupport Class
	//compare both their attack and their defense affinities
	private static double compareTypes(Pokemon userP, Pokemon cbrP) {
		Double sim = 0.0;
		Map<String, Double> defUserP =	TypeTableSupport.checkDefenseAffinities(userP);
		Map<String, Double> attUserP =	TypeTableSupport.checkAttackAffinities(userP);
		Map<String, Double> defCBRP =	TypeTableSupport.checkDefenseAffinities(cbrP);
		Map<String, Double> attCBRP =	TypeTableSupport.checkAttackAffinities(cbrP);
		
		List<String> typeKeys = new ArrayList<String>();
		//UserPokemon type values
		for (Map.Entry<String, Double> entry : attCBRP.entrySet()) {
			typeKeys.add(entry.getKey());
		}
		
		//Comparing both attack and defense values 
		//1.0 if match else its 0.0 for all 30 values
		for(int i = 0; i < typeKeys.size(); i++) {
			Double c1 = defUserP.get(typeKeys.get(i));
			Double c2 = defCBRP.get(typeKeys.get(i));
			
			if(Double.compare(c1, c2) == 0) {
				sim++;
			}
			c1 = attUserP.get(typeKeys.get(i));
			c2 = attCBRP.get(typeKeys.get(i));		
			if(Double.compare(c1, c2) == 0) {
				sim++;
			}
		}
		sim = sim/30.0;
		return sim;
	}
	
	public static void sortResultCases(ArrayList<Case_Pokemon> resultCases) {
		Collections.sort(resultCases, new Comparator<Case_Pokemon>() {
			@Override
			public int compare(Case_Pokemon case1, Case_Pokemon case2) {
				// TODO Auto-generated method stub
				return (case1.getSim() > case2.getSim()) ? -1 : (case1.getSim() < case2.getSim()) ? 1 : 0;
			}
		});
	}
}
