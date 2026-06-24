package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.service.AvaliadorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroAvaliadorController {

    @FXML private TextField     cadNomeV;
    @FXML private TextField     cadEndV;
    @FXML private TextField     cadCpfV;
    @FXML private PasswordField cadSenhaV;
    @FXML private PasswordField cadSenhaConfirmV;
    @FXML private Button        btVoltar;
    @FXML private Button        btCadastrar;

    private final AvaliadorService service = new AvaliadorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btCadastrar.setOnAction(e -> cadastrar());
    }

    private void cadastrar() {
        String nome    = cadNomeV.getText().trim();
        String end     = cadEndV.getText().trim();
        String cpf     = cadCpfV.getText().trim();
        String senha   = cadSenhaV.getText();
        String confirm = cadSenhaConfirmV.getText();

        if (senha.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Informe uma senha.", ButtonType.OK).showAndWait();
            return;
        }
        if (!senha.equals(confirm)) {
            new Alert(Alert.AlertType.WARNING, "As senhas não coincidem.", ButtonType.OK).showAndWait();
            return;
        }
        if (senha.length() < 6) {
            new Alert(Alert.AlertType.WARNING, "A senha deve ter pelo menos 6 caracteres.", ButtonType.OK).showAndWait();
            return;
        }

        try {
            service.cadastrar(cpf, nome, end, senha);
            new Alert(Alert.AlertType.INFORMATION, "Avaliador cadastrado com sucesso!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAvaliadores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
