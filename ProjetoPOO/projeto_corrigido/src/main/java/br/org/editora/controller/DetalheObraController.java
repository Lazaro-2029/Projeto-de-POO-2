package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.Sessao;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.service.ObraService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DetalheObraController {

    @FXML private Label  lblTitulo;
    @FXML private Label  lblAutor;
    @FXML private Label  lblGenero;
    @FXML private Label  lblAno;
    @FXML private Label  lblStatus;
    @FXML private Label  lblAvaliador;
    @FXML private Label  lblDataAval;
    @FXML private Button btAprovar;
    @FXML private Button btReprovar;
    @FXML private Button btAtribuir;
    @FXML private TextField tfCpfAvaliador;
    @FXML private Button btVoltar;

    private static Obra obraAtual;
    private final ObraService service = new ObraService();

    public static void setObraAtual(Obra o) { obraAtual = o; }

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainObras"));

        if (obraAtual != null) {
            lblTitulo.setText(obraAtual.getTitulo());
            lblAutor.setText(obraAtual.getAutor().getautor().getNome());
            lblGenero.setText(obraAtual.getGenero());
            lblAno.setText(String.valueOf(obraAtual.getAno()));
            lblStatus.setText(obraAtual.getStatus());
            lblAvaliador.setText(obraAtual.getAvaliadorResponsavel() != null
                    ? obraAtual.getAvaliadorResponsavel().getavaliador().getNome() : "Não definido");
            lblDataAval.setText(obraAtual.getDataAvaliacao() != null
                    ? obraAtual.getDataAvaliacao().toString() : "-");
        }

        Sessao sessao = Sessao.getInstance();
        boolean podeAvaliar = sessao.isAvaliador() && "PENDENTE".equals(obraAtual != null ? obraAtual.getStatus() : "");
        btAprovar.setVisible(podeAvaliar);
        btReprovar.setVisible(podeAvaliar);

        boolean podeAtribuir = sessao.isGerente() && "PENDENTE".equals(obraAtual != null ? obraAtual.getStatus() : "");
        btAtribuir.setVisible(podeAtribuir);
        tfCpfAvaliador.setVisible(podeAtribuir);

        btAprovar.setOnAction(e -> avaliar(true));
        btReprovar.setOnAction(e -> avaliar(false));
        btAtribuir.setOnAction(e -> atribuirAvaliador());
    }

    private void avaliar(boolean aprovar) {
        try {
            service.avaliar(obraAtual.getIdBanco(), Sessao.getInstance().getCpfUsuario(), aprovar);
            String msg = aprovar ? "Obra APROVADA!" : "Obra REPROVADA!";
            new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private void atribuirAvaliador() {
        String cpf = tfCpfAvaliador.getText().trim();
        if (cpf.isBlank()) { new Alert(Alert.AlertType.WARNING, "Informe o CPF do avaliador.", ButtonType.OK).showAndWait(); return; }
        try {
            service.atribuirAvaliador(obraAtual.getIdBanco(), cpf);
            new Alert(Alert.AlertType.INFORMATION, "Avaliador atribuído!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainObras");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
