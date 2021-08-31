package cbr_Pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.DoubleDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.model.SymbolDesc;
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
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	


//
//					for (Instance i : collection) {
//
//						Projekt projectNew = new Projekt(i.getName().toString(),
//								Integer.parseInt(i.getAttributes().get(anzahlA).getValueAsString()),
//								Integer.parseInt(i.getAttributes().get(anzahlT).getValueAsString()),
//								Integer.parseInt(i.getAttributes().get(dauer).getValueAsString()),
//								Integer.parseInt(i.getAttributes().get(mainComp).getValueAsString()),
//								i.getAttributes().get(complications).getValueAsString(),
//								Double.parseDouble(i.getAttributes().get(costG).getValueAsString()),
//								Double.parseDouble(i.getAttributes().get(costPA).getValueAsString()),
//								Double.parseDouble(i.getAttributes().get(costPM).getValueAsString()),
//								i.getAttributes().get(projektA).getValueAsString(),
//								i.getAttributes().get(goals).getValueAsString());
//						projectList.add(projectNew);
//					}
//					
//					// saving results in ArrayList and adding the sim results to the cases
//					if (result.size() > 0) {
//						for (int i = 0; i < result.size(); i++) {
//							//set Result Similarity anew with compare String method
//							System.out.println("Vergleich Projekt mit Case" + i);
//							
//							//get every iteration the newset similaritys for pasting purposes -> Works! -> Ask Jakob about further splittzing of the query Attributes
//							double currSim = result.get(i).getSecond().getValue();
//							double simA = RetrievalUtil.compareStrings(project.getGoals(), result.get(i).getFirst().getAttForDesc(goals).getValueAsString());
//							double simB = RetrievalUtil.compareStrings(project.getComplications(),result.get(i).getFirst().getAttForDesc(complications).getValueAsString());
//							//set Sim of each case with added weighted String similarity
//							result.get(i).setSecond(Similarity.get(weightSum(currSim, simA, simB)));						
//							
//							//add to arraylist
//							Case cNew = new Case(result.get(i).getFirst().getName(), result.get(i).getSecond().getValue());
//							resultCases.add(cNew);
//							
//							String answer = "I found " + result.get(i).getFirst().getName() + " with a sim of " + result.get(i).getSecond().getValue();
//							System.out.println(answer);
//						}
//					}

}
