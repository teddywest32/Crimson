package subterranean.crimson.universal.database;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import subterranean.crimson.universal.Logger;
import subterranean.crimson.universal.objects.InvalidObjectException;
import subterranean.crimson.universal.objects.ObjectTransfer;

public class Database {

	private boolean firstRUN;
	private Connection connection = null;
	private short entries = 0;

	private HashMap<Short, Serializable> buffer = new HashMap<Short, Serializable>();

	public Database(File settings) {
		firstRUN = !settings.exists();
		// load the sqlite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + settings.getAbsolutePath());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		exeU("CREATE TABLE IF NOT EXISTS storage (OID INT2, Object VARCHAR(32000));");

		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM storage ORDER BY `OID` DESC LIMIT 1");

			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {

				entries = 0;
			} else {
				entries = rs.getShort("OID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFirstRun() {
		return firstRUN;
	}

	private void exeU(String s) {
		try {
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);
			statement.executeUpdate(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		flush();
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Serializable retrieveObject(short dataID) {
		if (buffer.containsKey(dataID)) {
			return buffer.get(dataID);
		}
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM storage WHERE `OID`=?");
			stmt.setString(1, "" + dataID);

			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {

				return null;
			}
			Serializable s = ObjectTransfer.fromString(rs.getString("Object"));
			buffer.put(dataID, s);
			return s;
		} catch (SQLException | InvalidObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public synchronized short storeObject(Serializable object) {
		entries++;
		exeU("INSERT INTO storage(OID, Object) VALUES (" + entries + ", 'empty')");
		storeObject(object, entries);

		return entries;
	}

	public synchronized void storeObject(Serializable object, short dataID) {
		try {
			PreparedStatement stmt = connection.prepareStatement("UPDATE storage SET `Object`=? WHERE `OID`=?");
			stmt.setString(1, ObjectTransfer.toString(object, false));
			stmt.setString(2, "" + dataID);
			stmt.executeUpdate();

		} catch (SQLException w) {
			// TODO Auto-generated catch block
			w.printStackTrace();
		}
	}

	public synchronized void flush() {
		Logger.add("Flushing database buffer");
		HashMap<Short, Serializable> temp = new HashMap<Short, Serializable>();
		temp.putAll(buffer);
		buffer = new HashMap<Short, Serializable>();

		for (Short d : temp.keySet()) {
			storeObject(temp.get(d), d);
		}

	}

}
