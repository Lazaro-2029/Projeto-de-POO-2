package br.org.editora.controller;

import br.org.editora.App;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.service.AvaliadorService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BuscarAvaliadorController {

    @FXML private TextField buscarNomeV;
    @FXML private TextField buscarCpfV;
    @FXML private Button    btVoltar;
    @FXML private Button    btBuscar;

    private final AvaliadorService service = new AvaliadorService();

    @FXML
    public void initialize() {
        btVoltar.setOnAction(e -> App.navigateTo("TelaMainAvaliadores"));
        btBuscar.setOnAction(e -> buscar());
    }

    private void buscar() {
        String nome = buscarNomeV.getText().trim();
        String cpf  = buscarCpfV.getText().trim();

        Avaliador avaliador = null;
        if (!cpf.isBlank()) {
            avaliador = service.buscarPorCpf(cpf);
        } else if (!nome.isBlank()) {
            avaliador = service.buscarPorNome(nome);
        } else {
            List<Avaliador> todos = service.listarTodos();
            if (todos.isEmpty()) { alerta("Nenhum avaliador cadastrado."); return; }
            StringBuilder sb = new StringBuilder("Todos os avaliadores:\n\n");
            for (Avaliador a : todos) sb.append("• ").append(a.getavaliador().getNome()).append(" | ").append(a.getavaliador().getCpf()).append("\n");
            new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK).showAndWait();
            return;
        }

        if (avaliador == null) { alerta("Avaliador não encontrado."); return; }
        String info = "Nome: " + avaliador.getavaliador().getNome()
                    + "\nCPF: " + avaliador.getavaliador().getCpf()
                    + "\nEndereço: " + avaliador.getavaliador().getEndereco();
        new Alert(Alert.AlertType.INFORMATION, info, ButtonType.OK).showAndWait();
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }
}
