package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;

	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}
	
	public Studente getStudente(int matricola) {
		return studenteDao.getStudente(matricola);
	}
	
	public List<Studente> studentiIscrittiAlCorso(Corso corso){
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> cercaCorsoDatoStudente(Studente studente){
		return studenteDao.getCorsiFromStudente(studente);
	}
	
	public boolean isStudenteIscrittoAlCorso(Corso corso, Studente studente) {
		return studenteDao.isStudenteIscrittoAlCorso(studente, corso);
	}
	
	public boolean iscriviStudenteAlCorso(Studente studente , Corso corso) {
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}
	
	public List<Corso> getTuttiICorsi() {
		return corsoDao.getTuttiICorsi();
	}
	
}
