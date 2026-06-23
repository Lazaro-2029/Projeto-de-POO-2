package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.service.AvaliadorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditarAvaliadorController {

    @FXML private TextField edNomeV;
    @FXML private TextField edEndV;
    @FXML private TextField edCpfV;
    @FXML private Button    btVoltar;
    @FXML private Button    btEditar;

    private static Avaliador avaliadorAtual;
    private final AvaliadorService service = new AvaliadorService();

    public static void setAvaliadorAtual(Avaliador a) { avaliadorAtual = a; }

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btEditar.setOnAction(e -> salvar());

        if (avaliadorAtual != null) {
            edNomeV.setText(avaliadorAtual.getavaliador().getNome());
            edEndV.setText(avaliadorAtual.getavaliador().getEndereco());
            edCpfV.setText(avaliadorAtual.getavaliador().getCpf());
            edCpfV.setEditable(false);
        }
    }

    private void salvar() {
        String nome = edNomeV.getText().trim();
        String end  = edEndV.getText().trim();
        String cpf  = edCpfV.getText().trim();
        try {
            service.atualizar(cpf, nome, end);
            new Alert(Alert.AlertType.INFORMATION, "Avaliador atualizado!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAvaliadores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
