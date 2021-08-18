package cbr_Pokemon;

import database.DatabaseManipulator;
import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.config.NumberConfig;


public class CaseBaseLoader_Pokemon {
	//Membervariables
	private Project project;
	
	private Concept concept;
	
	private DefaultCaseBase cb;
	
	private DatabaseManipulator dbm;
	
	//Integer Functions needed for Retrieval
	private IntegerFct hpFct;
	
	private IntegerFct attFct;
	
	private IntegerFct spAttFct;
	
	private IntegerFct defFct;
	
	private IntegerFct spDefFct;
	
	private IntegerFct iniFct;
	
	public CaseBaseLoader_Pokemon(DatabaseManipulator dbm) {
		this.dbm = dbm;
	}
	
	//Create a project
	public boolean loadProject() {
		boolean success = true;
		
		try {
			//create project
			project = new Project();
			//create top concept
			concept = project.createTopConcept("Pokemon");
			
			//Initialize case attributes
			//Amount of hitpoints
			IntegerDesc hp = new IntegerDesc(concept, "Hitpoints", 1, 255);
			
			//Attack value
			IntegerDesc att = new IntegerDesc(concept,"Attack", 1,200);
			
			//Specialattack value
			IntegerDesc spAtt = new IntegerDesc(concept, "SpecialAttack", 1, 200);
			
			//Defense value
			IntegerDesc def = new IntegerDesc(concept, "Defense", 1, 200);
			
			//Specialdefense value
			IntegerDesc spDef = new IntegerDesc(concept, "SpecialDefense", 1, 200);
			
			//Pokemontype 1
			StringDesc pkType1 = new StringDesc(concept, "Pokemontype1");
			
			//Pokemontype 2
			StringDesc pkType2 = new StringDesc(concept, "Pokemontype2");
			
			//Name of the Pokemon
			StringDesc pkName = new StringDesc(concept, "Pokemonname");
			
			//Set all IntegerFunctions
			hpFct = hp.addIntegerFct("HPFct", true);
			hpFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			hpFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			attFct = att.addIntegerFct("AttackFct", true);
			attFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			attFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			spAttFct = spAtt.addIntegerFct("SpecialAttackFct", true);
			spAttFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			spAttFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			defFct = def.addIntegerFct("DefenseFct", true);
			defFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			defFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			spDefFct = spDef.addIntegerFct("SpecialDefenseFct", true);
			spDefFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			spDefFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			cb = project.createDefaultCB("PokemonCB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
		
		return success;
	}
}
