package pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TypeTableSupport {
	//Links für Angriff / Oben für Abwehr
	static String[][] typeArray = { {"", "Normal", "Fire", "Water", "Electro", "Grass",
		"Ice", "Figthing", "Poison", "Ground", "Flying", "Psycho", "Bug",
		"Rock", "Ghost", "Dragon"},
			{"Normal", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
			"1", "1", "0.5", "0", "1"},
			{"Fire", "1", "0.5", "0.5", "1", "2", "2", "1", "1", "1", "1",
				"1", "2", "0.5", "1", "0.5"},
			{"Water", "1", "2", "0.5", "1", "0.5", "1", "1", "1", "2", "1",
					"1", "1", "2", "1", "0.5"},
			{"Electric", "1", "1", "2", "0.5", "0.5", "1", "1", "1", "0", "2",
						"1", "1", "1", "1", "0.5"},
			{"Grass", "1", "0.5", "2", "1", "0.5", "1", "1", "0.5", "2", "0.5",
							"1", "0.5", "2", "1", "0.5"},
			{"Ice", "1", "0.5", "0.5", "1", "2", "0.5", "1", "1", "2", "2",
								"1", "1", "1", "1", "2"},
			{"Fighting", "2", "1", "1", "1", "1", "2", "1", "0.5", "1", "0.5",
									"0.5", "0.5", "2", "0", "1"},
			{"Poison", "1", "1", "1", "1", "2", "1", "1", "0.5", "0.5", "1",
									"1", "1", "0.5", "0.5", "1"},
			{"Ground", "1", "2", "1", "2", "0.5", "1", "1", "2", "1", "0",
									"1", "0.5", "2", "1", "1"},
			{"Flying", "1", "1", "1", "0.5", "2", "1", "2", "1", "1", "1",
									"1", "2", "0.5", "1", "1"},
			{"Psychic", "1", "1", "1", "1", "1", "1", "2", "2", "1", "1",
									"0.5", "1", "1", "1", "1"},
			{"Bug", "1", "0.5", "1", "1", "2", "1", "0.5", "0.5", "1", "0.5",
									"2", "1", "1", "0.5", "1"},
			{"Rock", "1", "2", "1", "1", "1", "2", "0.5", "1", "0.5", "2",
									"1", "2", "1", "1", "1"},
			{"Ghost", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1",
									"2", "1", "1", "2", "1"},
			{"Dragon", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
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
			if(typeArray[0][i].equals(pokemon.getType2())) {
				for(int j = 1; j < 16; j++) {
					defenseValuesT2.add(Double.parseDouble(typeArray[j][i]));
				}
			}
		}
		for (int i = 0; i < defenseNames.size(); i++) {
			mapTypesValuesDef.put(defenseNames.get(i), defenseValuesT1.get(i) * defenseValuesT2.get(i));
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
					if(typeArray[0][i].equals(pokemon.getType1())) {
						for(int j = 1; j < 16; j++) {
							//add all occuring types to List
							attackNames.add(typeArray[j][0].toString());
							//add specific type values to List
							attackValuesT1.add(Double.parseDouble(typeArray[j][i]));
						}
					}
					//Iteration within first row to find type 2
					if(typeArray[0][i].equals(pokemon.getType2())) {
						for(int j = 1; j < 16; j++) {
							attackValuesT2.add(Double.parseDouble(typeArray[j][i]));
						}
					}
				}
				for (int i = 0; i < attackNames.size(); i++) {
					mapTypesValuesAtt.put(attackNames.get(i), attackValuesT1.get(i) * attackValuesT2.get(i));
				}
				return mapTypesValuesAtt;
	}
	
}
