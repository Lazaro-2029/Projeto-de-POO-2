package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExcluirAutorController {

    @FXML private Label  nomeAutor;
    @FXML private Button btSim;
    @FXML private Button btNao;

    private static Autor autorAtual;
    private final AutorService service = new AutorService();

    public static void setAutorAtual(Autor a) { autorAtual = a; }

    @FXML
    public void initialize() {
        if (autorAtual != null) nomeAutor.setText(autorAtual.getautor().getNome());
        btNao.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btSim.setOnAction(e -> excluir());
    }

    private void excluir() {
        try {
            service.excluir(autorAtual.getautor().getCpf());
            new Alert(Alert.AlertType.INFORMATION, "Autor excluído.", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAutores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
