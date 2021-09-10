package pokemon;

import java.io.IOException;
import java.sql.SQLException;

import database.DatabaseManipulator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletPokemonAdd
 */
@WebServlet("/ServletPokemonAdd")
public class ServletPokemonAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletPokemonAdd() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Something went wrong: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		int pokemon_attack_damage_1 = 100;
		int pokemon_attack_damage_2 = 100;
		int pokemon_attack_damage_3 = 100;
		int pokemon_attack_damage_4 = 100;
		String pokemon_attack_effect_1 = "";
		String pokemon_attack_effect_2 = "";
		String pokemon_attack_effect_3 = "";
		String pokemon_attack_effect_4 = "";
		
		// Prüfen ob alle Formularfelder ausgefüllt wurden
		if(!request.getParameter("pokemon_name").isEmpty() && !request.getParameter("pokemon_type_1").isEmpty()
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
			pokemon_attack_type_1 = request.getParameter("pokemon_attack_type_1");
			pokemon_attack_type_2 = request.getParameter("pokemon_attack_type_2");
			pokemon_attack_type_3 = request.getParameter("pokemon_attack_type_3");	
			pokemon_attack_type_4 = request.getParameter("pokemon_attack_type_4");
			pokemon_attack_class_1 = request.getParameter("pokemon_attack_class_1");
			pokemon_attack_class_2 = request.getParameter("pokemon_attack_class_2");
			pokemon_attack_class_3 = request.getParameter("pokemon_attack_class_3");
			pokemon_attack_class_4 = request.getParameter("pokemon_attack_class_4");
			
			// Wenn Attacke eine Statusattacke ist, setze Effekt-Parameter und Schaden auf 0
			if(pokemon_attack_class_1.equals("status")) {
				pokemon_attack_damage_1 = 0;
				pokemon_attack_effect_1 = request.getParameter("effect1");
			}
			if(pokemon_attack_class_2.equals("status")) {
				pokemon_attack_damage_2 = 0;
				pokemon_attack_effect_2 = request.getParameter("effect2");
			}
			if(pokemon_attack_class_3.equals("status")) {
				pokemon_attack_damage_3 = 0;
				pokemon_attack_effect_3 = request.getParameter("effect3");
			}
			if(pokemon_attack_class_4.equals("status")) {
				pokemon_attack_damage_4 = 0;
				pokemon_attack_effect_4 = request.getParameter("effect4");
			}
			
			// Wenn zwei mal der gleiche Typ angegeben, gebe entsprechende Meldung zurück
			if(pokemon_type_1.equals(pokemon_type_2)) {
				request.setAttribute("message", "A pokemon can't have the same type twice.");
				request.getRequestDispatcher("pokemonAdd.jsp").forward(request, response);
			}
			
			// Erstellen von Attacken, welche dem Pokemonobjekt hinzugefügt werden
			Attack pokemonObjectAttack_1 = new Attack(pokemon_attack_type_1, pokemon_attack_class_1, pokemon_attack_damage_1, pokemon_attack_effect_1);
			Attack pokemonObjectAttack_2 = new Attack(pokemon_attack_type_2, pokemon_attack_class_2, pokemon_attack_damage_2, pokemon_attack_effect_2);
			Attack pokemonObjectAttack_3 = new Attack(pokemon_attack_type_3, pokemon_attack_class_3, pokemon_attack_damage_3, pokemon_attack_effect_3);
			Attack pokemonObjectAttack_4 = new Attack(pokemon_attack_type_4, pokemon_attack_class_4, pokemon_attack_damage_4, pokemon_attack_effect_4);
			
			// Erstellen eines Pokemonobjektes, welches der Datenbank hinzugefügt wird
			Pokemon pokemonObject = new Pokemon(pokemon_name, pokemon_type_1, pokemon_type_2,
					pokemon_hp, pokemon_attack, pokemon_defense, pokemon_specialattack,
					pokemon_specialdefense, pokemon_speed);
			
			// Hinzufügen der Attackn zum Pokemonobjekt
			pokemonObject.addAttacks(pokemonObjectAttack_1, pokemonObjectAttack_2, pokemonObjectAttack_3, pokemonObjectAttack_4);
			
			// Instanz von DatabaseManipulator wird erstellt
			// Der Methode addPokemonToDatabase aus DatabaseManipulator wird das Pokemonobjekt übergeben
			DatabaseManipulator dmPokemonDatabase = new DatabaseManipulator();
			
			try {
				dmPokemonDatabase.addPokemonToDatabase(pokemonObject);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect("pokemonAdd.jsp");
				return;
			}
			
			// Gebe Nachricht mit Nutzereingaben als Bestätigung zurück
			request.setAttribute("message", "The following pokemon was added to the database:"
			+ "<br>Name: " + pokemon_name + "<br>Typ 1: " + pokemon_type_1 + "<br>Typ 2: " + pokemon_type_2 + "<br>Hit Points: " + pokemon_hp
			+ "<br>Attack: " + pokemon_attack + "<br>Defense: " + pokemon_defense + "<br>Specialattack: " + pokemon_specialattack
			+ "<br>Specialdefense: " + pokemon_specialdefense + "<br>Speed: " + pokemon_speed);
			
		// Wenn nicht alle Felder gefüllt, gebe entsprechende Nachricht zurück
		} else {
			request.setAttribute("message", "Please fill in all empty fields");
		}
		
		request.getRequestDispatcher("pokemonAdd.jsp").forward(request, response);
	}
}
