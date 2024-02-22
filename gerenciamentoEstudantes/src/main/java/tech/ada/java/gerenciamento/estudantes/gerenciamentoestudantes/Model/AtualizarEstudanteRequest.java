package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import java.util.Objects;

public record AtualizarEstudanteRequest
        (boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel) {
    public AtualizarEstudanteRequest
            (boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel) {

        this.estaAtivo = estaAtivo();
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Nome do aluno é necessário");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Data de nascimento do aluno é necessário");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Nome do responsável é necessário");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Contato do responsável é necessário");
    }
}

