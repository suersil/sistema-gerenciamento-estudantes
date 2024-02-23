package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import java.time.LocalDateTime;
import java.util.Objects;

public record AtualizarEstudanteRequest
        (boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel,
         String contatoResponsavel) {

    public AtualizarEstudanteRequest(boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel,
                                     String contatoResponsavel) {
        this.estaAtivo = Objects.requireNonNull(estaAtivo, "É necessário informar se o estudante esta ativo");
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Nome do aluno é necessário");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento do aluno é necessário");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Nome do responsável é necessário");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Contato do responsável é necessário");
    }
}
