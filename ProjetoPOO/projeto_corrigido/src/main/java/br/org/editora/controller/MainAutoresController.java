package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainAutoresController {

    @FXML private Button btO, btA, btV, btR, btS;
    @FXML private Button cadAut;
    @FXML private Button pesq;
    @FXML private Button aut1, e1, x1;
    @FXML private Button aut2, e2, x2;
    @FXML private Button aut3, e3, x3;
    @FXML private Button aut4, e4, x4;

    private final AutorService service = new AutorService();
    private List<Autor> autores;
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
        pesq.setOnAction(e -> App.navigateTo("TelaBuscarAutor"));
        cadAut.setOnAction(e -> {
            if (!Sessao.getInstance().isGerente()) { alerta("Apenas o gerente pode cadastrar autores."); return; }
            App.navigateTo("TelaCadastroAutor");
        });

        autores = service.listarTodos();
        renderizar();
        configurarBotoes();
        aplicarPermissoes();
    }

    private void renderizar() {
        Button[] linhas = {aut1, aut2, aut3, aut4};
        Button[] es = {e1, e2, e3, e4};
        Button[] xs = {x1, x2, x3, x4};
        int inicio = pagina * POR_PAGINA;
        for (int i = 0; i < POR_PAGINA; i++) {
            int idx = inicio + i;
            if (idx < autores.size()) {
                Autor a = autores.get(idx);
                linhas[i].setText(a.getautor().getNome() + "  " + a.getautor().getCpf());
                linhas[i].setVisible(true); es[i].setVisible(true); xs[i].setVisible(true);
            } else {
                linhas[i].setText(""); linhas[i].setVisible(false);
                es[i].setVisible(false); xs[i].setVisible(false);
            }
        }
    }

    private void configurarBotoes() {
        Button[] ls = {aut1, aut2, aut3, aut4};
        Button[] es = {e1, e2, e3, e4};
        Button[] xs = {x1, x2, x3, x4};
        for (int i = 0; i < POR_PAGINA; i++) {
            final int idx = i;
            ls[idx].setOnAction(e -> { /* detalhe */ });
            es[idx].setOnAction(e -> abrirEditar(idx));
            xs[idx].setOnAction(e -> confirmarExclusao(idx));
        }
    }

    private void aplicarPermissoes() {
        boolean gerente = Sessao.getInstance().isGerente();
        e1.setVisible(gerente); e2.setVisible(gerente); e3.setVisible(gerente); e4.setVisible(gerente);
        x1.setVisible(gerente); x2.setVisible(gerente); x3.setVisible(gerente); x4.setVisible(gerente);
        cadAut.setVisible(gerente);
    }

    private Autor getAutor(int idx) {
        int real = pagina * POR_PAGINA + idx;
        return (real < autores.size()) ? autores.get(real) : null;
    }

    private void abrirEditar(int idx) {
        Autor a = getAutor(idx);
        if (a == null) return;
        EditarAutorController.setAutorAtual(a);
        App.navigateTo("TelaEditarAutor");
    }

    private void confirmarExclusao(int idx) {
        Autor a = getAutor(idx);
        if (a == null) return;
        ExcluirAutorController.setAutorAtual(a);
        App.navigateTo("TelaExcluirAutor");
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
