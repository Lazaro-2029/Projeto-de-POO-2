package br.org.editora.model.factory;

import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Usuario;

public class UsuarioFactory {
    public static Autor criarAutor(Usuario usuario) {
        return new Autor(usuario);
    }

    public static Avaliador criarAvaliador(Usuario usuario) {
        return new Avaliador(usuario);
    }
}
