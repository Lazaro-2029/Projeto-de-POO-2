package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.service.RelatorioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class RelatorioController {

    @FXML private Button     btO, btA, btV, btR, btS;
    @FXML private DatePicker dataIn;
    @FXML private DatePicker dataFi;
    @FXML private Button     btGerR;
    @FXML private Button     btRelAnt;
    @FXML private Label      lblTotalObras;
    @FXML private Label      lblTotalAutores;
    @FXML private Label      lblTotalAvaliadores;
    @FXML private Label      lblStatusRes;

    private final RelatorioService service = new RelatorioService();

    @FXML
    public void initialize() {
        if (!Sessao.getInstance().isGerente()) {
            new Alert(Alert.AlertType.WARNING, "Acesso restrito ao gerente.", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
            return;
        }

        btO.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btA.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btV.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btR.setOnAction(e -> App.navigateTo("TelaRelatorio"));
        btS.setOnAction(e -> App.navigateTo("TelaSair"));
        btGerR.setOnAction(e -> gerarRelatorio());
        btRelAnt.setOnAction(e -> mostrarResumoStatus());

        carregarResumo();

        dataFi.setValue(LocalDate.now());
        dataIn.setValue(LocalDate.now().minusMonths(1));
    }

    private void carregarResumo() {
        try {
            lblTotalObras.setText("Total de obras cadastradas: " + service.totalObras());
            lblTotalAutores.setText("Total de autores: " + service.totalAutores());
            lblTotalAvaliadores.setText("Total de avaliadores: " + service.totalAvaliadores());

            List<Object[]> status = service.statusObras();
            StringBuilder sb = new StringBuilder("Por status: ");
            for (Object[] row : status) sb.append(row[0]).append("=").append(row[1]).append("  ");
            lblStatusRes.setText(sb.toString());
        } catch (Exception ex) {
            lblTotalObras.setText("Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void gerarRelatorio() {
        LocalDate inicio = dataIn.getValue();
        LocalDate fim    = dataFi.getValue();
        if (inicio == null || fim == null) {
            new Alert(Alert.AlertType.WARNING, "Informe as datas inicial e final.", ButtonType.OK).showAndWait();
            return;
        }
        ResultadoRelatorioController.setPeriodo(inicio, fim);
        App.navigateTo("TelaResultadoRelatorio");
    }

    private void mostrarResumoStatus() {
        try {
            List<Object[]> porAutor = service.obrasPorAutor();
            StringBuilder sb = new StringBuilder("Obras por autor:\n\n");
            for (Object[] row : porAutor) {
                sb.append("• ").append(row[0]).append(": ").append(row[1]).append(" obras\n");
            }
            new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK).showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
