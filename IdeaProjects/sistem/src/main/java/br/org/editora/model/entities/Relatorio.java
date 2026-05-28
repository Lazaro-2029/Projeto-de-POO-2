package br.org.editora.model.entities;
class Relatorio {

    public void totalObras(SystemSearch sistema){
        System.out.println("Total de obras cadastradas: " + sistema.getObras().size());
    }

    public void totalAutores(SystemSearch sistema){
        System.out.println("Total de autores cadastrados: " + sistema.getAutores().size());
    }

    public void totalAvaliadores(SystemSearch sistema){
        System.out.println("Total de avaliadores cadastrados: " + sistema.getAvaliadores().size());
    }

    public void relatorioStatusObras(SystemSearch sistema){
        int pendentes = 0;
        int aprovadas = 0;
        int reprovadas = 0;

        for (Obra obra : sistema.getObras()){
            if (obra.getStatus().equalsIgnoreCase("PENDENTE")){
                pendentes++;
            }
            else if (obra.getStatus().equalsIgnoreCase("APROVADO")){
                aprovadas++;
            }
            else if (obra.getStatus().equalsIgnoreCase("REPROVADO")){
                reprovadas++;
            }
        }

        System.out.println("Obras Pendentes: " + pendentes);
        System.out.println("Obras Aprovadas: " + aprovadas);
        System.out.println("Obras Reprovadas: " + reprovadas);
    }

    public void relatorioObrasPorAutor(SystemSearch sistema){
        for (Autor autor : sistema.getAutores()){
            System.out.println("Autor: " + autor.getautor().getNome());
            System.out.println("Quantidade de obras: " + autor.getObras().size());
            System.out.println("-------------------");
        }
    }

    public void relatorioObrasPorAvaliador(SystemSearch sistema){
        for (Avaliador avaliador : sistema.getAvaliadores()){
            System.out.println("Avaliador: " + avaliador.getavaliador().getNome());
            System.out.println("Quantidade avaliadas: " + avaliador.getObrasAvaliadas().size());
            System.out.println("-------------------");
        }
    }

    public void gerarRelatorioGeral(SystemSearch sistema){
        System.out.println("===== RELATÓRIO GERAL DO SISTEMA =====");

        totalObras(sistema);
        totalAutores(sistema);
        totalAvaliadores(sistema);

        System.out.println("-------------------");

        relatorioStatusObras(sistema);

        System.out.println("-------------------");

        relatorioObrasPorAutor(sistema);

        System.out.println("-------------------");

        relatorioObrasPorAvaliador(sistema);
    }
}