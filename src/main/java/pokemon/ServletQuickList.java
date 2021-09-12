package pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cbr_Pokemon.Case_Pokemon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletQuickList
 */
public class ServletQuickList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServletQuickList() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		//Prüfe ob Session bereits Liste hat => Checke ob neue Pokemon bereits darin enthalten
		//Sonst erstelle Liste fülle mit Pokemon und übergebe an Session für Nutzung in Pokemonteam.jsp
		//Wichtig: Trennung von Case_Pokemon und Pokemon für einfachere Übergabe
		HttpSession session = request.getSession();
		List<Case_Pokemon> resultCases = (List)session.getAttribute("resultCases");
		List<Pokemon> quickListPokemon;
		
		//Check if there is already an existing quickList otherwise make one
		if(session.getAttribute("quickList") != null) {
			quickListPokemon = (List) session.getAttribute("quickList");
		} else {
			quickListPokemon = new ArrayList<Pokemon>();
		}
		
		//Extract all chosen Pokemon from possible checkboxes
		for(int i = 0; i < 9; i++) {
			if(request.getParameter("add" + i) != null) {
				if(checkUnique(resultCases.get(i), quickListPokemon)) {
					quickListPokemon.add(resultCases.get(i).getPokemon());
				}	
			}
		}
		session.setAttribute("quickList", quickListPokemon);
		request.getRequestDispatcher("pokemonSimilarityFinder.jsp").forward(request, response);
	}
	
	//check if Pokemon to Add is unique via DatbaseID
	private boolean checkUnique(Case_Pokemon casePoke, List<Pokemon> quickListPokemon) {
		boolean unique = true;
		if(quickListPokemon.size() > 0) {
		for(int i = 0; i < quickListPokemon.size(); i++) {			
				if(casePoke.getPokemon().getDatabaseID() == quickListPokemon.get(i).getDatabaseID()) {
					unique = false;
				}
			}
		}
		return unique;
	}

}
