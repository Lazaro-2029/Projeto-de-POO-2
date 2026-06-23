package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.AvaliadorService;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditarObraController {

    @FXML private TextField edNomeO;
    @FXML private TextField edAutorO;
    @FXML private TextField edGenO;
    @FXML private TextField edAnoO;
    @FXML private TextField edAvaO;
    @FXML private TextField edStaO;
    @FXML private Button    btVoltar;
    @FXML private Button    btEd;

    private static Obra obraAtual;
    private final ObraService service = new ObraService();
    private final AvaliadorService avalService = new AvaliadorService();

    public static void setObraAtual(Obra o) { obraAtual = o; }

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btEd.setOnAction(e -> salvar());

        if (obraAtual != null) {
            edNomeO.setText(obraAtual.getTitulo());
            edAutorO.setText(obraAtual.getAutor().getautor().getNome());
            edAutorO.setEditable(false);
            edGenO.setText(obraAtual.getGenero());
            edAnoO.setText(String.valueOf(obraAtual.getAno()));
            edStaO.setText(obraAtual.getStatus());
            edStaO.setEditable(false);

            if (obraAtual.getAvaliadorResponsavel() != null) {
                edAvaO.setText(obraAtual.getAvaliadorResponsavel().getavaliador().getCpf());
            }

            if (!Sessao.getInstance().isGerente()) {
                edAvaO.setEditable(false);
                if (!"PENDENTE".equals(obraAtual.getStatus())) {
                    edNomeO.setEditable(false);
                    edGenO.setEditable(false);
                    edAnoO.setEditable(false);
                    btEd.setDisable(true);
                }
            }
        }
    }

    private void salvar() {
        String titulo = edNomeO.getText().trim();
        String genero = edGenO.getText().trim();
        String anoStr = edAnoO.getText().trim();
        String cpfAva = edAvaO.getText().trim();
        int ano;
        try { ano = Integer.parseInt(anoStr); } catch (NumberFormatException ex) { alerta("Ano inválido."); return; }
        try {
            service.atualizar(obraAtual.getIdBanco(), titulo, genero, ano);

            if (Sessao.getInstance().isGerente() && !cpfAva.isBlank()) {
                service.atribuirAvaliador(obraAtual.getIdBanco(), cpfAva);
            }
            new Alert(Alert.AlertType.INFORMATION, "Obra atualizada com sucesso!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
        } catch (Exception ex) {
            alerta("Erro: " + ex.getMessage());
        }
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
