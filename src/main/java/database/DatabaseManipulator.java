package database;

import java.sql.Connection;

public class DatabaseManipulator {
	//Membervariables
	private Connection con;
	
	public DatabaseManipulator(Connection con) {
		this.con = con;
	}
}
