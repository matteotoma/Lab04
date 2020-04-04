package it.polito.tdp.lab04.DAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new ArrayList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codiceCorso) {
		List<Corso> l = new ArrayList<>(this.getTuttiICorsi());
		for(Corso c: l)
			if(c.getCodins().equals(codiceCorso))
				return c;
		return null;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiByCorso(String nomeCorso){
		final String sql="SELECT s.matricola, s.nome, s.cognome, s.CDS "
						+"FROM studente AS s, iscrizione i, corso c "
						+"WHERE c.codins=i.codins AND s.matricola=i.matricola AND c.nome=?";
		List<Studente> studenti = new ArrayList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nomeCorso);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getString("matricola"), rs.getString("cognome"),
										rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s);
			}
			conn.close();
			return studenti;
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore db" + e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		String sql1 = "SELECT s.matricola, s.cognome, s.nome, s.cds "
				+"FROM studente AS s, iscrizione i "
				+"WHERE i.matricola=s.matricola AND s.matricola=? AND i.codins=? ";
		String sql2 = "INSERT INTO iscrizione (matricola, codins) "
				+"VALUES (?, ?)";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st1 = conn.prepareStatement(sql1);
			st1.setString(1, studente.getMatricola());
			st1.setString(2, corso.getCodins());
			ResultSet rs1 = st1.executeQuery();
			while(rs1.next()) {
				return false;
			}
			PreparedStatement st2 = conn.prepareStatement(sql2);
			st2.setString(1, studente.getMatricola());
			st2.setString(2, corso.getCodins());
			ResultSet rs2 = st2.executeQuery();
			
			conn.close();
			return true;			
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore db" + e);
		}
	}
	
	/*
	 * Ottengo tutti i corsi a cui è iscritto lo studente
	 */
	public List<Corso> getCorsiByStudente(String matricola){
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
						+"FROM studente AS s, iscrizione i, corso c "
						+"WHERE c.codins=i.codins AND s.matricola=i.matricola AND s.matricola=?";
		List<Corso> corsi = new ArrayList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			conn.close();
			return corsi;
		}
		catch(SQLException e) {
			throw new RuntimeException("Errore db"+e);
		}
	}

}
