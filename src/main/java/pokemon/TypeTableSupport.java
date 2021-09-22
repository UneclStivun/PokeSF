package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				"ice", "fighting", "poison", "ground", "flying", "psycho", "bug",
				"rock", "ghost", "dragon");
		//Counting all resistances, weaknesses and immunities for the whole team
		List<Integer> immuneCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> resCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> weakCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		Map<String, Integer> mapTypesValuesDef = new HashMap<String, Integer>();
		//Iteration within first row to find type 1
		
		for(int k = 0; k < pokemonteam.getPokemon().size(); k++) {
			//Iteration within first row to find type 1
			for(int i = 0; i < 16; i ++) {	
				if(typeArray[0][i].equals(pokemonteam.getPokemon().get(k).getType1())) {
					for(int j = 1; j < 16; j++) {
						//if weakness was found
						if(Double.parseDouble(typeArray[j][i]) == 2.0) {
							weakCounter.set(j, weakCounter.get(j) + 1);
						}
						//if resistance was found
						if(Double.parseDouble(typeArray[j][i]) == 0.5) {
							resCounter.set(j, resCounter.get(j) + 1);
						}
						//if immunity was found
						if(Double.parseDouble(typeArray[j][i]) == 0.0) {
							immuneCounter.set(j, immuneCounter.get(j) + 1);
						}
					}
				}
				//Iteration within first row to find type 2
				if(typeArray[0][i].equals(pokemonteam.getPokemon().get(k).getType2()) 
						&& !pokemonteam.getPokemon().get(k).getType2().isEmpty() 
						&& !pokemonteam.getPokemon().get(k).getType1().equals(pokemonteam.getPokemon().get(k).getType2())) {
					for(int j = 1; j < 16; j++) {
						//if weakness was found
						if(Double.parseDouble(typeArray[j][i]) == 2.0) {
							weakCounter.set(j, weakCounter.get(j) + 1);
						}
						//if resistance was found
						if(Double.parseDouble(typeArray[j][i]) == 0.5) {
							resCounter.set(j, resCounter.get(j) + 1);
						}
						//if immunity was found
						if(Double.parseDouble(typeArray[j][i]) == 0.0) {
							immuneCounter.set(j, immuneCounter.get(j) + 1);
						}
					}
				}
			}
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
				"ice", "fighting", "poison", "ground", "flying", "psycho", "bug",
				"rock", "ghost", "dragon");
		//Counting all resistances, weaknesses and immunities for the whole team
		List<Integer> immuneCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> resCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		List<Integer> weakCounter = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
		Map<String, Integer> mapTypesValuesAtt = new HashMap<String, Integer>();
		//Iteration within first row to find type 1
		
		for(int k = 0; k < pokemonteam.getPokemon().size(); k++) {
			//Iteration within first row to find type 1
			for(int i = 0; i < 16; i ++) {	
				if(typeArray[i][0].equals(pokemonteam.getPokemon().get(k).getType1())) {
					for(int j = 1; j < 16; j++) {
						//if weakness was found
						if(Double.parseDouble(typeArray[j][i]) == 2.0) {
							weakCounter.set(j, weakCounter.get(j) + 1);
						}
						//if resistance was found
						if(Double.parseDouble(typeArray[j][i]) == 0.5) {
							resCounter.set(j, resCounter.get(j) + 1);
						}
						//if immunity was found
						if(Double.parseDouble(typeArray[j][i]) == 0.0) {
							immuneCounter.set(j, immuneCounter.get(j) + 1);
						}
					}
				}
				//Iteration within first row to find type 2
				if(typeArray[i][0].equals(pokemonteam.getPokemon().get(k).getType2()) 
						&& !pokemonteam.getPokemon().get(k).getType2().isEmpty() 
						&& !pokemonteam.getPokemon().get(k).getType1().equals(pokemonteam.getPokemon().get(k).getType2())) {
					for(int j = 1; j < 16; j++) {
						//if weakness was found
						if(Double.parseDouble(typeArray[j][i]) == 2.0) {
							weakCounter.set(j, weakCounter.get(j) + 1);
						}
						//if resistance was found
						if(Double.parseDouble(typeArray[j][i]) == 0.5) {
							resCounter.set(j, resCounter.get(j) + 1);
						}
						//if immunity was found
						if(Double.parseDouble(typeArray[j][i]) == 0.0) {
							immuneCounter.set(j, immuneCounter.get(j) + 1);
						}
					}
				}
			}
		}
		//save all Lists to one Map with their specific Values
		for(int i = 0; i < types.size(); i++) {
			mapTypesValuesAtt.put("reduced" + types.get(i), resCounter.get(i));
			mapTypesValuesAtt.put("strong" + types.get(i), weakCounter.get(i));
			mapTypesValuesAtt.put("none" + types.get(i), immuneCounter.get(i));
		}
		return mapTypesValuesAtt;
	}
}
