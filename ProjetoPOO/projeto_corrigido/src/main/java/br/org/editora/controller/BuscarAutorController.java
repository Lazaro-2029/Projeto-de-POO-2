package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.service.AutorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BuscarAutorController {

    @FXML private TextField buscarNomeA;
    @FXML private TextField buscarCpfA;
    @FXML private Button    btVoltar;
    @FXML private Button    btBuscar;

    private final AutorService service = new AutorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAutores"));
        btBuscar.setOnAction(e -> buscar());
    }

    private void buscar() {
        String nome = buscarNomeA.getText().trim();
        String cpf  = buscarCpfA.getText().trim();

        Autor autor = null;
        if (!cpf.isBlank()) {
            autor = service.buscarPorCpf(cpf);
        } else if (!nome.isBlank()) {
            autor = service.buscarPorNome(nome);
        } else {
            List<Autor> todos = service.listarTodos();
            if (todos.isEmpty()) { alerta("Nenhum autor cadastrado."); return; }
            StringBuilder sb = new StringBuilder("Todos os autores:\n\n");
            for (Autor a : todos) sb.append("• ").append(a.getautor().getNome()).append(" | ").append(a.getautor().getCpf()).append("\n");
            new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK).showAndWait();
            return;
        }

        if (autor == null) { alerta("Autor não encontrado."); return; }
        String info = "Nome: " + autor.getautor().getNome()
                    + "\nCPF: " + autor.getautor().getCpf()
                    + "\nEndereço: " + autor.getautor().getEndereco();
        new Alert(Alert.AlertType.INFORMATION, info, ButtonType.OK).showAndWait();
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
