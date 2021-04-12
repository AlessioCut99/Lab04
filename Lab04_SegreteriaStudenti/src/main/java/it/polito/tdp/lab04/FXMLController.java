/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Corso> corsi;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboCorso"
    private ComboBox<Corso> comboCorso; // Value injected by FXMLLoader

    @FXML // fx:id="bntCercaIscrittiCorso"
    private Button bntCercaIscrittiCorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="bntCercaNome"
    private Button bntCercaNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="bntCerca"
    private Button bntCerca; // Value injected by FXMLLoader

    @FXML // fx:id="bntIscrivi"
    private Button bntIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="bntReset"
    private Button bntReset; // Value injected by FXMLLoader

    @FXML
    void doCercaCorsi(ActionEvent event) {


		txtResult.clear();

		try {

			int matricola = Integer.parseInt(txtMatricola.getText());

			Studente studente = model.getStudente(matricola);
			
			if (studente == null) {
				txtResult.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			List<Corso> corsi = model.cercaCorsoDatoStudente(studente);

			StringBuilder sb = new StringBuilder();
			
			txtResult.setStyle("-fx-font-family: monospace");

			for (Corso corso : corsi) {
				sb.append(String.format("%-10s ", corso.getCodins()));
				sb.append(String.format("%-4d ", corso.getCrediti()));
				sb.append(String.format("%-45s ", corso.getNome()));
				sb.append(String.format("%-4d ", corso.getPd()));
				sb.append("\n");
			}
			txtResult.appendText(sb.toString());

		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    	
    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {

    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	try {

			Corso corso = comboCorso.getValue();
			if (corso == null) {
				txtResult.setText("Selezionare un corso.");
				return;
			}

			List<Studente> studenti = model.studentiIscrittiAlCorso(corso);

			StringBuilder sb = new StringBuilder();
			txtResult.setStyle("-fx-font-family: monospace");
			
			for (Studente studente : studenti) {

				sb.append(String.format("%-10s ", studente.getMatricola()));
				sb.append(String.format("%-20s ", studente.getNome()));
				sb.append(String.format("%-20s ", studente.getCognome()));
				
				sb.append(String.format("%-10s ", studente.getCDS()));
				sb.append("\n");
			}

			txtResult.appendText(sb.toString());

		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    	
    }

    @FXML
    void doCercaNome(ActionEvent event) {

    	txtResult.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	
    	try {
			int matricola = Integer.parseInt(txtMatricola.getText());
			
			Studente studente = model.getStudente(matricola);
			
			if(studente == null) {
				txtResult.appendText("Nessun risultato :La matricola non esiste");
				return;
			}
    		
			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());
			
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    }

    @FXML
    void doIscrivi(ActionEvent event) {

    	txtResult.clear();
    	
    	try {
			
    		if(txtMatricola.getText().isEmpty()) {
    			txtResult.setText("Inserire una matricola");
    			return;
    		}
    		
    		if(comboCorso.getValue()== null) {
    			txtResult.setText("Selezionare un corso");
    			return;
    		}
    		
    		//Prendo la matricola in input
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		
    		Studente studente = model.getStudente(matricola);
    		if(studente == null) {
    			txtResult.setText("Nessun risultato :La matricola non esiste");
    			return;
    		}
    		
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    		
    		Corso corso = comboCorso.getValue();
    		
    		//Studente iscritto al corso?
    		if(model.isStudenteIscrittoAlCorso(corso, studente)) {
    			txtResult.appendText("Studente già iscritto al corso");
    			return;
    		}
    		
    		if(!model.iscriviStudenteAlCorso(studente, corso)) {
    			txtResult.appendText("Errore durante l'iscrizione");
    			return;
    		}else {
    			txtResult.appendText("Studente iscritto correttamente");
    			return;
    		}
    		
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtMatricola.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	txtResult.clear();
    	comboCorso.getSelectionModel().clearSelection();

    }
    
    private void setComboItems() {
    	//ottieni tutti i corsi del model
    	corsi = model.getTuttiICorsi();
    	
    	//Aggiungi tutti i corsi alla ComboBox. 
    	Collections.sort(corsi); // per avere maggiore ordine li sorto alfabeticamente
    	comboCorso.getItems().addAll(corsi); // richiama il toString dell'oggetto Corso
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bntCercaIscrittiCorso != null : "fx:id=\"bntCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bntCercaNome != null : "fx:id=\"bntCercaNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bntCerca != null : "fx:id=\"bntCerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bntIscrivi != null : "fx:id=\"bntIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert bntReset != null : "fx:id=\"bntReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model= model;
    	setComboItems();
    }
}
