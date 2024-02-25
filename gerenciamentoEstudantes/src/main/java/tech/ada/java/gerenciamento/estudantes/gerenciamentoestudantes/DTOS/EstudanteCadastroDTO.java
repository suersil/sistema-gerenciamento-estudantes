package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter

public class EstudanteCadastroDTO {
    @Getter
    private String nomeAluno;
    @Getter
    private String nomeResponsavel;
    @Getter
    private String dataNascimento;
    @Getter
    private String contatoResponsavel;
    private Boolean estaAtivo;
    
    public EstudanteCadastroDTO(String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel, Boolean estaAtivo) {
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Informe o nome do aluno");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Informe o nome do responsável");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Informe a data de nascimento");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Informe o contato do responsável");
        this.estaAtivo = estaAtivo;
    }

    public boolean estaAtivo() {
        return estaAtivo;
    }
}
