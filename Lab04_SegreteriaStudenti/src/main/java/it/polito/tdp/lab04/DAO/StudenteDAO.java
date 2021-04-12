package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudente (int matricola) {

		final String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola=?";

		Studente studente = new Studente(matricola);

		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				studente = new Studente(rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
		
				
			}

			conn.close();
			
			return studente;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	

	public boolean isStudenteIscrittoAlCorso(Studente studente, Corso corso) {

		final String sql = "SELECT * FROM iscrizione WHERE matricola=? AND codins=?";

		boolean toreturn = false;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins() );

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				toreturn = true;
			}

			conn.close();
			
			return toreturn;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public List<Corso> getCorsiFromStudente(Studente studente) {

		final String sql = "SELECT * "
				+ "FROM iscrizione, corso "
				+ "WHERE iscrizione.codins= corso.codins "
				+ "AND matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corsi.add(new Corso(codins,nome,numeroCrediti,periodoDidattico));
				
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
}
