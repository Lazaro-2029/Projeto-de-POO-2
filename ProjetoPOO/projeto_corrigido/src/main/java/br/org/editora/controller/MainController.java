package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;

public class MainController {

    @FXML private Button btO;
    @FXML private Button btA;
    @FXML private Button btV;
    @FXML private Button btR;
    @FXML private Button btS;
    @FXML private Button cadO;
    @FXML private Button pesq;

    @FXML private Button o1, e1, x1;
    @FXML private Button o2, e2, x2;
    @FXML private Button o3, e3, x3;
    @FXML private Button o4, e4, x4;

    private final ObraService service = new ObraService();
    private List<Obra> obras;
    private int pagina = 0;
    private static final int POR_PAGINA = 4;

    @FXML
    public void initialize() {
        configurarMenu();
        carregarObras(null);
        configurarBotoesCRUD();
        aplicarPermissoes();
        pesq.setOnAction(e -> App.navigateTo("TelaBuscarObra"));
    }

    private void configurarMenu() {
        btO.setOnAction(e -> App.navigateTo("TelaMainObras"));
        btA.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btV.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btR.setOnAction(e -> {
            if (Sessao.getInstance().isGerente()) App.navigateTo("TelaRelatorio");
            else alerta("Apenas o gerente pode acessar relatórios.");
        });
        btS.setOnAction(e -> App.navigateTo("TelaSair"));
    }

    private void carregarObras(String filtro) {
        Sessao sessao = Sessao.getInstance();
        if (sessao.isAutor()) {
            obras = service.buscarPorAutorCpf(sessao.getCpfUsuario());
        } else if (sessao.isAvaliador()) {
            obras = service.buscarPorAvaliadorCpf(sessao.getCpfUsuario());
        } else {
            obras = service.listarTodas();
        }
        renderizarPagina();
    }

    private void renderizarPagina() {
        Button[] obBtns = {o1, o2, o3, o4};
        Button[] eBtns  = {e1, e2, e3, e4};
        Button[] xBtns  = {x1, x2, x3, x4};

        int inicio = pagina * POR_PAGINA;
        for (int i = 0; i < POR_PAGINA; i++) {
            int idx = inicio + i;
            if (idx < obras.size()) {
                Obra ob = obras.get(idx);
                obBtns[i].setText(ob.getTitulo() + "  [" + ob.getStatus() + "]");
                obBtns[i].setVisible(true);
                eBtns[i].setVisible(true);
                xBtns[i].setVisible(true);
            } else {
                obBtns[i].setText("");
                obBtns[i].setVisible(false);
                eBtns[i].setVisible(false);
                xBtns[i].setVisible(false);
            }
        }
    }

    private void configurarBotoesCRUD() {
        Button[] obBtns = {o1, o2, o3, o4};
        Button[] eBtns  = {e1, e2, e3, e4};
        Button[] xBtns  = {x1, x2, x3, x4};

        for (int i = 0; i < POR_PAGINA; i++) {
            final int idx = i;
            obBtns[i].setOnAction(e -> abrirDetalhe(idx));
            eBtns[i].setOnAction(e  -> abrirEditar(idx));
            xBtns[i].setOnAction(e  -> confirmarExclusao(idx));
        }

        cadO.setOnAction(e -> App.navigateTo("TelaCadastroObra"));
    }

    private void aplicarPermissoes() {
        Sessao sessao = Sessao.getInstance();
        if (!sessao.isGerente() && !sessao.isAutor()) {
            cadO.setVisible(false);
        }

        boolean podeCRUD = sessao.isGerente() || sessao.isAutor();
        e1.setVisible(podeCRUD); e2.setVisible(podeCRUD);
        e3.setVisible(podeCRUD); e4.setVisible(podeCRUD);
        x1.setVisible(podeCRUD); x2.setVisible(podeCRUD);
        x3.setVisible(podeCRUD); x4.setVisible(podeCRUD);
    }

    private Obra getObra(int idx) {
        int real = pagina * POR_PAGINA + idx;
        if (real < obras.size()) return obras.get(real);
        return null;
    }

    private void abrirDetalhe(int idx) {
        Obra ob = getObra(idx);
        if (ob == null) return;
        if (Sessao.getInstance().isAvaliador()) {
            DetalheObraController.setObraAtual(ob);
            App.navigateTo("TelaDetalheObra");
        } else {
            DetalheObraController.setObraAtual(ob);
            App.navigateTo("TelaDetalheObra");
        }
    }

    private void abrirEditar(int idx) {
        Obra ob = getObra(idx);
        if (ob == null) return;
        EditarObraController.setObraAtual(ob);
        App.navigateTo("TelaEditarObra");
    }

    private void confirmarExclusao(int idx) {
        Obra ob = getObra(idx);
        if (ob == null) return;
        ExcluirObraController.setObraAtual(ob);
        App.navigateTo("TelaExcluirObra");
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
