package com.example.fishmanagement.controller;

import com.example.fishmanagement.database.UtenteDAO;
import com.example.fishmanagement.model.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label label;

    @FXML
    private void accedi(MouseEvent event) throws IOException {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            label.setText("Inserisci username e password");
            return;
        }

        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utente = utenteDAO.login(username, password);

        if (utente != null){
            if ("cliente".equalsIgnoreCase(utente.getRuolo())){
                apriFinestra("cliente.fxml", "Area Cliente");
            } else if ("gestore".equalsIgnoreCase(utente.getRuolo())) {
                apriFinestra("gestore.fxml", "Area Gestore");
            }
        }
        else{
            label.setText("Credenziali errate!");
        }
    }

    private void apriFinestra(String fxmlPath, String titolo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setTitle(titolo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage currentStage = (Stage) txtUsername.getScene().getWindow();
        currentStage.close();
    }
}
