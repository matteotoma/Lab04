package it.polito.tdp.lab04;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private ObservableList<String> list = FXCollections.observableArrayList("Gestione dell'innovazione e sviluppo prodotto", "Gestione dell'innovazione e sviluppo prodotto ICT", "Ingegneria della qualità", "Strategia, tecnologia e marketing", "Economia e finanza d'impresa", "Analisi e gestione dei sistemi produttivi", "Analisi finanziaria e creditizia per l'impresa", "Diritto commerciale", "Economia dei sistemi industriali", "Sistemi informativi aziendali", "Analisi dei sistemi economici", "Economia aziendale", "Economia aziendale", "Gestione dei progetti", "");

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> chcBox;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	String matricola = txtMatricola.getText();
    	int m;
		try {
			m = Integer.parseInt(matricola);
		}
		catch(Exception e) {
			throw new RuntimeException("Formato matricola errato" + e);
		}
		List<Corso> corsi = new ArrayList<>();
		corsi.addAll(model.getCorsiByStudente(matricola));
		if(corsi.size()==0)
			txtRisultato.appendText("Studente non iscritto ad alcun corso");
		else {
			for(Corso c: corsi)
				txtRisultato.appendText(c.getCodins()+" "+c.getCrediti()+" "+c.getNome()+" "+c.getPd()+"\n");
		}
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	String nomeCorso = chcBox.getValue();
    	if(nomeCorso.equals("") || nomeCorso.equals("Corsi"))
    		txtRisultato.appendText("Devi selezionare un corso\n");
    	else {
    		List<Studente> studenti = new ArrayList<>();
    		studenti.addAll(model.getStudentiByCorso(nomeCorso));
    		if(studenti.size()==0)
    			txtRisultato.appendText("Corso senza iscritti");
    		else {
    			for(Studente s: studenti)
    				txtRisultato.appendText(s.getMatricola()+" "+s.getNome()+" "+s.getCognome()+" "+s.getCds()+"\n");
    		}
    	}
    }

    @FXML
    void doCercaStudente(ActionEvent event) {
    	String matricola = txtMatricola.getText();
    	int m;
		try {
			m = Integer.parseInt(matricola);
		}
		catch(Exception e) {
			throw new RuntimeException("Formato matricola errato" + e);
		}
    	Studente s = model.getStudenteByMatricola(txtMatricola.getText());
    	if(s!=null) {
    		txtNome.setText(s.getNome());
    		txtCognome.setText(s.getCognome());
    	}
    	else
    		txtRisultato.appendText("Questa matricola non corrisponde a nessuno studente");
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	boolean iscritto;
    	this.doCercaStudente(event);
    	if(!txtRisultato.getText().equals("Questa matricola non corrisponde a nessuno studente")) {
    		Studente s = model.getStudenteByMatricola(txtMatricola.getText());
    		String nomeCorso = chcBox.getValue();
        	if(nomeCorso.equals("") || nomeCorso.equals("Corsi")) {
        		txtRisultato.appendText("Devi selezionare un corso\n");
        		return;
        	}
        	iscritto = model.iscriviStudente(s, nomeCorso);
        	if(iscritto)
        		txtRisultato.appendText("Studente iscritto al corso!\n");
        	else
        		txtRisultato.appendText("Studente già iscritto a questo corso\n");
    		
    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    }

    @FXML
    void initialize() {
        assert chcBox != null : "fx:id=\"chcBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMatricola != null : "fx:id=\"btnMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        
        chcBox.setItems(list);
        chcBox.setValue("");

    }
    
    public void setModel(Model m) {
    	this.model = m;
    }
    
}
