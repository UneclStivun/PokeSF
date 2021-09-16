package pokemon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletPokemonFight
 */
public class ServletPokemonFight extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletPokemonFight() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Session setzen oder wiederverwenden
		HttpSession session = request.getSession();
		
		// In der Session gespeichertes Pokemonteam aufrufen
		List<Pokemon> poketeam = (List)session.getAttribute("poketeam");
		
		boolean teamDefeated = false;
		
		switch(request.getParameter("action")) {
			case "switch":
				switchPokemon(poketeam, Integer.parseInt(request.getParameter("position")));
				break;
			case "damage":
				teamDefeated = calculateDamage(poketeam);
				break;
			default:
				break;
		}
		
//		// Rückgabeinhalt auf JSON einstellen
//		response.setContentType("application/json");
//	    PrintWriter out = response.getWriter();
//	    
//		JSONObject pokeToJson = new JSONObject("{"
//				+ "defeated:" + teamDefeated 
//				+ ",pokeName:" + poketeam.get(0).getName()
//				+ ",pokeHP:" + poketeam.get(0).getHitpoints()
//				+ ",pokeAil:" + poketeam.get(0).getAil1()
//				+ "}");
//		
//		out.print(pokeToJson);
		response.sendRedirect("pokemonFight.jsp");
	}
	
	// Methode zum Austauschen der Pokemon.
	// Zu tauschende Pokemon tauschen den Platz in der Liste
	private void switchPokemon(List<Pokemon> poketeam, int position) {
		Pokemon pokemonPlaceholder = poketeam.get(0);
		
		poketeam.set(0, poketeam.get(position));
		
		poketeam.set(position, pokemonPlaceholder);
	}
	
	// Methode für die Schadensberechnung
	// Gibt einen boolean zurück, der angibt, ob das Team besiegt wurde
	private boolean calculateDamage(List<Pokemon> poketeam) {
		int countDefeated = 0;
		boolean isDefeated = false;
		
		poketeam.get(0).setHitpoints(poketeam.get(0).getHitpoints()-10);
		
		// Wenn Hitpoints unter 1 fallen, setze sie auf 0 und vergebe den Status 'Besiegt'
		if(poketeam.get(0).getHitpoints() < 1) {
			poketeam.get(0).setHitpoints(0);
			poketeam.get(0).setAil1("K.O.");
			
			// Prüfe für jedes Pokemon im Team, ob Pokemon besiegt wurde
			for(int i = 0; i < poketeam.size(); i++) {
				if(poketeam.get(i).getAil1() != null) {
					if(poketeam.get(i).getAil1().equals("K.O.")) {
						countDefeated++;
					}
				}
			}
			
			// Wurden soviele Pokemon besiegt, wie im Team sind, wurde der Spieler besiegt
			if(countDefeated == poketeam.size()) {
				isDefeated = true;
			}
		}
		return isDefeated;
	}
}
