package cbr_Pokemon;

import database.DatabaseManipulator;
import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.similarity.IntegerFct;

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
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//Es fehlen noch Similarities für Typen und damit verbundene Schwächen/Stärken
	//ToDo: Eigene Funktionen
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
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
		
		return success;
	}
}
