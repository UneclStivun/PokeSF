package pokemon;

import java.io.IOException;
import java.util.Enumeration;

import cbr_Pokemon.CaseBaseLoader_Pokemon;
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

		String pokemon_name;
		String pokemon_type_1;
		String pokemon_type_2;
		int pokemon_hp;
		int pokemon_attack;
		int pokemon_defense;
		int pokemon_specialattack;
		int pokemon_specialdefense;
		int pokemon_speed;
		String pokemon_attack_type_1;
		String pokemon_attack_type_2;
		String pokemon_attack_type_3;
		String pokemon_attack_type_4;
		String pokemon_attack_class_1;
		String pokemon_attack_class_2;
		String pokemon_attack_class_3;
		String pokemon_attack_class_4;
		String effect1;
		String effect2;
		String effect3;
		String effect4;
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
		if(!request.getParameter("pokemon_attack_type_1").isEmpty()) {
			Attack att = new Attack();
			att.setAttacktype(request.getParameter("pokemon_attack_type_1"));
			minimum = true;	
		}
		
		if(!request.getParameter("pokemon_attack_class_1").isEmpty()) {
			pokemon_attack_class_1 = request.getParameter("pokemon_attack_class_1");
			minimum = true;
		}
		//Create Attack 2
		if(!request.getParameter("pokemon_attack_type_2").isEmpty()) {
			pokemon_attack_type_2 = request.getParameter("pokemon_attack_type_2");
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_attack_class_2").isEmpty()) {
			pokemon_attack_class_2 = request.getParameter("pokemon_attack_class_2");
			minimum = true;
		}
		
		//Create Attack 3
		if(!request.getParameter("pokemon_attack_type_3").isEmpty()) {
			pokemon_attack_type_3 = request.getParameter("pokemon_attack_type_3");
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_attack_class_3").isEmpty()) {
			pokemon_attack_class_3 = request.getParameter("pokemon_attack_class_3");
			minimum = true;
		}
		
		//Create Attack 4
		if(!request.getParameter("pokemon_attack_type_4").isEmpty()) {
			pokemon_attack_type_4 = request.getParameter("pokemon_attack_type_4");
			minimum = true;
		}
		
		if(!request.getParameter("pokemon_attack_class_4").isEmpty()) {
			pokemon_attack_class_4 = request.getParameter("pokemon_attack_class_4");
			minimum = true;
		}
		if(minimum) {
			// Suche ähnliche Pokemon aus der Fallbasis
			retrieveSimilarPokemon(poke, request.getSession());
		} else {
			request.setAttribute("message", "Please fill in all empty fields");
		}
	}
	
	// Methode um mit den erhaltenen Daten die ähnlichstens Pokemon aus der Fallbasis abzurufen
	private void retrieveSimilarPokemon(Pokemon poke, HttpSession session) {
		Retrieval_Pokemon.retrieveSimCases((CaseBaseLoader_Pokemon) session.getAttribute("cbl"), poke);
	}
}
