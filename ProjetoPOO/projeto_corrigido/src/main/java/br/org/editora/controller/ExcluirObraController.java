package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExcluirObraController {

    @FXML private Label  nomeObra;
    @FXML private Button btSim;
    @FXML private Button btNao;

    private static Obra obraAtual;
    private final ObraService service = new ObraService();

    public static void setObraAtual(Obra o) { obraAtual = o; }

    @FXML
    public void initialize() {
        if (obraAtual != null) nomeObra.setText(obraAtual.getTitulo());
        btNao.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btSim.setOnAction(e -> excluir());
    }

    private void excluir() {
        if (obraAtual == null) return;
        if (!"PENDENTE".equals(obraAtual.getStatus())) {
            new Alert(Alert.AlertType.WARNING, "Só é possível excluir obras pendentes.", ButtonType.OK).showAndWait();
            return;
        }
        try {
            service.excluir(obraAtual.getIdBanco());
            new Alert(Alert.AlertType.INFORMATION, "Obra excluída.", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
