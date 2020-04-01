package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		System.out.println("------------------------");
		
		List<String> nomi = new ArrayList<>(model.getNomeCorsi());
		for(String n: nomi)
			System.out.println(n);
		
		System.out.println("------------------------");

		List<Corso> corsi = new ArrayList<>(model.getCorsi());
		for(Corso c: corsi)
			System.out.println(c.toString());
		
		System.out.println("------------------------");
		
		List<Studente> studenti = new ArrayList<>(model.getStudenti());
		for(Studente s: studenti)
			System.out.println(s.toString());
		
		System.out.println("------------------------");
		
		List<Studente> studenti2 = new ArrayList<>(model.getStudentiByCorso("Analisi dei sistemi economici"));
		for(Studente s: studenti2)
			System.out.println(s.getMatricola()+" "+s.getNome()+" "+s.getCognome()+" "+s.getCds()+"\n");
		
		System.out.println("------------------------");
		
		List<Corso> corsi2 = new ArrayList<>(model.getCorsiByStudente("200482"));
			for(Corso c: corsi2)
				System.out.println(c.getCodins()+" "+c.getCrediti()+" "+c.getNome()+" "+c.getPd()+"\n");
		
	}

}
