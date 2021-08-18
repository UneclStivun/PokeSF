package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManipulator {
	// Klassenvariablen
	private Connection con;
	
	// Konstruktor, in welchem die Verbindung aufgerufen wird
	public DatabaseManipulator() {
		this.con = DatabaseConnector.getConnection();
	}
	
	// Methode zum Überprüfen, ob die Email einmalig ist
	public boolean emailIsUnique(String user_email) throws SQLException {
			
		boolean emailIsUnique = false;
			
		// Erstelle Query um gleiche Mailszu filtern
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
		
	// Methode zum Hinzufügen eines Nutzers in die Datenbank
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
	
	// Methode zum Hinzufügen von Pokemon in die Datenbank
	public void addPokemonToDatabase(String pokemon_name, String pokemon_type_1, String pokemon_type_2,
			int pokemon_hp, int pokemon_attack, int pokemon_defense, int pokemon_specialattack,
			int pokemon_specialdefense, int pokemon_speed, String pokemon_attack_type_1,
			String pokemon_attack_type_2, String pokemon_attack_type_3, String pokemon_attack_type_4,
			String pokemon_attack_class_1, String pokemon_attack_class_2, String pokemon_attack_class_3,
			String pokemon_attack_class_4) throws SQLException {
		
		// Erstelle Query zum Hinzufügen eines Pokemons in die Datenbank
		String queryAddPokemon = "INSERT INTO pokemon values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pStmntAddPokemon = con.prepareStatement(queryAddPokemon);
		
		// Der Query werden die Pokemon-Werte zugewiesen
		pStmntAddPokemon.setString(1, pokemon_name);
		pStmntAddPokemon.setString(2, pokemon_type_1);
		pStmntAddPokemon.setString(3, pokemon_type_2);
		pStmntAddPokemon.setInt(4, pokemon_hp);
		pStmntAddPokemon.setInt(5, pokemon_attack);
		pStmntAddPokemon.setInt(6, pokemon_defense);
		pStmntAddPokemon.setInt(7, pokemon_specialattack);
		pStmntAddPokemon.setInt(8, pokemon_specialdefense);
		pStmntAddPokemon.setInt(9, pokemon_speed);
		pStmntAddPokemon.setString(10, pokemon_attack_type_1);
		pStmntAddPokemon.setString(11, pokemon_attack_type_2);
		pStmntAddPokemon.setString(12, pokemon_attack_type_3);
		pStmntAddPokemon.setString(13, pokemon_attack_type_4);
		pStmntAddPokemon.setString(14, pokemon_attack_class_1);
		pStmntAddPokemon.setString(15, pokemon_attack_class_2);
		pStmntAddPokemon.setString(16, pokemon_attack_class_3);
		pStmntAddPokemon.setString(17, pokemon_attack_class_4);
		pStmntAddPokemon.setBoolean(18, false);
		
		// Query wird ausgeführt
		pStmntAddPokemon.executeUpdate();
		
		// Datenbankverbindung wird geschlossen
		con.close();
	}
}
