package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroUsuarioController {

    @FXML private TextField     cadNome;
    @FXML private TextField     cadEnd;
    @FXML private TextField     cadCpf;
    @FXML private Button        btVoltar;
    @FXML private Button        btCadastrar;

    private final AutorService service = new AutorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaLogin"));
        btCadastrar.setOnAction(e -> cadastrar());
    }

    private void cadastrar() {
        String nome = cadNome.getText().trim();
        String end  = cadEnd.getText().trim();
        String cpf  = cadCpf.getText().trim();
        try {
            service.cadastrar(cpf, nome, end);
            new Alert(Alert.AlertType.INFORMATION,
                    "Cadastro realizado!\nFaça login com seu CPF.",
                    ButtonType.OK).showAndWait();
            App.navigateTo("TelaLogin");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
