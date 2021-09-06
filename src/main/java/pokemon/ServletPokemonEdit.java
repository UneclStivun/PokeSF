package pokemon;

import java.io.IOException;
import java.util.List;

import database.DatabaseManipulator;
import de.dfki.mycbr.core.casebase.Instance;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		
		String show_pokemon = request.getParameter("action");
		
		// Instanz von DatabaseManipulator wird erstellt, um von dort die Pokemonliste zu bekommen
		DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
		
		List<Pokemon> pokemonList = dmPokemonDatabase.getPokemonFromDatabase();
		
		if(show_pokemon.equals("all")) {
			showAllPokemon();
		} else {
			//loop through list to add Pokemon to casebase
			for(int i = 0; i < pokemonList.size(); i++) {
				
				System.out.println("Name: " + pokemonList.get(i).getName());
				
				try {
					//Pokemonname with Instance
					//Instance inst = concept.addInstance(pokemons.get(i).getName());
//					//adding attributes
//					inst.addAttribute("Hitpoints", pokemons.get(i).getHitpoints());
//					inst.addAttribute("Attack", pokemons.get(i).getAttack());
//					inst.addAttribute("SpecialAttack", pokemons.get(i).getSpAttack());
//					inst.addAttribute("Defense", pokemons.get(i).getDefense());
//					inst.addAttribute("SpecialDefense", pokemons.get(i).getSpDefense());
//					inst.addAttribute("Pokemontype1", pokemons.get(i).getType1());
//					inst.addAttribute("Pokemontype2", pokemons.get(i).getType2());
//					inst.addAttribute("Pokemonname", pokemons.get(i).getName());
//					inst.addAttribute("PokemonAttacks", pokemons.get(i).attackListToString());
				} catch (Exception e) {
				}
			}
		}
		
		// Spaltennamen und Liste weitersenden
		request.setAttribute("columnNames", new String[] { "Name", "Type 1", "Type 2", "Hitpoints", "Attack", "Defense", "Sp.Attack", "Sp.Defense", "Speed"});
		request.setAttribute("resultList", "");

		request.getRequestDispatcher("pokemonEdit.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
	}
	
	private void showAllPokemon() {
		
	}
	
	private void showInvalidPokemon() {
		
	}
}
