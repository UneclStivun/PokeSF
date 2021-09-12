package cbr_Pokemon;

import java.util.List;

import database.DatabaseManipulator;
import de.dfki.mycbr.core.DefaultCaseBase;
import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Instance;
import de.dfki.mycbr.core.model.Concept;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.model.StringDesc;
import de.dfki.mycbr.core.similarity.IntegerFct;
import de.dfki.mycbr.core.similarity.config.NumberConfig;
import pokemon.Pokemon;


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
		//if project gets created successfully load Cases from database
		if(loadProject()) {
			loadCasesFromDb();
		}
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
			IntegerDesc def = new IntegerDesc(concept, "Defense", 1, 250);
			
			//Specialdefense value
			IntegerDesc spDef = new IntegerDesc(concept, "SpecialDefense", 1, 250);
			
			//Initiative value
			IntegerDesc ini = new IntegerDesc(concept, "Initiative", 1, 200);
			
			//Pokemontype 1
			StringDesc pkType1 = new StringDesc(concept, "Pokemontype1");
			
			//Pokemontype 2
			StringDesc pkType2 = new StringDesc(concept, "Pokemontype2");
			
			//Name of the Pokemon
			StringDesc pkName = new StringDesc(concept, "Pokemonname");
			
			//Pokemonattacks
			StringDesc pkAttacks = new StringDesc(concept, "PokemonAttacks");
			
			//DatabaseID not for Retrieval but identification
			StringDesc pkDBID = new StringDesc(concept, "DatabaseID");
			
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
			
			iniFct = ini.addIntegerFct("InitiativeDefense", true);
			iniFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			iniFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			cb = project.createDefaultCB("PokemonCB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}	
		return success;
	}
	
	public boolean loadCasesFromDb() {
		boolean success = true;
		//fill List via Database
		List<Pokemon> pokemons = dbm.getPokemonFromDatabase();
		//loop through list to add Pokemon to casebase
		for(int i = 0; i < pokemons.size(); i++) {
			try {
				//Pokemonname with Instance
				Instance inst = concept.addInstance(pokemons.get(i).getName());
				
				//adding attributes
				inst.addAttribute("Hitpoints", pokemons.get(i).getHitpoints());
				inst.addAttribute("Attack", pokemons.get(i).getAttack());
				inst.addAttribute("SpecialAttack", pokemons.get(i).getSpAttack());
				inst.addAttribute("Defense", pokemons.get(i).getDefense());
				inst.addAttribute("SpecialDefense", pokemons.get(i).getSpDefense());
				inst.addAttribute("Initiative", pokemons.get(i).getInitiative());
				inst.addAttribute("Pokemontype1", pokemons.get(i).getType1());
				inst.addAttribute("Pokemontype2", pokemons.get(i).getType2());
				inst.addAttribute("Pokemonname", pokemons.get(i).getName());
				inst.addAttribute("PokemonAttacks", pokemons.get(i).attackListToString());
				inst.addAttribute("DatabaseID", String.valueOf(pokemons.get(i).getDatabaseID()));
				// add Instance to casebase
				cb.addCase(inst);
			} catch (Exception e) {
				// TODO: handle exception
				success = false;
			}
		}
		return success;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	public DefaultCaseBase getCb() {
		return cb;
	}

	public void setCb(DefaultCaseBase cb) {
		this.cb = cb;
	}

	public DatabaseManipulator getDbm() {
		return dbm;
	}

	public void setDbm(DatabaseManipulator dbm) {
		this.dbm = dbm;
	}

	public IntegerFct getHpFct() {
		return hpFct;
	}

	public void setHpFct(IntegerFct hpFct) {
		this.hpFct = hpFct;
	}

	public IntegerFct getAttFct() {
		return attFct;
	}

	public void setAttFct(IntegerFct attFct) {
		this.attFct = attFct;
	}

	public IntegerFct getSpAttFct() {
		return spAttFct;
	}

	public void setSpAttFct(IntegerFct spAttFct) {
		this.spAttFct = spAttFct;
	}

	public IntegerFct getDefFct() {
		return defFct;
	}

	public void setDefFct(IntegerFct defFct) {
		this.defFct = defFct;
	}

	public IntegerFct getSpDefFct() {
		return spDefFct;
	}

	public void setSpDefFct(IntegerFct spDefFct) {
		this.spDefFct = spDefFct;
	}

	public IntegerFct getIniFct() {
		return iniFct;
	}

	public void setIniFct(IntegerFct iniFct) {
		this.iniFct = iniFct;
	}
}
