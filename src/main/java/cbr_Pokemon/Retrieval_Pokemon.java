package cbr_Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import pokemon.Pokemon;

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
					pokemonList.add(poke);
				}
				
				//Check if there are any results and then get the similarity from all cases
				if(result.size() > 0) {
					for(int i = 0; i < pokemonList.size();i++) {					
						//sum up similarities from functions with self written String functions
						double currSim = result.get(i).getSecond().getValue();
						//similarity Attacks
						double simA = compareAttacks(pokemon.attackListToString(), result.get(i).getFirst().getAttForDesc(pkAttacks).getValueAsString());
						//similarity Name
						double simN = compareNames(pokemon.getName(), result.get(i).getFirst().getAttForDesc(pkName).getValueAsString());
						//similarityTypes
						double simT = compareTypes(pokemon.getType1(), pokemon.getType2(), 
								result.get(i).getFirst().getAttForDesc(pkType1), result.get(i).getFirst().getAttForDesc(pkType2));
						
						//Then add result to case instance and save as ArrayList
						Case_Pokemon cp = new Case_Pokemon(pokemonList.get(i).getName(), result.get(i).getSecond().getValue());
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
		//Return value
		return resultCasesPokemon;
	}
	
	//sum up all similarities in their specific weights
	private static double weightSum(double currSim, double simA, double simN, double simT) {
		return currSim + simA + simN + simT;
	}

	//compare Pokemonattacks
	public static double compareAttacks(String attackListToString, String valueAsString) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//compare Pokemonnames with a simple Check
	public static double compareNames(String name, String valueAsString) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//compare Pokemontypes via the TypeSupport Class
	private static double compareTypes(String type1, String type2, Attribute attForDesc, Attribute attForDesc2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
