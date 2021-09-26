package tags;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseManipulator;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.TagSupport;
import pokemon.Pokemonteam;

/**Tag Klasse um alle validen Pokemonteams in die Session zu speichern
 * @author Steven Oberle
 * */
public class PrepareTeamTag extends TagSupport{
	private static final long serialVersionUID = 1L;
	
	List<Pokemonteam> allTeams = new ArrayList<Pokemonteam>();
	
	public int doStartTag() {
		//Get all pokemonteams with their Pokemons from the database => new method
		DatabaseManipulator dbm = new DatabaseManipulator();
		allTeams = dbm.getAllPokemonTeamsFromDatabase();
		//save to session
		HttpSession session = pageContext.getSession();
		session.setAttribute("allTeamList", allTeams);
		return SKIP_BODY;
	}
}
