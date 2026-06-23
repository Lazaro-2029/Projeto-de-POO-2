package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SairController {

    @FXML private Button btSim;
    @FXML private Button btNao;

    @FXML
    public void initialize() {
        btNao.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btSim.setOnAction(e -> {
            Sessao.getInstance().encerrar();
            App.navigateTo("TelaLogin");
        });
    }
}
