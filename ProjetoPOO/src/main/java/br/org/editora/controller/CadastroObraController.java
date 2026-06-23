package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroObraController {

    @FXML private TextField cadNomeO;
    @FXML private TextField cadAutorO;
    @FXML private TextField cadGenO;
    @FXML private TextField cadAnoO;
    @FXML private Button    btVoltar;
    @FXML private Button    btCadastrar;

    private final ObraService service = new ObraService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btCadastrar.setOnAction(e -> cadastrar());

        if (Sessao.getInstance().isAutor()) {
            cadAutorO.setText(Sessao.getInstance().getCpfUsuario());
            cadAutorO.setEditable(false);
        }
    }

    private void cadastrar() {
        String titulo = cadNomeO.getText().trim();
        String cpfAutor = cadAutorO.getText().trim();
        String genero   = cadGenO.getText().trim();
        String anoStr   = cadAnoO.getText().trim();

        if (anoStr.isBlank()) { alerta("Informe o ano."); return; }
        int ano;
        try { ano = Integer.parseInt(anoStr); } catch (NumberFormatException ex) { alerta("Ano inválido."); return; }

        try {
            int id = service.cadastrar(titulo, genero, ano, cpfAutor);
            new Alert(Alert.AlertType.INFORMATION, "Obra cadastrada! ID: " + id, ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
        } catch (Exception ex) {
            alerta("Erro: " + ex.getMessage());
        }
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
