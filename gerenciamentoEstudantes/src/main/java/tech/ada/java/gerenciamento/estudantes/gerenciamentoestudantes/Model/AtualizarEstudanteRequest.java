package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import java.util.Objects;

public record AtualizarEstudanteRequest(
        boolean estaAtivo,
        String nomeAluno,
        String dataNascimento,
        String nomeResponsavel,
        String contatoResponsavel) {

    public AtualizarEstudanteRequest {
        Objects.requireNonNull(nomeAluno, "Nome do aluno é necessário");
        Objects.requireNonNull(dataNascimento, "Data de nascimento do aluno é necessária");
        Objects.requireNonNull(nomeResponsavel, "Nome do responsável é necessário");
        Objects.requireNonNull(contatoResponsavel, "Contato do responsável é necessário");
    }
}

