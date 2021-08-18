package pokemon;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletPokemonSimilarityFinder
 */
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
		
		// Prüfen ob alle Formularfelder ausgefüllt wurden
		if(!request.getParameter("pokemon_name").isEmpty() && !request.getParameter("pokemon_type_1").isEmpty() && !request.getParameter("pokemon_type_2").isEmpty()
				&& !request.getParameter("pokemon_hp").isEmpty() && !request.getParameter("pokemon_attack").isEmpty() && !request.getParameter("pokemon_defense").isEmpty()
				&& !request.getParameter("pokemon_specialattack").isEmpty() && !request.getParameter("pokemon_specialdefense").isEmpty() && !request.getParameter("pokemon_speed").isEmpty()
				&& !request.getParameter("pokemon_attack_type_1").isEmpty() && !request.getParameter("pokemon_attack_type_2").isEmpty() && !request.getParameter("pokemon_attack_type_3").isEmpty()
				&& !request.getParameter("pokemon_attack_type_4").isEmpty() && !request.getParameter("pokemon_attack_class_1").isEmpty() && !request.getParameter("pokemon_attack_class_2").isEmpty()
				&& !request.getParameter("pokemon_attack_class_3").isEmpty() && !request.getParameter("pokemon_attack_class_4").isEmpty()) {
			
			// Wenn alle Felder gefüllt, setze Werte
			pokemon_name = request.getParameter("pokemon_name");
			pokemon_type_1 = request.getParameter("pokemon_type_1");
			pokemon_type_2 = request.getParameter("pokemon_type_2");			
			pokemon_hp = Integer.parseInt(request.getParameter("pokemon_hp"));
			pokemon_attack = Integer.parseInt(request.getParameter("pokemon_attack"));
			pokemon_defense = Integer.parseInt(request.getParameter("pokemon_defense"));
			pokemon_specialattack = Integer.parseInt(request.getParameter("pokemon_specialattack"));
			pokemon_specialdefense = Integer.parseInt(request.getParameter("pokemon_specialdefense"));
			pokemon_speed = Integer.parseInt(request.getParameter("pokemon_speed"));
	
		} else {
			
		}
		
		// Suche ähnliche Pokemon aus der Fallbasis
		retrieveSimilarPokemon();
	}
	
	// Methode um mit den erhaltenen Daten die ähnlichstens Pokemon aus der Fallbasis abzurufen
	private void retrieveSimilarPokemon() {
		
	}
}
