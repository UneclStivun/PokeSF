package cbr_Pokemonteam;

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
import pokemon.Pokemonteam;

public class CaseBaseLoader_Pokemonteam {
	//Membervariables
	private Project project;
		
	private Concept concept;
		
	private DefaultCaseBase cb;
		
	private DatabaseManipulator dbm;
		
	//Integer Functions
	IntegerFct sumHpFct;
	
	IntegerFct sumAttFct;
	
	IntegerFct sumSpAttFct;
	
	IntegerFct sumDefFct;
	
	IntegerFct sumSpDefFct;
	
	IntegerFct sumIniFct;
	
	public CaseBaseLoader_Pokemonteam(DatabaseManipulator dbm, Project project) {
		this.dbm = dbm;
		this.project = project;
		//if project gets created successfully load Cases from database
		if(loadProject()) {
			loadCasesFromDb();
		}
	}	

	private boolean loadCasesFromDb() {
		boolean success = true;
		//fill List via database
		List<Pokemonteam> allTeams = dbm.getAllPokemonTeamsFromDatabase();
		
		//loop through list to create Instances
		try {
			for(int i = 0; i < allTeams.size(); i++) {
				//Pokemonteamname as instance
				Instance inst = new Instance(concept, allTeams.get(i).getTeamname());
				allTeams.get(i).setSumUpVals();
				//adding attributes
				inst.addAttribute("Hitpoints", allTeams.get(i).getSumUpVals().get(0));
				inst.addAttribute("Attack", allTeams.get(i).getSumUpVals().get(1));
				inst.addAttribute("SpecialAttack", allTeams.get(i).getSumUpVals().get(2));
				inst.addAttribute("Defense", allTeams.get(i).getSumUpVals().get(3));
				inst.addAttribute("SpecialDefense", allTeams.get(i).getSumUpVals().get(4));
				inst.addAttribute("Initiative", allTeams.get(i).getSumUpVals().get(5));
				inst.addAttribute("TeamId", String.valueOf(allTeams.get(i).getTeamid()));	
				//add to casebase
				cb.addCase(inst);
			}
		} catch (Exception e) {
			success = false;
			System.out.println(e);
		}
		return success;
	}

	public boolean loadProject() {
		boolean success = true;
		
		try {
			//use existing project to create a new concept and casebase
			concept = project.createTopConcept("Pokemonteam");
			
			//Initialize case attributes
			//Amount of hitpoints
			IntegerDesc hp = new IntegerDesc(concept, "Hitpoints", 6, 1580);
			
			//Attack value
			IntegerDesc att = new IntegerDesc(concept,"Attack", 6,1200);
			
			//Specialattack value
			IntegerDesc spAtt = new IntegerDesc(concept, "SpecialAttack", 6, 1200);
			
			//Defense value
			IntegerDesc def = new IntegerDesc(concept, "Defense", 6, 1500);
			
			//Specialdefense value
			IntegerDesc spDef = new IntegerDesc(concept, "SpecialDefense", 6, 1500);
			
			//Initiative value
			IntegerDesc ini = new IntegerDesc(concept, "Initiative", 6, 1200);
			
			//StringDesc TeamId
			StringDesc teamId = new StringDesc(concept, "TeamId");
			
			//Set all IntegerFunctions
			sumHpFct = hp.addIntegerFct("HPFct", true);
			sumHpFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumHpFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			sumAttFct = att.addIntegerFct("AttackFct", true);
			sumAttFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumAttFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			sumSpAttFct = spAtt.addIntegerFct("SpecialAttackFct", true);
			sumSpAttFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumSpAttFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			sumDefFct = def.addIntegerFct("DefenseFct", true);
			sumDefFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumDefFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			sumSpDefFct = spDef.addIntegerFct("SpecialDefenseFct", true);
			sumSpDefFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumSpDefFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			sumIniFct = ini.addIntegerFct("InitiativeDefense", true);
			sumIniFct.setFunctionTypeL(NumberConfig.POLYNOMIAL_WITH);
			sumIniFct.setFunctionTypeR(NumberConfig.POLYNOMIAL_WITH);
			
			cb = project.createDefaultCB("PokemonteamCB");
		} catch (Exception e) {
			System.out.println(e);
			success = false;
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

	public IntegerFct getSumHpFct() {
		return sumHpFct;
	}

	public void setSumHpFct(IntegerFct sumHpFct) {
		this.sumHpFct = sumHpFct;
	}

	public IntegerFct getSumAttFct() {
		return sumAttFct;
	}

	public void setSumAttFct(IntegerFct sumAttFct) {
		this.sumAttFct = sumAttFct;
	}

	public IntegerFct getSumSpAttFct() {
		return sumSpAttFct;
	}

	public void setSumSpAttFct(IntegerFct sumSpAttFct) {
		this.sumSpAttFct = sumSpAttFct;
	}

	public IntegerFct getSumDefFct() {
		return sumDefFct;
	}

	public void setSumDefFct(IntegerFct sumDefFct) {
		this.sumDefFct = sumDefFct;
	}

	public IntegerFct getSumSpDefFct() {
		return sumSpDefFct;
	}

	public void setSumSpDefFct(IntegerFct sumSpDefFct) {
		this.sumSpDefFct = sumSpDefFct;
	}

	public IntegerFct getSumIniFct() {
		return sumIniFct;
	}

	public void setSumIniFct(IntegerFct sumIniFct) {
		this.sumIniFct = sumIniFct;
	}
}
