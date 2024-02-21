package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import java.time.LocalDateTime;
import java.util.Objects;

public record AtualizarEstudanteRequest
        (boolean ativo, String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel) {

    public AtualizarEstudanteRequest(boolean ativo, String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel) {
        this.ativo = Objects.requireNonNull(ativo, "É necessário informar se o estudante esta ativo");
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Nome do aluno é necessário");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento do aluno é necessário");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Nome do responsável é necessário");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Contato do responsável é necessário");
    }
}
