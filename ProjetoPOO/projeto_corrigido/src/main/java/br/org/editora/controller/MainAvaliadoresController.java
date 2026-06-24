package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.service.AvaliadorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainAvaliadoresController {

    @FXML private Button btO, btA, btV, btR, btS;
    @FXML private Button cadV;
    @FXML private Button pesq;
    @FXML private Button v1, e1, x1;
    @FXML private Button v2, e2, x2;
    @FXML private Button v3, e3, x3;
    @FXML private Button v4, e4, x4;

    private final AvaliadorService service = new AvaliadorService();
    private List<Avaliador> avaliadores;
    private int pagina = 0;
    private static final int POR_PAGINA = 4;

    @FXML
    public void initialize() {
        btO.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btA.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btV.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btR.setOnAction(e -> {
            if (Sessao.getInstance().isGerente()) App.navigateTo("TelaRelatorio");
            else alerta("Apenas o gerente acessa relatórios.");
        });
        btS.setOnAction(e -> App.navigateTo("TelaSair"));
        pesq.setOnAction(e -> App.navigateTo("TelaBuscarAvaliador"));
        cadV.setOnAction(e -> {
            if (!Sessao.getInstance().isGerente()) { alerta("Apenas o gerente pode cadastrar avaliadores."); return; }
            App.navigateTo("TelaCadastroAvaliador");
        });

        avaliadores = service.listarTodos();
        renderizar();
        configurarBotoes();
        aplicarPermissoes();
    }

    private void renderizar() {
        Button[] ls = {v1, v2, v3, v4};
        Button[] es = {e1, e2, e3, e4};
        Button[] xs = {x1, x2, x3, x4};
        int inicio = pagina * POR_PAGINA;
        for (int i = 0; i < POR_PAGINA; i++) {
            int idx = inicio + i;
            if (idx < avaliadores.size()) {
                Avaliador a = avaliadores.get(idx);
                ls[i].setText(a.getavaliador().getNome() + "  " + a.getavaliador().getCpf());
                ls[i].setVisible(true); es[i].setVisible(true); xs[i].setVisible(true);
            } else {
                ls[i].setText(""); ls[i].setVisible(false);
                es[i].setVisible(false); xs[i].setVisible(false);
            }
        }
    }

    private void configurarBotoes() {
        Button[] ls = {v1, v2, v3, v4};
        Button[] es = {e1, e2, e3, e4};
        Button[] xs = {x1, x2, x3, x4};
        for (int i = 0; i < POR_PAGINA; i++) {
            final int idx = i;
            ls[idx].setOnAction(e -> { /* detalhe futuro */ });
            es[idx].setOnAction(e -> abrirEditar(idx));
            xs[idx].setOnAction(e -> confirmarExclusao(idx));
        }
    }

    private void aplicarPermissoes() {
        boolean gerente = Sessao.getInstance().isGerente();
        e1.setVisible(gerente); e2.setVisible(gerente); e3.setVisible(gerente); e4.setVisible(gerente);
        x1.setVisible(gerente); x2.setVisible(gerente); x3.setVisible(gerente); x4.setVisible(gerente);
        cadV.setVisible(gerente);
    }

    private Avaliador getAvaliador(int idx) {
        int real = pagina * POR_PAGINA + idx;
        return (real < avaliadores.size()) ? avaliadores.get(real) : null;
    }

    private void abrirEditar(int idx) {
        Avaliador a = getAvaliador(idx);
        if (a == null) return;
        EditarAvaliadorController.setAvaliadorAtual(a);
        App.navigateTo("TelaEditarAvaliador");
    }

    private void confirmarExclusao(int idx) {
        Avaliador a = getAvaliador(idx);
        if (a == null) return;
        ExcluirAvaliadorController.setAvaliadorAtual(a);
        App.navigateTo("TelaExcluirAvaliador");
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
