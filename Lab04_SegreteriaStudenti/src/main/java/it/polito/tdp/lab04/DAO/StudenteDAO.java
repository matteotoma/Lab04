package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public List<Studente> getTuttiGliStudenti(){
		final String sql = "SELECT * FROM studente";
		List<Studente> studenti = new ArrayList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getString("matricola"), rs.getString("nome"),
										rs.getString("cognome"), rs.getString("CDS"));
				studenti.add(s);
			}
			conn.close();
			return studenti;
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore db" + e);
		}
	}

}
