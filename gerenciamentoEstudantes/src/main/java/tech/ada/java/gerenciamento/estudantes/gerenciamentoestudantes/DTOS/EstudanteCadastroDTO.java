package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import java.util.Objects;

public record EstudanteCadastroDTO
        (String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel) {
    
    public EstudanteCadastroDTO(String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel) {
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Informe o nome do aluno");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Informe o nome do responsável");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Informe a data de nascimento");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Informe o contato do responsável");
    }
}
