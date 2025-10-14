package com.example.fishmanagement.controller;

import com.example.fishmanagement.model.Pesce;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClienteController {
    //Catalogo
    @FXML
    private Button btnAggiungi;
    @FXML
    private TableColumn<Pesce, Integer> colDisponibilita;
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
    private TableColumn<Pesce, Integer> colCarrelloQuantita;
    @FXML
    private Button btnRimuovi;
    @FXML
    private Button btnCompletaOrdine;

}
