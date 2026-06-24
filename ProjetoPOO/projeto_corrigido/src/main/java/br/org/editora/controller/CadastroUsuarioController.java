package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroUsuarioController {

    @FXML private TextField     cadNome;
    @FXML private TextField     cadEnd;
    @FXML private TextField     cadCpf;
    @FXML private PasswordField cadSenha;        // NOVO
    @FXML private PasswordField cadSenhaConfirm; // NOVO
    @FXML private Button        btVoltar;
    @FXML private Button        btCadastrar;

    private final AutorService service = new AutorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaLogin"));
        btCadastrar.setOnAction(e -> cadastrar());
    }

    private void cadastrar() {
        String nome    = cadNome.getText().trim();
        String end     = cadEnd.getText().trim();
        String cpf     = cadCpf.getText().trim();
        String senha   = cadSenha.getText();
        String confirm = cadSenhaConfirm.getText();

        if (!senha.equals(confirm)) {
            new Alert(Alert.AlertType.WARNING, "As senhas não coincidem.", ButtonType.OK).showAndWait();
            return;
        }

        try {
            service.cadastrar(cpf, nome, end, senha);
            new Alert(Alert.AlertType.INFORMATION,
                    "Cadastro realizado!\nFaça login com seu CPF e senha.",
                    ButtonType.OK).showAndWait();
            App.navigateTo("TelaLogin");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
