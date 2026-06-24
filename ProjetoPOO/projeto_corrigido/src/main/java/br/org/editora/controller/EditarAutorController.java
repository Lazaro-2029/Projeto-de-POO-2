package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditarAutorController {

    @FXML private TextField     edNomeA;
    @FXML private TextField     edEndA;
    @FXML private TextField     edCpfA;
    @FXML private Button        btVoltar;
    @FXML private Button        btEd;

    private static Autor autorAtual;
    private final AutorService service = new AutorService();

    public static void setAutorAtual(Autor a) { autorAtual = a; }

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btEd.setOnAction(e -> salvar());

        if (autorAtual != null) {
            edNomeA.setText(autorAtual.getautor().getNome());
            edEndA.setText(autorAtual.getautor().getEndereco());
            edCpfA.setText(autorAtual.getautor().getCpf());
            edCpfA.setEditable(false);
        }
    }

    private void salvar() {
        String nome = edNomeA.getText().trim();
        String end  = edEndA.getText().trim();
        String cpf  = edCpfA.getText().trim();
        try {
            service.atualizar(cpf, nome, end);
            new Alert(Alert.AlertType.INFORMATION, "Autor atualizado!", ButtonType.OK).showAndWait();
            App.navigateTo("TelaMainAutores");
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Erro: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }
}
