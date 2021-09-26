package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Klasse um alle Pokemontyp bezogenen Funktionalitäten gesammelt zu behandeln.
 * @author Steven Oberle
 * */
public final class TypeTableSupport {
	//Links für Angriff / Oben für Abwehr
	static String[][] typeArray = { {"", "normal", "fire", "water", "electric", "grass",
		"ice", "fighting", "poison", "ground", "flying", "psychic", "bug",
		"rock", "ghost", "dragon"},
			{"normal", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
			"1", "1", "0.5", "0", "1"},
			{"fire", "1", "0.5", "0.5", "1", "2", "2", "1", "1", "1", "1",
				"1", "2", "0.5", "1", "0.5"},
			{"water", "1", "2", "0.5", "1", "0.5", "1", "1", "1", "2", "1",
					"1", "1", "2", "1", "0.5"},
			{"electric", "1", "1", "2", "0.5", "0.5", "1", "1", "1", "0", "2",
						"1", "1", "1", "1", "0.5"},
			{"grass", "1", "0.5", "2", "1", "0.5", "1", "1", "0.5", "2", "0.5",
							"1", "0.5", "2", "1", "0.5"},
			{"ice", "1", "0.5", "0.5", "1", "2", "0.5", "1", "1", "2", "2",
								"1", "1", "1", "1", "2"},
			{"fighting", "2", "1", "1", "1", "1", "2", "1", "0.5", "1", "0.5",
									"0.5", "0.5", "2", "0", "1"},
			{"poison", "1", "1", "1", "1", "2", "1", "1", "0.5", "0.5", "1",
									"1", "1", "0.5", "0.5", "1"},
			{"ground", "1", "2", "1", "2", "0.5", "1", "1", "2", "1", "0",
									"1", "0.5", "2", "1", "1"},
			{"flying", "1", "1", "1", "0.5", "2", "1", "2", "1", "1", "1",
									"1", "2", "0.5", "1", "1"},
			{"psychic", "1", "1", "1", "1", "1", "1", "2", "2", "1", "1",
									"0.5", "1", "1", "1", "1"},
			{"bug", "1", "0.5", "1", "1", "2", "1", "0.5", "0.5", "1", "0.5",
									"2", "1", "1", "0.5", "1"},
			{"rock", "1", "2", "1", "1", "1", "2", "0.5", "1", "0.5", "2",
									"1", "2", "1", "1", "1"},
			{"ghost", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1",
									"2", "1", "1", "2", "1"},
			{"dragon", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
									"1", "1", "1", "1", "2"}};
	
	//Returns all values of both types regarding defense
	public static Map<String, Double> checkDefenseAffinities(Pokemon pokemon) {
		//instantiate needed variables
		List<String> defenseNames = new ArrayList<String>();
		List<Double> defenseValuesT1 = new ArrayList<Double>();
		List<Double> defenseValuesT2 = new ArrayList<Double>();
		Map<String, Double> mapTypesValuesDef = new HashMap<String, Double>();
		//Iteration within first row to find type 1
		for(int i = 0; i < 16; i ++) {	
			if(typeArray[0][i].equals(pokemon.getType1())) {
				for(int j = 1; j < 16; j++) {
					//add all occuring types to List
					defenseNames.add(typeArray[j][0].toString());
					//add specific type values to List
					defenseValuesT1.add(Double.parseDouble(typeArray[j][i]));
				}
			}
			//Iteration within first row to find type 2
			if(typeArray[0][i].equals(pokemon.getType2()) && !pokemon.getType2().isEmpty() && !pokemon.getType1().equals(pokemon.getType2())) {
				for(int j = 1; j < 16; j++) {
					defenseValuesT2.add(Double.parseDouble(typeArray[j][i]));
				}
			}
		}
		if(defenseValuesT2.size() > 0) {
			for (int i = 0; i < defenseNames.size(); i++) {
				mapTypesValuesDef.put(defenseNames.get(i), defenseValuesT1.get(i) * defenseValuesT2.get(i));
			}
		} else {
			for (int i = 0; i < defenseNames.size(); i++) {
				mapTypesValuesDef.put(defenseNames.get(i), defenseValuesT1.get(i));
			}
		}
		return mapTypesValuesDef;
	}
	
	public static Map<String, Double> checkAttackAffinities(Pokemon pokemon) {
		//instantiate needed variables
				List<String> attackNames = new ArrayList<String>();
				List<Double> attackValuesT1 = new ArrayList<Double>();
				List<Double> attackValuesT2 = new ArrayList<Double>();
				Map<String, Double> mapTypesValuesAtt = new HashMap<String, Double>();
				//Iteration within first row to find type 1
				for(int i = 0; i < 16; i ++) {
					if(typeArray[i][0].equals(pokemon.getType1())) {
						for(int j = 1; j < 16; j++) {
							//add all occuring types to List
							attackNames.add(typeArray[0][j].toString());
							//add specific type values to List
							attackValuesT1.add(Double.parseDouble(typeArray[j][i]));
						}
					}
					//Iteration within first row to find type 2
					if(typeArray[i][0].equals(pokemon.getType2()) && !pokemon.getType2().isEmpty() && !pokemon.getType1().equals(pokemon.getType2())) {
						for(int j = 1; j < 16; j++) {
							attackValuesT2.add(Double.parseDouble(typeArray[j][i]));
						}
					}
				}
				if(attackValuesT2.size() > 0) {
					for (int i = 0; i < attackNames.size(); i++) {
						mapTypesValuesAtt.put(attackNames.get(i), attackValuesT1.get(i) * attackValuesT2.get(i));
					}
				} else {
					for (int i = 0; i < attackNames.size(); i++) {
						mapTypesValuesAtt.put(attackNames.get(i), attackValuesT1.get(i));
					}
				}
				return mapTypesValuesAtt;
	}
	
	//Returns all values of both types regarding defense
	public static Map<String, Integer> checkTeamDefenseAffinities(Pokemonteam pokemonteam) {
		//instantiate needed variables
		var types = List.of("normal", "fire", "water", "electric", "grass",
				"ice", "fighting", "poison", "ground", "flying", "psychic", "bug",
				"rock", "ghost", "dragon");
		//Counting all resistances, weaknesses and immunities for the whole team
		List<Integer> immuneCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> resCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> weakCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Double> values1 = new ArrayList<Double>();
		
		Map<String, Integer> mapTypesValuesDef = new HashMap<String, Integer>();
		
		//compare both type of a pokemon to check if they are resistant/weak/immune
		for(int i = 0; i < pokemonteam.getPokemon().size(); i++) {
			//iterate through all types once to get type 1
			for(int j = 1; j < 16; j++) {
				//if type 1 of Pokemon was found
				if(typeArray[0][j].equals(pokemonteam.getPokemon().get(i).getType1())) {
					//add all found values to List values1
					for(int k = 1; k < 16; k++) {
						values1.add(Double.parseDouble(typeArray[k][j]));
					}
				}
			}
			//check if Type 2 exists
			if(!pokemonteam.getPokemon().get(i).getType2().isEmpty()) {
				//iterate a second to get Type 2
				for(int j = 1; j < 16; j++) {
					//if type 2 was found and exists
					if(typeArray[0][j].equals(pokemonteam.getPokemon().get(i).getType2())) {
						for(int k = 1; k < 16; k++) {
							values1.set(k - 1, values1.get(k -1) * Double.parseDouble(typeArray[k][j]));
						}
					}
				}
			}
			
			//iterate through multiplied type values of one pokemon
			for(int j = 0; j < values1.size(); j++) {
				//if doubled weakness was found
				if(values1.get(j) == 4.0) {
					weakCounter.set(j, weakCounter.get(j) + 2);
				}
				//if weakness was found
				if(values1.get(j) == 2.0) {
					weakCounter.set(j, weakCounter.get(j) + 1);
				}
				//if resistance was found
				if(values1.get(j) == 0.5) {
					resCounter.set(j, resCounter.get(j) + 1);
				}
				//if doubled resistance was found
				if(values1.get(j) == 0.25) {
					resCounter.set(j, resCounter.get(j) + 2);
				}
				//if immunity was found
				if(values1.get(j) == 0.0) {
					immuneCounter.set(j, immuneCounter.get(j) + 1);
				}
			}
			//clear List to fill with new values
			values1.clear();
		}
		
		//save all Lists to one Map with their specific Values
		for(int i = 0; i < types.size(); i++) {
			mapTypesValuesDef.put("res" + types.get(i), resCounter.get(i));
			mapTypesValuesDef.put("weak" + types.get(i), weakCounter.get(i));
			mapTypesValuesDef.put("immune" + types.get(i), immuneCounter.get(i));
		}
		return mapTypesValuesDef;
	}
	
	//returns the attackaffinities for both types of each Pokemon in the team
	public static Map<String, Integer> checkTeamAttackAffinities(Pokemonteam pokemonteam) {
		//instantiate needed variables
		var types = List.of("normal", "fire", "water", "electric", "grass",
				"ice", "fighting", "poison", "ground", "flying", "psychic", "bug",
				"rock", "ghost", "dragon");
		//Counting all resistances, weaknesses and immunities for the whole team
		List<Integer> immuneCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> resCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> weakCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Double> values1 = new ArrayList<Double>();
		Map<String, Integer> mapTypesValuesAtt = new HashMap<String, Integer>();
		//Iteration within first row to find type 1
		
		for(int i = 0; i < pokemonteam.getPokemon().size(); i++) {
			for(int j = 1; j < 16; j++) {
				//if type 1 of Pokemon was found
				if(typeArray[j][0].equals(pokemonteam.getPokemon().get(i).getType1())) {
					//add all found values to List values1
					for(int k = 1; k < 16; k++) {
						values1.add(Double.parseDouble(typeArray[j][k]));
					}
				}
			}
			//check if Type 2 exists
			if(!pokemonteam.getPokemon().get(i).getType2().isEmpty()) {
				//iterate a second to get Type 2
				for(int j = 1; j < 16; j++) {
					//if type 2 was found and exists
					if(typeArray[j][0].equals(pokemonteam.getPokemon().get(i).getType2())) {
						for(int k = 1; k < 16; k++) {
							values1.set(k - 1, values1.get(k -1) * Double.parseDouble(typeArray[j][k]));
						}
					}
				}
			}
			//iterate through multiplied type values of one pokemon
			for(int j = 0; j < values1.size(); j++) {
				//if doubled weakness was found
				if(values1.get(j) == 4.0) {
					weakCounter.set(j, weakCounter.get(j) + 2);
				}
				//if weakness was found
				if(values1.get(j) == 2.0) {
					weakCounter.set(j, weakCounter.get(j) + 1);
				}
				//if resistance was found
				if(values1.get(j) == 0.5) {
					resCounter.set(j, resCounter.get(j) + 1);
				}
				//if doubled resistance was found
				if(values1.get(j) == 0.25) {
					resCounter.set(j, resCounter.get(j) + 2);
				}
				//if immunity was found
				if(values1.get(j) == 0.0) {
					immuneCounter.set(j, immuneCounter.get(j) + 1);
				}
			}
			//clear List to fill with new values
			values1.clear();
		}
		//save all Lists to one Map with their specific Values
		for(int i = 0; i < types.size(); i++) {
			mapTypesValuesAtt.put("reduced" + types.get(i), resCounter.get(i));
			mapTypesValuesAtt.put("strong" + types.get(i), weakCounter.get(i));
			mapTypesValuesAtt.put("none" + types.get(i), immuneCounter.get(i));
		}
		return mapTypesValuesAtt;
	}
	
	//this method returns fitting types to adress the weaknesses with scoring
	public static List<String> adressWeaknesses(List<String> weaknessesLeft) {
		List<String> resTypes = new ArrayList<String>();
		for(int i = 0; i < weaknessesLeft.size(); i++) {
			for(int j = 1; j < 16 ; j++) {
				if(typeArray[j][0].equals(weaknessesLeft.get(i))) {
					for(int k = 1; k < 16; k++) {
						if(Double.parseDouble(typeArray[j][k]) == 0.5) {
							resTypes.add(weaknessesLeft.get(i) + ":" + typeArray[0][k]);
						}
					}
				}
			}
		}
		return resTypes;
	}
	
	//this methods uses the combination String List of adressWeaknesses to check several Type combinations
	//regarding their effectiveness and influence on the team combination
	//returns a List of rated Pokemontype combinations
	public static List<ScorePair> calculateTypeComboResistance(List<String> resTypes, List<String> teamRes) {
		List<String> onlyResTypes = new ArrayList<String>();
		List<String> onlyResTypesNoDoubles = new ArrayList<String>();
		for(int i = 0; i < resTypes.size(); i++) {
			int split = resTypes.get(i).indexOf(":");
			onlyResTypes.add(resTypes.get(i).substring(split +1));
		}
		onlyResTypes.sort(Comparator.naturalOrder());
		//remove doubles from List
		for(int i = 0; i < onlyResTypes.size(); i++) {
			if(i == 0) {
				onlyResTypesNoDoubles.add(onlyResTypes.get(i));
			} else {
				//if Resistance is not a double beginning from 1
				if(!onlyResTypes.get(i -1).equals(onlyResTypes.get(i))) {
					onlyResTypesNoDoubles.add(onlyResTypes.get(i));
				}
			}
		}
		//create Type combinations and save them as Pokemon to a List<Pokemon> for further analysis
		List<Pokemon> pokemonCombinations = new ArrayList<Pokemon>();
		for(int i = 0; i < onlyResTypesNoDoubles.size(); i++) {
			for(int j = i; j < onlyResTypesNoDoubles.size();j++) {
				//combination of the same type
				Pokemon pokemon = new Pokemon();
				if(onlyResTypesNoDoubles.get(i).equals(onlyResTypesNoDoubles.get(j))) {
					pokemon.setType1(onlyResTypesNoDoubles.get(i));
				} else {
					pokemon.setType1(onlyResTypesNoDoubles.get(i));
					pokemon.setType2(onlyResTypesNoDoubles.get(j));
				}
				pokemonCombinations.add(pokemon);
			}
		}
		
		List<ScorePair> result = new ArrayList<ScorePair>();
		//get from each Pokemon defense Mapping and score it by comparing to resistances from current pokemonteam
		for(int i = 0; i < pokemonCombinations.size(); i++) {
			int score = 0;
			Map<String, Double> defMap = checkDefenseAffinities(pokemonCombinations.get(i));
			for (Map.Entry<String, Double> entry : defMap.entrySet()) {
				for(int j = 0; j < teamRes.size(); j++) {
					//if pokemon combination introduces new weakness that is not covered
					if(!entry.getKey().equals(teamRes.get(j)) && entry.getValue() == 2.0) {
						score--;
					} else if (!entry.getKey().equals(teamRes.get(j)) && entry.getValue() == 4.0) {
						score = score - 2;
					}
					//if pokemon combination introduces new teamResistance => better coverage
					if(!entry.getKey().equals(teamRes.get(j)) && entry.getValue() == 0.5) {
						score++;
					} else if(!entry.getKey().equals(teamRes.get(j)) && entry.getValue() == 0.25) {
						score = score + 2;
					}
				}
			}
			result.add(new ScorePair(pokemonCombinations.get(i),score));
		}	
		result.sort(Comparator.comparingInt(ScorePair::getScore).reversed());
		//return the Pokemon on the first index as input for the following retrieve
		return result;
	}
}
