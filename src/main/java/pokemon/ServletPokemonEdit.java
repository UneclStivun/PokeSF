package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManipulator;
import de.dfki.mycbr.core.casebase.Instance;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPokemonEdit
 */
public class ServletPokemonEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor. 
     */
    public ServletPokemonEdit() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
		
		// Gedrückter Button wird in Session gespeichert
		session.setAttribute("pressedButton", request.getParameter("button"));
				
		showPokemonTable(request, response, request.getParameter("button"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("validate") != null && !request.getParameter("validate").equals("")) {
			validatePokemon(request, response, Integer.parseInt(request.getParameter("validate")));
		}
		
		if(request.getParameter("invalidate") != null && !request.getParameter("invalidate").equals("")) {
			invalidatePokemon(request, response, Integer.parseInt(request.getParameter("invalidate")));
		}
		
		if(request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
			deletePokemon(request, response, Integer.parseInt(request.getParameter("delete")));
		}
	}
	
	// Methode zum einholen der Pokemon aus der Datenbank, um diese als Tabelle anzuzeigen
	private void showPokemonTable(HttpServletRequest request, HttpServletResponse response, String show_pokemon) throws ServletException, IOException {
			
		//Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
			
		// Instanz von DatabaseManipulator wird erstellt, um von dort die Pokemonliste zu bekommen
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		
		// Liste aus der Datenbank übergebener Pokemon
		List<Pokemon> pokemonList;
		
		// Gebe alle Pokemon, nur die validierten oder nur die nicht validierten aus
		if(show_pokemon.equals("all")) {
			// Liste der Pokemonobjekte
			pokemonList = dmPokemonDatabase.getPokemonFromDatabase();
			
		} else if(show_pokemon.equals("valid")){
			pokemonList = dmPokemonDatabase.getValidatedPokemonFromDatabase(true);
			
		} else {
			pokemonList = dmPokemonDatabase.getValidatedPokemonFromDatabase(false);
		}
		
		// Übergebe Liste der Pokemonobjekte in die Session
		session.setAttribute("pokemonList", pokemonList);
		
		// Spaltennamen übergeben
		request.setAttribute("columnNames", new String[] { "Name", "Type 1", "Type 2", "Hitpoints", "Attack", "Defense", "Sp.Attack", "Sp.Defense", "Speed"});
			
		request.getRequestDispatcher("pokemonEdit.jsp").forward(request, response);
	}
		
	// Methode zum Validieren der Pokemon
	private void validatePokemon(HttpServletRequest request, HttpServletResponse response, int pokemon_id) throws ServletException, IOException {
		
		//Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
		
		// Instanz von DatabaseManipulator, um Validationseintrag in der Datenbank zu aktualisieren
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		dmPokemonDatabase.validatePokemon(true, pokemon_id);
		
		showPokemonTable(request, response, session.getAttribute("pressedButton").toString());
	}
	
	private void invalidatePokemon(HttpServletRequest request, HttpServletResponse response, int pokemon_id) throws ServletException, IOException {
		
		//Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
				
		// Instanz von DatabaseManipulator, um Validationseintrag in der Datenbank zu aktualisieren
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		dmPokemonDatabase.validatePokemon(false, pokemon_id);
				
		showPokemonTable(request, response, session.getAttribute("pressedButton").toString());
	}
		
	// Methode zum Löschen der Pokemon
	private void deletePokemon(HttpServletRequest request, HttpServletResponse response, int pokemon_id) throws ServletException, IOException {
		
		// Session setzen oder wiederverwenden
		HttpSession session = request.getSession();

		// Instanz von DatabaseManipulator, um Validationseintrag in der Datenbank zu aktualisieren
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		//dmPokemonDatabase.deletePokemon(pokemon_id);
		
		showPokemonTable(request, response, session.getAttribute("pressedButton").toString());
	}
}
