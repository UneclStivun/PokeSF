package cbr_Pokemonteam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.retrieval.Retrieval;
import de.dfki.mycbr.core.retrieval.Retrieval.RetrievalMethod;
import de.dfki.mycbr.core.similarity.Similarity;
import de.dfki.mycbr.util.Pair;
import pokemon.Pokemonteam;
import pokemon.TypeTableSupport;

/**Klasse um den Retrieval Prozess des Pokemonteams umzusetzen
 * @author Steven Oberle
 * */

public class Retrieval_Pokemonteam {
	//static method to set the query attributes for retrieval and get all result
	//cases for a single Pokemonteam
	public static List<Case_Pokemonteam> retrieveSimCases(CaseBaseLoader_Pokemonteam cbl, Pokemonteam team, List<Pokemonteam> allTeams) {
		//List to save all similar cases for user pokemonteam
		List<Case_Pokemonteam> teamCases = new ArrayList<Case_Pokemonteam>();
		try {
			//set Retrieval and method
			Retrieval ret = new Retrieval(cbl.getConcept(), cbl.getCb());
			ret.setRetrievalMethod(RetrievalMethod.RETRIEVE_SORTED);
			Instance query = ret.getQueryInstance();
			
			//sumUp all Pokemonattributes
			team.setSumUpVals();
			
			//Query from Casebase
			//set attributes
			IntegerDesc hp = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Hitpoints");
			query.addAttribute(hp, hp.getAttribute(team.getSumUpVals().get(0)));

			IntegerDesc att = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Attack");
			query.addAttribute(att, att.getAttribute(team.getSumUpVals().get(1)));
			
			IntegerDesc spAtt = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("SpecialAttack");
			query.addAttribute(spAtt, spAtt.getAttribute(team.getSumUpVals().get(2)));

			IntegerDesc def = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Defense");
			query.addAttribute(def, def.getAttribute(team.getSumUpVals().get(3)));
			
			IntegerDesc spDef = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("SpecialDefense");
			query.addAttribute(spDef, spDef.getAttribute(team.getSumUpVals().get(4)));
			
			IntegerDesc ini = (IntegerDesc) cbl.getConcept().getAllAttributeDescs().get("Initiative");
			query.addAttribute(ini, ini.getAttribute(team.getSumUpVals().get(5)));
			
			StringDesc teamId = (StringDesc) cbl.getConcept().getAllAttributeDescs().get("TeamId");
			
			//calculate similarity for already defined similarity functions to current Pokemonteam
			List<Pair<Instance, Similarity>> result;
			try {
				//Casefetching and saving to ArrayList<Pokemon>
				result = ret.getRetrievalEngine().retrieve(cbl.getCb(), query);
				Collection<Instance> cases = cbl.getCb().getCases();
				List<Pokemonteam> pokemonteamList = new ArrayList<Pokemonteam>();
				
				//Create Pokemoninstances from cases of the Casebase
				for(Instance i : cases) {
					Pokemonteam team_cbr = new Pokemonteam();
					team_cbr.setTeamname(i.getName());
					int id = Integer.parseInt(i.getAttributes().get(teamId).getValueAsString());
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(hp).getValueAsString()));
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(att).getValueAsString()));
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(spAtt).getValueAsString()));
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(def).getValueAsString()));
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(spDef).getValueAsString()));
					team_cbr.getSumUpVals().add(Integer.parseInt(i.getAttributes().get(ini).getValueAsString()));
					team_cbr.setTeamid(id);
					//search via id in allTeams in order to get the pokemonList
					for(int j = 0; j < allTeams.size(); j++) {
						if(id == allTeams.get(j).getTeamid()) {
							team_cbr.setPokemon(allTeams.get(j).getPokemon());
						}
					}
					pokemonteamList.add(team_cbr);
				}
				
				//Check if there are any results and then get the similarity from all cases
				//Create new ExplanationManager object and save each calculated similarity 
				//in order to create more explanations
				if(result.size() > 0) {
					//get Userteam defensive affinities once
					List<String> resListUser = new ArrayList<String>();
					List<String> weakListUser = new ArrayList<String>();
					List<String> immuneListUser = new ArrayList<String>();
					//get Userteam offensive affinities once
					List<String> reducedListUser = new ArrayList<String>();
					List<String> strongListUser = new ArrayList<String>();
					List<String> noneListUser = new ArrayList<String>();
					Map<String, Integer> userDef = TypeTableSupport.checkTeamDefenseAffinities(team);
					Map<String, Integer> userAtt = TypeTableSupport.checkTeamAttackAffinities(team);
					
					//save user team defense affinities to List
					for(Map.Entry<String, Integer> entry : userDef.entrySet()) {
						if(entry.getKey().contains("res") && entry.getValue() > 0) {
							resListUser.add(entry.getKey());
						}
						if(entry.getKey().contains("weak") && entry.getValue() > 0) {
							weakListUser.add(entry.getKey());
						}
						if(entry.getKey().contains("immune") && entry.getValue() > 0) {
							immuneListUser.add(entry.getKey());
						}
					}
					//save user attack affinities to List
					for(Map.Entry<String, Integer> entry : userAtt.entrySet()) {
						if(entry.getKey().contains("reduced") && entry.getValue() > 0) {
							reducedListUser.add(entry.getKey());
						}
						if(entry.getKey().contains("strong") && entry.getValue() > 0) {
							strongListUser.add(entry.getKey());
						}
						if(entry.getKey().contains("none") && entry.getValue() > 0) {
							noneListUser.add(entry.getKey());
						}
					}
					//saving all affinity lists to one list because i am lazy
					List<List<String>> userAff = new ArrayList<List<String>>();
					userAff.add(resListUser);
					userAff.add(weakListUser);
					userAff.add(immuneListUser);
					userAff.add(reducedListUser);
					userAff.add(strongListUser);
					userAff.add(noneListUser);
					
					//sum up similarities for each pokemonteam
					for(int i = 0; i < pokemonteamList.size(); i++) {
						CBRExplanationManagerPokemonteam expManT = new CBRExplanationManagerPokemonteam();
						
						//check if HP Similarity is lower than 0
						if(cbl.getSumHpFct()
								.calculateSimilarity(team.getSumUpVals().get(0), pokemonteamList.get(i).getSumUpVals().get(0)).getValue() >= 0) {
							expManT.setHpSim(cbl.getSumHpFct()
									.calculateSimilarity(team.getSumUpVals().get(0), pokemonteamList.get(i).getSumUpVals().get(0)).getValue());
						} else {
							expManT.setHpSim(0.0);
						}
						
						//check if Attack Similarity is lower than 0
						if(cbl.getSumAttFct()
								.calculateSimilarity(team.getSumUpVals().get(1), pokemonteamList.get(i).getSumUpVals().get(1)).getValue() >= 0) {
							expManT.setAttSim(cbl.getSumAttFct()
									.calculateSimilarity(team.getSumUpVals().get(1), pokemonteamList.get(i).getSumUpVals().get(1)).getValue());
						}else {
							expManT.setAttSim(0.0);
						}
						
						//check if Special Attack Similarity is lower than 0
						if(cbl.getSumSpAttFct()
								.calculateSimilarity(team.getSumUpVals().get(2), pokemonteamList.get(i).getSumUpVals().get(2)).getValue() >= 0) {
							expManT.setSpAttSim(cbl.getSumSpAttFct()
									.calculateSimilarity(team.getSumUpVals().get(2), pokemonteamList.get(i).getSumUpVals().get(2)).getValue());
						} else {
							expManT.setSpAttSim(0.0);
						}
						
						
						//check if Defense Similarity is lower than 0
						if(cbl.getSumDefFct()
								.calculateSimilarity(team.getSumUpVals().get(3), pokemonteamList.get(i).getSumUpVals().get(3)).getValue() >= 0) {
							expManT.setDefSim(cbl.getSumDefFct()
									.calculateSimilarity(team.getSumUpVals().get(3), pokemonteamList.get(i).getSumUpVals().get(3)).getValue());
						} else {
							expManT.setDefSim(0.0);
						}
						
						//check if Special Defense Similarity is lower than 0
						if(cbl.getSumSpDefFct()
								.calculateSimilarity(team.getSumUpVals().get(4), pokemonteamList.get(i).getSumUpVals().get(4)).getValue() >= 0) {
							expManT.setSpDefSim(cbl.getSumSpDefFct()
									.calculateSimilarity(team.getSumUpVals().get(4), pokemonteamList.get(i).getSumUpVals().get(4)).getValue());
						} else {
							expManT.setSpDefSim(0.0);
						}
						
						//check if Initiative Similarity is lower than 0
						if(cbl.getSumIniFct()
								.calculateSimilarity(team.getSumUpVals().get(5), pokemonteamList.get(i).getSumUpVals().get(5)).getValue() >= 0) {
							expManT.setIniSim(cbl.getSumIniFct()
									.calculateSimilarity(team.getSumUpVals().get(5), pokemonteamList.get(i).getSumUpVals().get(5)).getValue());
						} else {
							expManT.setIniSim(0.0);
						}
						
						//Selfwritten Similarity functions
						//Typecomparison
						expManT.setTypeSim(compareTeamTypes(userAff, pokemonteamList.get(i)));
						expManT.calculateHighestSim();
						//
						Case_Pokemonteam case_pt = new Case_Pokemonteam(pokemonteamList.get(i).getTeamname(), expManT.sumUpSimilarities(), pokemonteamList.get(i));
						teamCases.add(case_pt);
					}
				}
			} catch (Exception e) {
			System.out.println("Error1: " + e);
			}
		} catch (Exception e) {
			System.out.println("Error2: " + e);
		}
		//sort resulting teamCases List
		sortResultCases(teamCases);
		return teamCases;
	}
	
	//this method compares the team types by listing their affinities and directly comparing them
	private static double compareTeamTypes(List<List<String>> userAff,Pokemonteam pokemonteam) {
		double sim = 0.0;
		//List for comparing both team´s defense affinities
		List<String> resListCbr = new ArrayList<String>();
		List<String> weakListCbr = new ArrayList<String>();
		List<String> immuneListCbr = new ArrayList<String>();
		List<String> reducedListCbr = new ArrayList<String>();
		List<String> strongListCbr = new ArrayList<String>();
		List<String> noneListCbr = new ArrayList<String>();
		List<List<String>> cbrAff = new ArrayList<List<String>>();
		//sum up all Defense affinities for the user Team
		Map<String, Integer> cbrDef = TypeTableSupport.checkTeamDefenseAffinities(pokemonteam);
		Map<String,Integer> cbrAtt = TypeTableSupport.checkTeamAttackAffinities(pokemonteam);
		for(Map.Entry<String, Integer> entry : cbrDef.entrySet()) {
			if(entry.getKey().contains("res") && entry.getValue() > 0) {
				resListCbr.add(entry.getKey());
			}
			if(entry.getKey().contains("weak") && entry.getValue() > 0) {
				weakListCbr.add(entry.getKey());
			}
			if(entry.getKey().contains("immune") && entry.getValue() > 0) {
				immuneListCbr.add(entry.getKey());
			}
		}
		for(Map.Entry<String, Integer> entry : cbrAtt.entrySet()) {
			if(entry.getKey().contains("reduced") && entry.getValue() > 0) {
				reducedListCbr.add(entry.getKey());
			}
			if(entry.getKey().contains("strong") && entry.getValue() > 0) {
				strongListCbr.add(entry.getKey());
			}
			if(entry.getKey().contains("none") && entry.getValue() > 0) {
				noneListCbr.add(entry.getKey());
			}
		}
		//add cbr Lists to a single list for an easier comparison to user List of lists
		cbrAff.add(resListCbr);
		cbrAff.add(weakListCbr);
		cbrAff.add(immuneListCbr);
		cbrAff.add(reducedListCbr);
		cbrAff.add(strongListCbr);
		cbrAff.add(noneListCbr);
		//Create integer to determine size of Lists in order to calculate the highest number of attributes
		double sizeU = 0.0;
		double sizeC = 0.0;
		double compare_c = 0.0;
		//list of lists
		for(int i = 0; i < userAff.size(); i++) {
			//single list of user
			sizeC += cbrAff.get(i).size();
			sizeU += userAff.get(i).size();
			for(int j = 0; j < userAff.get(i).size(); j++) {
				//compare to list of cbr
				for(int k = 0; k < cbrAff.get(i).size(); k++) {
					if(userAff.get(i).get(j).equals(cbrAff.get(i).get(k))) {
						compare_c++;
					}
				}
			}
		}
		if(sizeU > sizeC) {
			sim = compare_c / sizeU;
		} else {
			sim = compare_c / sizeC;
		}
		if(Double.isNaN(sim)) {
			sim = 0.001;
		}
		return sim;
	}
	
	//this method sorts the resulted Cases descending
	public static void sortResultCases(List<Case_Pokemonteam> resultCases) {
		Collections.sort(resultCases, new Comparator<Case_Pokemonteam>() {
			@Override
			public int compare(Case_Pokemonteam case1, Case_Pokemonteam case2) {
				// TODO Auto-generated method stub
				return (case1.getSim() > case2.getSim()) ? -1 : (case1.getSim() < case2.getSim()) ? 1 : 0;
			}
		});
	}
}
