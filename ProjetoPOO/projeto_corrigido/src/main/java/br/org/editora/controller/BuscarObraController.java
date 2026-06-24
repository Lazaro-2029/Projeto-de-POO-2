package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BuscarObraController {

    @FXML private TextField buscarNomeO;
    @FXML private TextField buscarAutorO;
    @FXML private TextField buscarGenO;
    @FXML private TextField buscarAnoO;
    @FXML private Button    btVoltar;
    @FXML private Button    btBuscar;

    private final ObraService service = new ObraService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btBuscar.setOnAction(e -> buscar());
        buscarGenO.setPromptText("Status");
    }

    private void buscar() {
        String titulo  = buscarNomeO.getText().trim();
        String autor   = buscarAutorO.getText().trim();
        String status  = buscarGenO.getText().trim();
        String anoStr  = buscarAnoO.getText().trim();

        List<Obra> result = null;

        if (!titulo.isBlank()) {
            result = service.buscarPorTitulo(titulo);
        } else if (!autor.isBlank()) {
            result = service.buscarPorAutorNome(autor);
        } else if (!status.isBlank()) {
            result = service.buscarPorStatus(status);
        } else if (!anoStr.isBlank()) {
            try {
                result = service.buscarPorAno(Integer.parseInt(anoStr));
            } catch (NumberFormatException e) {
                alerta("Ano inválido.");
                return;
            }
        } else {
            result = service.listarTodas();
        }

        if (result == null || result.isEmpty()) {
            alerta("Nenhuma obra encontrada.");
            return;
        }

        StringBuilder sb = new StringBuilder("Resultados (" + result.size() + "):\n\n");
        for (Obra o : result) {
            sb.append("• ").append(o.getTitulo())
              .append(" | ").append(o.getAutor().getautor().getNome())
              .append(" | ").append(o.getGenero())
              .append(" | ").append(o.getAno())
              .append(" | ").append(o.getStatus()).append("\n");
        }
        new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK).showAndWait();
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
