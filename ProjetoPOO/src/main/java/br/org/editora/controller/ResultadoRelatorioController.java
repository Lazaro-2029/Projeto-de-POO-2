package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.RelatorioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class ResultadoRelatorioController {

    @FXML private Label    lblPeriodo;
    @FXML private TextArea areaResultado;
    @FXML private Label    lblTotal;
    @FXML private Button   btVoltar;

    private static LocalDate periodoInicio;
    private static LocalDate periodoFim;
    private final RelatorioService service = new RelatorioService();

    public static void setPeriodo(LocalDate inicio, LocalDate fim) {
        periodoInicio = inicio;
        periodoFim    = fim;
    }

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaRelatorio"));

        if (periodoInicio == null || periodoFim == null) {
            lblPeriodo.setText("Período não definido");
            return;
        }

        lblPeriodo.setText("Período: " + periodoInicio + " a " + periodoFim);

        try {
            List<Obra> obras = service.obrasAvaliadasNoPeriodo(periodoInicio, periodoFim);

            if (obras.isEmpty()) {
                areaResultado.setText("Nenhuma obra avaliada nesse período.");
                lblTotal.setText("Total: 0 obras");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-30s %-15s %-20s %-12s%n",
                    "TÍTULO", "STATUS", "AVALIADOR", "DATA AVAL."));
            sb.append("-".repeat(82)).append("\n");

            for (Obra o : obras) {
                String nomeAva = (o.getAvaliadorResponsavel() != null)
                        ? o.getAvaliadorResponsavel().getavaliador().getNome() : "—";
                String dataAva = (o.getDataAvaliacao() != null)
                        ? o.getDataAvaliacao().toString() : "—";

                sb.append(String.format("%-30s %-15s %-20s %-12s%n",
                        truncar(o.getTitulo(), 29),
                        o.getStatus(),
                        truncar(nomeAva, 19),
                        dataAva));
            }

            areaResultado.setText(sb.toString());
            lblTotal.setText("Total de obras avaliadas no período: " + obras.size());

        } catch (Exception ex) {
            areaResultado.setText("Erro ao gerar relatório: " + ex.getMessage());
        }
    }

    private String truncar(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max) : s;
    }
}
