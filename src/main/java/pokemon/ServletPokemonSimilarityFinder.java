package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cbr_Pokemon.CaseBaseLoader_Pokemon;
import cbr_Pokemon.Case_Pokemon;
import cbr_Pokemon.Retrieval_Pokemon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPokemonSimilarityFinder
 */
@WebServlet("/ServletPokemonSimilarityFinder")
public class ServletPokemonSimilarityFinder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static List<Case_Pokemon> resultCases = new ArrayList<Case_Pokemon>();
	private static List<Case_Pokemon> allCases = new ArrayList<Case_Pokemon>();
    /**
     * Default constructor. 
     */
    public ServletPokemonSimilarityFinder() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		resultCases.clear();
		HttpSession session = request.getSession();
		Pokemon poke = new Pokemon();
		boolean minimum = false;
		
		if(!request.getParameter("pokemon_name").isEmpty()) {
			poke.setName(request.getParameter("pokemon_name"));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_type_1").isEmpty()) {
			poke.setType1(request.getParameter("pokemon_type_1"));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_type_2").isEmpty()) {
			poke.setType2(request.getParameter("pokemon_type_2"));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_hp").isEmpty()) {
			poke.setHitpoints(Integer.parseInt(request.getParameter("pokemon_hp")));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_attack").isEmpty()) {
			poke.setAttack(Integer.parseInt(request.getParameter("pokemon_attack"))) ;
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_defense").isEmpty()) {
			poke.setDefense(Integer.parseInt(request.getParameter("pokemon_defense")));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_specialattack").isEmpty()) {
			poke.setSpAttack(Integer.parseInt(request.getParameter("pokemon_specialattack")));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_specialdefense").isEmpty()) {
			poke.setSpDefense(Integer.parseInt(request.getParameter("pokemon_specialdefense")));
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_speed").isEmpty()) {
			poke.setInitiative(Integer.parseInt(request.getParameter("pokemon_speed")));
			minimum = true;
		}
		
		//Create Attack 1
		if(request.getParameter("cb_att1") != null) {
			Attack att = new Attack();
			att.setAttacktype(request.getParameter("pokemon_attack_type_1"));
			att.setAttackclass(request.getParameter("pokemon_attack_class_1"));
			if(att.getAttackclass().equals("status")) {
				att.setEffect(request.getParameter("effect1"));
			}
			poke.getAttacks().add(att);
			minimum = true;	
		}

		//Create Attack 2
		if(request.getParameter("cb_att2") != null) {
			Attack att = new Attack();
			att.setAttacktype(request.getParameter("pokemon_attack_type_2"));
			att.setAttackclass(request.getParameter("pokemon_attack_class_2"));
			if(att.getAttackclass().equals("status")) {
				att.setEffect(request.getParameter("effect2"));
			}
			poke.getAttacks().add(att);
			minimum = true;	
		}
		
		//Create Attack 3
		if(request.getParameter("cb_att3") != null) {
			Attack att = new Attack();
			att.setAttacktype(request.getParameter("pokemon_attack_type_3"));
			att.setAttackclass(request.getParameter("pokemon_attack_class_3"));
			if(att.getAttackclass().equals("status")) {
				att.setEffect(request.getParameter("effect3"));
			}
			poke.getAttacks().add(att);
			minimum = true;	
		}
		
		//Create Attack 4
		if(request.getParameter("cb_att4") != null) {
			Attack att = new Attack();
			att.setAttacktype(request.getParameter("pokemon_attack_type_4"));
			att.setAttackclass(request.getParameter("pokemon_attack_class_4"));
			if(att.getAttackclass().equals("status")) {
				att.setEffect(request.getParameter("effect4"));
			}
			poke.getAttacks().add(att);
			minimum = true;	
		}
		if(minimum) {
			// Suche ähnliche Pokemon aus der Fallbasis
			retrieveSimilarPokemon(poke, session);
		} else {
			request.setAttribute("message", "Please fill in all empty fields");
		}
		// Spaltennamen übergeben
		request.setAttribute("columnNames", new String[] { "Name", "Type 1", "Type 2", "Hitpoints", "Attack", "Defense", "Sp.Attack", "Sp.Defense", "Speed", "Similarity", "Add to Quicklist"});
		session.setAttribute("resultCases", resultCases);
		request.getRequestDispatcher("pokemonSimilarityFinder.jsp").forward(request, response);
	}
	
	// Methode um mit den erhaltenen Daten die ähnlichstens Pokemon aus der Fallbasis abzurufen
	private void retrieveSimilarPokemon(Pokemon poke, HttpSession session) {
		allCases = Retrieval_Pokemon.retrieveSimCases((CaseBaseLoader_Pokemon) session.getAttribute("cbl"), poke);
		//decrease number of cases to best 10
		if(allCases.size() > 0) {
			for(int i = 0; i < 9; i++) {
				resultCases.add(allCases.get(i));
			}
		}
	}
}
