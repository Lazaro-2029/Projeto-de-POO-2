package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField     login;
    @FXML private PasswordField senha;
    @FXML private Button        btAcessar;
    @FXML private Button        btCadastro;

    private final UsuarioService service = new UsuarioService();

    @FXML
    public void initialize() {
        btAcessar.setOnAction(e -> acessar());
        btCadastro.setOnAction(e -> App.navigateTo("TelaCadastroUsuario"));
    }

    private void acessar() {
        String cpf = login.getText().trim();
        if (cpf.isBlank()) {
            alerta("Informe o CPF para acessar.");
            return;
        }
        try {
            String[] resultado = service.autenticar(cpf);
            String nome = resultado[0];
            String tipo = resultado[1];
            Sessao.getInstance().iniciar(cpf, nome, tipo);

            switch (tipo) {
                case "GERENTE"   -> App.navigateTo("TelaMainObras");
                case "AUTOR"     -> App.navigateTo("TelaMainObras");
                case "AVALIADOR" -> App.navigateTo("TelaMainObras");
                default          -> alerta("Perfil desconhecido.");
            }
        } catch (Exception ex) {
            alerta("Erro ao fazer login: " + ex.getMessage());
        }
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
