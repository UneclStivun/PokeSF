package tags;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseManipulator;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.TagSupport;
import pokemon.Pokemon;

/**Tag Klasse um alle validen Pokemon beim laden in die Session zu speichern
 * @author Steven Oberle
 * */

public class CreateTeamTag extends TagSupport{
	private static final long serialVersionUID = 1L;

	private List<Pokemon> pokeList = new ArrayList<Pokemon>();
	
	//this tag saves all current valid Pokemon in the datalist and adds them to the session
	public int doStartTag() {
		DatabaseManipulator dbm = new DatabaseManipulator();
		pokeList = dbm.getPokemonFromDatabase();
		HttpSession session = pageContext.getSession();
		session.setAttribute("allPokeList", pokeList);
		return SKIP_BODY;
	}	
}
