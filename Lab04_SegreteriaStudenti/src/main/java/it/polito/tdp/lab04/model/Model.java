package it.polito.tdp.lab04.model;

import java.util.ArrayList;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO daoC;
	private StudenteDAO daoS;
	
	public Model() {
		daoC = new CorsoDAO();
		daoS = new StudenteDAO();
	}
	
	public List<Corso> getCorsi(){
		return daoC.getTuttiICorsi();
	}
	
	public ArrayList<String> getNomeCorsi(){
		List<Corso> corsi = new ArrayList<Corso>(daoC.getTuttiICorsi());
		ArrayList<String> nomi = new ArrayList<String>();
		for(Corso c: corsi)
				nomi.add(c.getNome());
		return nomi;
	}
	
	public List<Studente> getStudenti(){
		return daoS.getTuttiGliStudenti();
	}
	
	public Studente getStudenteByMatricola(String matricola) {
		List<Studente> studenti = new ArrayList<>(this.getStudenti());
		for(Studente s: studenti)
			if(s.getMatricola().equals(matricola))
				return s;
		return null;
	}
	
	public List<Studente> getStudentiByCorso(String nomeCorso){
		return daoC.getStudentiByCorso(nomeCorso);
	}
	
	public List<Corso> getCorsiByStudente(String matricola){
		return daoC.getCorsiByStudente(matricola);
	}
	
	public boolean iscriviStudente(Studente s, String nomeCorso) {
		Corso c;
		for(Corso cTemp: this.getCorsi()) {
			if(cTemp.getNome().equals(nomeCorso)) {
				c = daoC.getCorso(cTemp.getCodins());
				return daoC.inscriviStudenteACorso(s, c);
			}
		}
		return false;
	}
	
}
