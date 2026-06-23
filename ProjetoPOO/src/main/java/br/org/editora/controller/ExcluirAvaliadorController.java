package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.service.AvaliadorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExcluirAvaliadorController {

    @FXML private Label  nomeAva;
    @FXML private Button btSim;
    @FXML private Button btNao;

    private static Avaliador avaliadorAtual;
    private final AvaliadorService service = new AvaliadorService();

    public static void setAvaliadorAtual(Avaliador a) { avaliadorAtual = a; }

    @FXML
    public void initialize() {
        if (avaliadorAtual != null) nomeAva.setText(avaliadorAtual.getavaliador().getNome());
        btNao.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btSim.setOnAction(e -> excluir());
    }

    private void excluir() {
        try {
            service.excluir(avaliadorAtual.getavaliador().getCpf());
            new Alert(Alert.AlertType.INFORMATION, "Avaliador excluído.", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAvaliadores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
