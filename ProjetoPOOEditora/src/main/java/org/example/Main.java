package org.example;
import br.org.editora.exceptions.*;
import br.org.editora.model.DAO.*;
import br.org.editora.model.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class Main {
    static void titulo(String msg) {
        System.out.println("\n========================================");
        System.out.println("  " + msg);
        System.out.println("========================================");
    }

    static void ok(String msg)   { System.out.println("  [OK] " + msg); }
    static void info(String msg) { System.out.println("  --> " + msg); }

    public static void main(String[] args) {

        try {

            titulo("TESTE 1 – UsuarioDAO");

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            // inserir
            Usuario u1 = new Usuario("111.111.111-11", "", "");
            try { u1.setNome("Carlos Silva");        } catch (SemNomeException e)     { throw new RuntimeException(e); }
            try { u1.setEndereco("Rua das Flores, 10"); } catch (SemEnderecoException e) { throw new RuntimeException(e); }
            usuarioDAO.inserir(u1);
            ok("Inserido usuario: " + u1.getNome() + " | CPF: " + u1.getCpf());

            Usuario u2 = new Usuario("222.222.222-22", "", "");
            try { u2.setNome("Ana Souza");           } catch (SemNomeException e)     { throw new RuntimeException(e); }
            try { u2.setEndereco("Av. Brasil, 200");  } catch (SemEnderecoException e) { throw new RuntimeException(e); }
            usuarioDAO.inserir(u2);
            ok("Inserido usuario: " + u2.getNome() + " | CPF: " + u2.getCpf());

            Usuario u3 = new Usuario("333.333.333-33", "", "");
            try { u3.setNome("Pedro Lima");          } catch (SemNomeException e)     { throw new RuntimeException(e); }
            try { u3.setEndereco("Rua XV, 5");        } catch (SemEnderecoException e) { throw new RuntimeException(e); }
            usuarioDAO.inserir(u3);
            ok("Inserido usuario: " + u3.getNome() + " | CPF: " + u3.getCpf());

            // buscar por CPF
            Usuario encontrado = usuarioDAO.buscarPorCpf("111.111.111-11");
            if (encontrado != null)
                ok("buscarPorCpf: " + encontrado.getNome() + " | Endereço: " + encontrado.getEndereco());
            else
                System.out.println("  [FALHOU] buscarPorCpf não encontrou o usuario");

            // buscar por nome
            Usuario porNome = usuarioDAO.buscarPorNome("Ana Souza");
            if (porNome != null)
                ok("buscarPorNome: " + porNome.getCpf());
            else
                System.out.println("  [FALHOU] buscarPorNome");

            // listar todos
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            ok("listarTodos: " + usuarios.size() + " usuario(s) encontrado(s)");

            // atualizar nome e endereço
            usuarioDAO.atualizarNome("111.111.111-11", "Carlos R. Silva");
            ok("atualizarNome: OK");

            usuarioDAO.atualizarEndereco("111.111.111-11", "Rua Nova, 99");
            ok("atualizarEndereco: OK");

            // confirmar atualização
            Usuario atualizado = usuarioDAO.buscarPorCpf("111.111.111-11");
            if (atualizado != null)
                ok("confirmação pós-update: " + atualizado.getNome() + " | " + atualizado.getEndereco());

            // ==================================================================
            // 2. AUTOR DAO
            // ==================================================================
            titulo("TESTE 2 – AutorDAO");

            AutorDAO autorDAO = new AutorDAO();

            // inserir autores (u1 e u2 já estão na tabela usuario)
            Autor autor1 = new Autor(u1);
            autorDAO.inserir(autor1);
            ok("Inserido autor: " + u1.getNome());

            Autor autor2 = new Autor(u2);
            autorDAO.inserir(autor2);
            ok("Inserido autor: " + u2.getNome());

            // buscar por CPF
            Autor buscadoAutor = autorDAO.buscarPorCpf("111.111.111-11");
            if (buscadoAutor != null)
                ok("buscarPorCpf autor: " + buscadoAutor.getautor().getCpf());
            else
                System.out.println("  [FALHOU] buscarPorCpf autor");

            // buscar por nome (usa o nome gravado no banco)
            Autor buscadoPorNome = autorDAO.buscarPorNome("Carlos R. Silva");
            if (buscadoPorNome != null)
                ok("buscarPorNome autor encontrado");
            else
                System.out.println("  [FALHOU] buscarPorNome autor");

            // listar todos
            List<Autor> autores = autorDAO.listarTodos();
            ok("listarTodos autores: " + autores.size() + " autor(es)");

            // ==================================================================
            // 3. AVALIADOR DAO
            // ==================================================================
            titulo("TESTE 3 – AvaliadorDAO");

            AvaliadorDAO avaliadorDAO = new AvaliadorDAO();

            // u3 vira avaliador
            Avaliador avaliador1 = new Avaliador(u3);
            avaliadorDAO.inserir(avaliador1);
            ok("Inserido avaliador: " + u3.getNome());

            // buscar por CPF
            Avaliador buscadoAval = avaliadorDAO.buscarPorCpf("333.333.333-33");
            if (buscadoAval != null)
                ok("buscarPorCpf avaliador: " + buscadoAval.getavaliador().getCpf());
            else
                System.out.println("  [FALHOU] buscarPorCpf avaliador");

            // buscar por nome
            Avaliador avalPorNome = avaliadorDAO.buscarPorNome("Pedro Lima");
            if (avalPorNome != null)
                ok("buscarPorNome avaliador encontrado");
            else
                System.out.println("  [FALHOU] buscarPorNome avaliador");

            // listar todos
            List<Avaliador> avaliadores = avaliadorDAO.listarTodos();
            ok("listarTodos avaliadores: " + avaliadores.size() + " avaliador(es)");

            // ==================================================================
            // 4. GERENTE DAO
            // ==================================================================
            titulo("TESTE 4 – GerenteDAO");

            GerenteDAO gerenteDAO = new GerenteDAO();

            // precisamos de um novo usuario para o gerente
            Usuario u4 = new Usuario("444.444.444-44", "", "");
            try { u4.setNome("Lucia Mendes");        } catch (SemNomeException e)     { throw new RuntimeException(e); }
            try { u4.setEndereco("Alameda Santos, 1"); } catch (SemEnderecoException e) { throw new RuntimeException(e); }
            usuarioDAO.inserir(u4);

            Gerente gerente1 = new Gerente(u4);
            gerenteDAO.inserir(gerente1);
            ok("Inserido gerente: " + u4.getNome());

            // buscar por CPF
            Gerente buscadoGer = gerenteDAO.buscarPorCpf("444.444.444-44");
            if (buscadoGer != null)
                ok("buscarPorCpf gerente: " + buscadoGer.getGerente().getCpf());
            else
                System.out.println("  [FALHOU] buscarPorCpf gerente");

            // buscar por nome
            Gerente gerPorNome = gerenteDAO.buscarPorNome("Lucia Mendes");
            if (gerPorNome != null)
                ok("buscarPorNome gerente encontrado");
            else
                System.out.println("  [FALHOU] buscarPorNome gerente");

            // listar todos
            List<Gerente> gerentes = gerenteDAO.listarTodos();
            ok("listarTodos gerentes: " + gerentes.size() + " gerente(s)");

            // ==================================================================
            // 5. OBRA DAO
            // ==================================================================
            titulo("TESTE 5 – ObraDAO");

            ObraDAO obraDAO = new ObraDAO();

            // inserir obras vinculadas ao autor1 (u1)
            Obra obra1 = new Obra("Dom Casmurro",   "Romance",   1899, autor1);
            Obra obra2 = new Obra("O Alienista",    "Conto",     1882, autor1);
            Obra obra3 = new Obra("Capitães da Areia", "Romance", 1937, autor2);

            obraDAO.inserir(obra1);
            ok("Inserida obra: " + obra1.getTitulo() + " | status: " + obra1.getStatus());

            obraDAO.inserir(obra2);
            ok("Inserida obra: " + obra2.getTitulo());

            obraDAO.inserir(obra3);
            ok("Inserida obra: " + obra3.getTitulo());

            // buscar por título
            List<Obra> porTitulo = obraDAO.buscarPorTitulo("O Alienista");
            ok("buscarPorTitulo 'O Alienista': " + porTitulo.size() + " resultado(s)");

            // buscar por status PENDENTE
            List<Obra> pendentes = obraDAO.buscarPorStatus("PENDENTE");
            ok("buscarPorStatus PENDENTE: " + pendentes.size() + " obra(s)");

            // buscar por autor (CPF do u1)
            List<Obra> obrasDoAutor = obraDAO.buscarPorAutor("111.111.111-11");
            ok("buscarPorAutor (u1): " + obrasDoAutor.size() + " obra(s)");

            // listar todas
            List<Obra> todasObras = obraDAO.listarTodas();
            ok("listarTodas obras: " + todasObras.size() + " obra(s)");

            // atribuir avaliador à obra1 (buscamos o id real pelo título)
            List<Obra> obra1Lista = obraDAO.buscarPorTitulo("Dom Casmurro");
            if (!obra1Lista.isEmpty()) {
                // o id real está no banco; como a entidade não expõe o id do banco,
                // fazemos a atualização usando o título + autor como identificador indireto.
                // Na prática o sistema real precisaria de um id vindo do banco.
                // Aqui buscamos via SQL usando a consulta que retorna o id gerado:
                int idObra1 = buscarIdPorTitulo(UsuarioDAO.getConnection(), "Dom Casmurro");
                if (idObra1 > 0) {
                    obraDAO.atribuirAvaliador(idObra1, "333.333.333-33");
                    ok("atribuirAvaliador na obra id=" + idObra1);

                    // atualizar status para APROVADO
                    obraDAO.atualizarStatus(idObra1, "APROVADO", LocalDate.now());
                    ok("atualizarStatus -> APROVADO na obra id=" + idObra1);
                }
            }

            // atualizar dados da obra2
            int idObra2 = buscarIdPorTitulo(UsuarioDAO.getConnection(), "O Alienista");
            if (idObra2 > 0) {
                obraDAO.atualizar(idObra2, "O Alienista", "Novela", 1882);
                ok("atualizar genero obra2 -> 'Novela'");
            }

            // ==================================================================
            // 6. RELATORIO DAO
            // ==================================================================
            titulo("TESTE 6 – RelatorioDAO");

            RelatorioDAO relatorioDAO = new RelatorioDAO();
            info("Total de obras:       " + relatorioDAO.totalObras());
            info("Total de autores:     " + relatorioDAO.totalAutores());
            info("Total de avaliadores: " + relatorioDAO.totalAvaliadores());
            relatorioDAO.relatorioStatusObras();
            relatorioDAO.relatorioObrasPorAutor();
            relatorioDAO.relatorioObrasPorAvaliador();
            ok("gerarRelatorioGeral:");
            relatorioDAO.gerarRelatorioGeral();

            // ==================================================================
            // 7. SYSTEM SEARCH DAO
            // ==================================================================
            titulo("TESTE 7 – SystemSearchDAO");

            SystemSearchDAO ssDAO = new SystemSearchDAO();

            System.out.println("  [buscarObrasPorAutor 'Carlos R. Silva']");
            ssDAO.buscarObrasPorAutor("Carlos R. Silva");

            System.out.println("  [buscarObraPorStatus 'APROVADO']");
            ssDAO.buscarObraPorStatus("APROVADO");

            System.out.println("  [buscarObraPorStatus 'PENDENTE']");
            ssDAO.buscarObraPorStatus("PENDENTE");

            System.out.println("  [buscarObraPorTitulo 'Capitães da Areia']");
            ssDAO.buscarObraPorTitulo("Capitães da Areia");

            System.out.println("  [listarTodasObras]");
            ssDAO.listarTodasObras();

            System.out.println("  [listarAutores]");
            ssDAO.listarAutores();

            System.out.println("  [listarAvaliadores]");
            ssDAO.listarAvaliadores();

            Autor achado = ssDAO.buscarAutorPorNome("Carlos R. Silva");
            ok("buscarAutorPorNome: " + (achado != null ? "encontrado" : "não encontrado"));

            Avaliador achadoAval = ssDAO.buscarAvaliadorPorNome("Pedro Lima");
            ok("buscarAvaliadorPorNome: " + (achadoAval != null ? "encontrado" : "não encontrado"));

            // ==================================================================
            // 8. LIMPEZA – excluir tudo em ordem (FK: obras antes de autores, etc.)
            // ==================================================================
            titulo("TESTE 8 – Limpeza (excluir registros de teste)");

            // excluir obras pelo id
            for (String t : new String[]{"Dom Casmurro", "O Alienista", "Capitães da Areia"}) {
                int id = buscarIdPorTitulo(UsuarioDAO.getConnection(), t);
                if (id > 0) { obraDAO.excluir(id); ok("Excluída obra: " + t); }
            }

            // excluir especializações antes do usuario base
            autorDAO.excluir("111.111.111-11");
            autorDAO.excluir("222.222.222-22");
            ok("Autores excluídos");

            avaliadorDAO.excluir("333.333.333-33");
            ok("Avaliador excluído");

            gerenteDAO.excluir("444.444.444-44");
            ok("Gerente excluído");

            // agora excluir os usuarios base
            usuarioDAO.excluir("111.111.111-11");
            usuarioDAO.excluir("222.222.222-22");
            usuarioDAO.excluir("333.333.333-33");
            usuarioDAO.excluir("444.444.444-44");
            ok("Usuarios excluídos");

            // confirmar que banco ficou limpo
            List<Usuario> restantes = usuarioDAO.listarTodos();
            ok("Usuarios restantes no banco (deve ser 0 de teste): " + restantes.size());

            // ==================================================================
            titulo("TODOS OS TESTES CONCLUÍDOS COM SUCESSO");

        } finally {
            // fechar todas as conexões ao terminar
            UsuarioDAO.closeConnection();
            AutorDAO.closeConnection();
            AvaliadorDAO.closeConnection();
            GerenteDAO.closeConnection();
            ObraDAO.closeConnection();
            RelatorioDAO.closeConnection();
            SystemSearchDAO.closeConnection();
        }
    }

    // ─── auxiliar: busca o id real de uma obra no banco pelo título ───────────
    private static int buscarIdPorTitulo(Connection con, String titulo) {
        String sql = "SELECT id FROM obra WHERE titulo = ? LIMIT 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                ps.close();
                return id;
            }
            ps.close();
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }
}
