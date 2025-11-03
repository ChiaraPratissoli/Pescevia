package com.example.fishmanagement.controller;

import com.example.fishmanagement.database.PesceDAO;
import com.example.fishmanagement.model.Pesce;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {
    //Catalogo

    @FXML
    private TableColumn<Pesce, Double> colDisponibilita;
    @FXML
    private TableColumn<Pesce, String> colNome;
    @FXML
    private TableColumn<Pesce, Double> colPrezzo;
    @FXML
    private TableView<Pesce> tablePesci;
    @FXML
    private TextField txtQuantita;

    //Carrello
    @FXML
    private TableView<Pesce> tableCarrello;
    @FXML
    private TableColumn<Pesce, String> colCarrelloNome;
    @FXML
    private TableColumn<Pesce, Double> colCarrelloPrezzo;
    @FXML
    private TableColumn<Pesce, Double> colCarrelloQuantita;

    private final PesceDAO pesceDAO = new PesceDAO();
    private ObservableList<Pesce> carrello = FXCollections.observableArrayList();
    private final ObservableList<Pesce> pesci = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        // Cataologo
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colDisponibilita.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        //Carrello
        colCarrelloNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCarrelloPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colCarrelloQuantita.setCellValueFactory(new PropertyValueFactory<>("quantita"));

        tableCarrello.setItems(carrello);
        tablePesci.setItems(pesci);

        //Carico pesci dal database
        caricaPesci();
    }

    public void caricaPesci(){
        pesci.clear();
        pesci.setAll(pesceDAO.getAll());
        tablePesci.refresh();
    }

    @FXML
    private void aggiungiAlCarrello(){
        Pesce selezionato = tablePesci.getSelectionModel().getSelectedItem();
        if (selezionato == null){
            showAlert(Alert.AlertType.INFORMATION, "Selezione mancante", null,"Seleziona un pesce dal catalogo");
            return;
        }

        double q;
        try{
            q = Double.parseDouble(txtQuantita.getText());
        } catch (NumberFormatException e){
            showAlert(Alert.AlertType.ERROR, "Valore non valido", null, "Inserisci un valore numerico valido (quantità in kg)");
            return;
        }

        if (q <= 0 || q > selezionato.getQuantita()){
            showAlert(Alert.AlertType.ERROR, "Quantità non valida", null, "Inserisci una quantità valida");
            return;
        }

        //Aggiorno la quantità al catalogo
        selezionato.setQuantita(selezionato.getQuantita() - q);
        tablePesci.refresh();

        //Aggiungo al carrello
        carrello.add(new Pesce(selezionato.getId(), selezionato.getNome(), selezionato.getPrezzo(), q));
        tableCarrello.refresh();

        showAlert(Alert.AlertType.CONFIRMATION, "Successo", null,"Aggiunto al carrello con successo");
        txtQuantita.clear();
    }

    @FXML
    private void rimuoviDalCarrello(){
        Pesce selezionato = tableCarrello.getSelectionModel().getSelectedItem();
        if (selezionato == null){
            showAlert(Alert.AlertType.ERROR, "Selezione mancante",  null,"Seleziona un pesce da rimuovere dal carrello");
            return;
        }

        //Rimuovo dal carrello
        carrello.remove(selezionato);

        //Ripristino la quantità nel catalogo
        Pesce originale = pesci.stream().
                filter(p -> p.getId() == selezionato.getId()).
                findFirst()
                .orElse(null);

        if (originale != null){
            originale.setQuantita(originale.getQuantita() + selezionato.getQuantita());
            pesceDAO.restoreQuantity(originale);
            tablePesci.refresh();
        }

        tableCarrello.refresh();
        showAlert(Alert.AlertType.CONFIRMATION, "Successo", null, "Pesce rimosso dal carrello con successo");
    }

    @FXML
    private void completaOrdine(){
        carrello = tableCarrello.getItems();

        if (carrello.isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Carrello vuoto",  null,"Inserisci almeno un articolo");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double totale = 0;

        for (Pesce p : carrello){
            double subtotale = p.getQuantita() * p.getPrezzo();
            sb.append(String.format("%s - %.2f kg x %.2f € = %.2f €\n",
                    p.getNome(), p.getQuantita(), p.getPrezzo(), subtotale));
            totale += subtotale;
        }

        sb.append("\nTotale ordine: ").append(String.format("%.2f", totale));
        showAlert(Alert.AlertType.INFORMATION, "Riepilogo Ordine",
                "Controlla il tuo ordine prima di confermare", sb.toString());

        boolean acquistoRiuscito = true;
        for (Pesce p : carrello){
            boolean successo = pesceDAO.buy(p);
            if (!successo){
                acquistoRiuscito = false;
                break;
            }
        }

        if (acquistoRiuscito){
            showAlert(Alert.AlertType.CONFIRMATION, "Successo",  null,"Acquisto completato con successo");
            carrello.clear();
            caricaPesci();
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Fallito",  null,"Acquisto fallito");
        }

        tableCarrello.refresh();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
