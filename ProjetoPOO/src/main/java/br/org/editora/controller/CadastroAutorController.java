package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroAutorController {

    @FXML private TextField     cadNomeA;
    @FXML private TextField     cadEndA;
    @FXML private TextField     cadCpfA;
    @FXML private Button        btVoltar;
    @FXML private Button        btCadastrar;

    private final AutorService service = new AutorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btCadastrar.setOnAction(e -> cadastrar());
    }

    private void cadastrar() {
        String nome  = cadNomeA.getText().trim();
        String end   = cadEndA.getText().trim();
        String cpf   = cadCpfA.getText().trim();
        try {
            service.cadastrar(cpf, nome, end);
            new Alert(Alert.AlertType.INFORMATION, "Autor cadastrado com sucesso!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAutores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
