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
        System.out.println("Usou 3");
    }

  /*   public EstudanteCadastroDTO(String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel) {
        System.out.println("Usou 1");
        this.nomeAluno = nomeAluno;
        this.nomeResponsavel = nomeResponsavel;
        this.dataNascimento = dataNascimento;
        this.contatoResponsavel = contatoResponsavel;
    }

    public Boolean estaAtivo() {
        System.out.println("Usou2");
        return estaAtivo;
    } */

   


}

