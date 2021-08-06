package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Klasse um die Verbindung zur SQL-Datenbank zu erstellen
 * @author DBAE Wintersemester 17/18
 * 
 */
public class DatabaseConnector {
	
	// Zugangsdaten zur Datenbank
	protected static Connection con;
	private static final String DB_SERVER = "207.154.234.136:5432";
	private static final String DB_NAME = "2021-Pokemon";
	private static final String DB_USER = "2021-Pokemon";
	private static final String DB_PASSWORD = "2f6869d2f26a607eb86e39d483720685";
	private static final String DB_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://" + DB_SERVER + "/" + DB_NAME;

	/** Initialisiere eine Verbindung zur Datenbank */
	private static Connection init() {
		try {
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			return con;
			
		// Falls ein Fehler entsteht: 
		// Pr�fen Sie, ob Sie die postgresql Treiberdatei (.jar) unter WEB-INF/lib eingef�gt ist
		// Pr�fen Sie, ob Ihre Zugangsdaten korrekt sind
		// Lesen Sie die Fehlermeldung und beheben Sie sie :)
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("PostgresDb: Something went wrong: ");
			e.printStackTrace();
		}
		// Gibt nur etwas zur�ck, wenn der try-Block auch erfolgreich war!
		return null;
	}

	/** Rufe eine Verbindung auf, wenn eine bereits existiert. Wenn nicht, rufe init() auf */
	public static Connection getConnection() {
		try {
			return (con == null || con.isClosed()) ? init() : con;
		} catch (SQLException e) {
			e.printStackTrace();
			return init();
		}
	}

	/** Schlie�e die Verbindung */
	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
