package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pokemon.Pokemon;
import pokemon.Pokemonteam;

public class DatabaseManipulator {
	// Klassenvariablen
	private Connection con;
	
	// Konstruktor, in welchem die Verbindung aufgerufen wird
	public DatabaseManipulator() {
		this.con = DatabaseConnector.getConnection();
	}
	
	//==== Methode zum Überprüfen, ob die Email einmalig ist ====//
	public boolean emailIsUnique(String user_email) throws SQLException {
		
		boolean emailIsUnique = false;
			
		// Erstelle Query, um gleiche Mails zu filtern
		String queryGetMail = "SELECT email FROM account WHERE email ILIKE ?";
		PreparedStatement pStmntGetMail = con.prepareStatement(queryGetMail);
			
		// Der Query wird die Email angehängt
		pStmntGetMail.setString(1, user_email);
		
		// Query wird ausgeführt und es wird geprüft, ob ein Ergebnis zurückkommt
		// Falls kein Ergebnis geliefert wird, ist Email einzigartig
		if (!pStmntGetMail.executeQuery().next()) {
			emailIsUnique = true;
		}
		
		return emailIsUnique;
	}
	
	//==== Methode zum Überprüfen, ob User vorhanden ist ====//
	public boolean userExists(String user_email, String user_password) throws SQLException {
		
		boolean userExists = false;
		
		// Erstelle Query, um User aus der Datenbank zu suchen
		String queryGetUser = "SELECT email FROM account WHERE email = ? AND password = ?";	
		PreparedStatement pStmntGetUser = con.prepareStatement(queryGetUser);
		
		// Der Query wird die Email und das Passwort angehängt
		pStmntGetUser.setString(1, user_email);
		pStmntGetUser.setString(2, user_password);
		
		// Query wird ausgeführt und es wird geprüft, ob ein Ergebnis zurückkommt
		// Falls ein Ergebnis geliefert wird, ist Kombination von Email und Passwort gültig
		if (pStmntGetUser.executeQuery().next()) {
			userExists = true;
		}
		
		return userExists;
	}
	
	//==== Methode zum Einholen der Nutzerdaten ====//
	public ArrayList<String[]> getUserData(String user_email) throws SQLException {
		
		ArrayList<String[]> userData = new ArrayList<String[]>();
		
		// Erstelle Query, um Nutzerdaten eines Nutzers zu erhalten
		String queryGetUserData = "SELECT username, password, email, role FROM account WHERE email = ?";
		PreparedStatement pStmntGetUserData = con.prepareStatement(queryGetUserData);
		
		// Der Query wird die Email des Nutzers angehängt
		pStmntGetUserData.setString(1, user_email);
		
		// Query wird ausgeführt und die Ergebnisse werden in einem Array gespeichert
		// Das Array wird zur Rückgabe in einer ArrayList gespeichert
		ResultSet uds = pStmntGetUserData.executeQuery();
		
		while(uds.next()) {
			String[] s = {uds.getString(1), uds.getString(2), uds.getString(3), uds.getString(4)};
			userData.add(s);
		}
		
		// Datenbankverbindung wird geschlossen
		con.close();
		
		return userData;
	}
		
	//==== Methode zum Hinzufügen eines Nutzers in die Datenbank ====//
	public void addUserToDatabase(String user_name, String user_password, String user_email) throws SQLException {
		
		// Erstelle Query zum Hinzufügen eines Nutzers in die Datenbank
		String queryAddUser = "INSERT INTO account values (?,?,?,?)";
		PreparedStatement pStmntAddUser = con.prepareStatement(queryAddUser);
		
		// Dem Query werden die Nutzerdaten zugewiesen
		pStmntAddUser.setString(1, user_name);
		pStmntAddUser.setString(2, user_email);
		pStmntAddUser.setString(3, user_password);
		pStmntAddUser.setString(4, "user");
		
		// Query wird ausgeführt
		pStmntAddUser.executeUpdate();
		
		// Datenbankverbindung wird geschlossen
		con.close();
	}
	
	//==== Methode zum Hinzufügen von Pokemon in die Datenbank ====//
	public void addPokemonToDatabase(Pokemon pokemonObject) throws SQLException {
		
		// Erstelle Query zum Hinzufügen eines Pokemons in die Datenbank
		String queryAddPokemon = "INSERT INTO pokemon values (?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pStmntAddPokemon = con.prepareStatement(queryAddPokemon);
		
		// Der Query werden die Pokemon-Werte zugewiesen
		pStmntAddPokemon.setString(1, pokemonObject.getName());
		pStmntAddPokemon.setString(2, pokemonObject.getType1());
		pStmntAddPokemon.setString(3, pokemonObject.getType2());
		pStmntAddPokemon.setInt(4, pokemonObject.getHitpoints());
		pStmntAddPokemon.setInt(5, pokemonObject.getAttack());
		pStmntAddPokemon.setInt(6, pokemonObject.getDefense());
		pStmntAddPokemon.setInt(7, pokemonObject.getSpAttack());
		pStmntAddPokemon.setInt(8, pokemonObject.getSpDefense());
		pStmntAddPokemon.setInt(9, pokemonObject.getInitiative());
		pStmntAddPokemon.setString(10, pokemonObject.attackListToString());
		pStmntAddPokemon.setBoolean(11, false);
		
		// Query wird ausgeführt
		pStmntAddPokemon.executeUpdate();
		
		// Datenbankverbindung wird geschlossen
		con.close();
	}
	
	//Method for pulling all Pokemonobjects from database
	//returns List of pokemon
	public List<Pokemon> getPokemonFromDatabase() {
		List<Pokemon> pokemonList = new ArrayList<Pokemon>();
		ResultSet rs;
		
		//Create Query
		String query = "SELECT * FROM Pokemon WHERE validation = true ORDER BY pokeid";
		
		try {
			PreparedStatement pstat = con.prepareStatement(query);
			rs = pstat.executeQuery();		
			while(rs.next()) {
				//Create pokemon object from database
				Pokemon pokemon = new Pokemon(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
				pokemon.setDatabaseID(rs.getInt(12));
				
				//Add attacks to pokemon
				pokemon.translateAttacksFromDB(rs.getString(10));
				pokemonList.add(pokemon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pokemonList;
	}
	
	//Method for pulling all (in)validated Pokemonobjects from database
	//returns List of pokemon
	public List<Pokemon> getValidatedPokemonFromDatabase(boolean setValidation) {
		List<Pokemon> pokemonList = new ArrayList<Pokemon>();
		ResultSet rs;
			
		//Create Query where validation = true
		String query = "SELECT * FROM pokemon WHERE validation = ? ORDER BY pokeid";
			
		try {
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setBoolean(1, setValidation);
			rs = pstat.executeQuery();
			
			while(rs.next()) {
				//Create pokemon object from database
				Pokemon pokemon = new Pokemon(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9));
				pokemon.setDatabaseID(rs.getInt(12));
					
				//Add attacks to pokemon
				pokemon.translateAttacksFromDB(rs.getString(10));
				pokemonList.add(pokemon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pokemonList;
	}
	
	//Methode zum Validieren oder Invalidieren eines Pokemons
	public void validatePokemon(boolean setValidation, int pokemon_id) {
		
		String queryValidate = "UPDATE pokemon SET validation = ? WHERE pokeid = ?";
				
		try {
			PreparedStatement pStmntValidate = con.prepareStatement(queryValidate);
			
			pStmntValidate.setBoolean(1, setValidation);
			pStmntValidate.setInt(2, pokemon_id);
			
			pStmntValidate.executeUpdate();
			
			// Datenbankverbindung schließen
			con.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Methode zumLöschen von Pokemon anhand der Pokemon-ID
	public void deletePokemon(int pokemon_id) {
		
		String queryValidate = "DELETE FROM pokemon WHERE pokeid = ?";
		
		try {
			PreparedStatement pStmntValidate = con.prepareStatement(queryValidate);
			
			pStmntValidate.setInt(1, pokemon_id);
			
			pStmntValidate.executeUpdate();
			
			// Datenbankverbindung schließen
			con.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addPokemonteamToDatabase(Pokemonteam team) throws SQLException {
		// Erstelle Query zum Hinzufügen eines Pokemons in die Datenbank
				String queryAddPokemon = "INSERT INTO pokemonteam values (?,?,?,?,?,?,?)";
				PreparedStatement pStmntAddPokemon = con.prepareStatement(queryAddPokemon);
				
				// Der Query werden die Pokemon-Werte zugewiesen
				pStmntAddPokemon.setInt(1, team.getPokemon().get(0).getDatabaseID());
				pStmntAddPokemon.setInt(2, team.getPokemon().get(1).getDatabaseID());
				pStmntAddPokemon.setInt(3, team.getPokemon().get(2).getDatabaseID());
				pStmntAddPokemon.setInt(4, team.getPokemon().get(3).getDatabaseID());
				pStmntAddPokemon.setInt(5, team.getPokemon().get(4).getDatabaseID());
				pStmntAddPokemon.setInt(6, team.getPokemon().get(5).getDatabaseID());
				pStmntAddPokemon.setString(7, team.getTeamname());
				
				// Query wird ausgeführt
				pStmntAddPokemon.executeUpdate();
				
				// Datenbankverbindung wird geschlossen
				con.close();
	}
}
