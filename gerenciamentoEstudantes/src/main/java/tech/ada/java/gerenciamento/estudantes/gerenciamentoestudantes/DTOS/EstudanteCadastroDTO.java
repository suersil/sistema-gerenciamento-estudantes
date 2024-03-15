package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Getter
    private Boolean estaAtivo;

    @JsonCreator
    public EstudanteCadastroDTO(@JsonProperty("nomeAluno") String nomeAluno,
                                @JsonProperty("nomeResponsavel") String nomeResponsavel,
                                @JsonProperty("dataNascimento") String dataNascimento,
                                @JsonProperty("contatoResponsavel") String contatoResponsavel) {
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Informe o nome do aluno");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Informe o nome do responsável");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Informe a data de nascimento");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Informe o contato do responsável");
        this.estaAtivo = true;
    
    }

}

