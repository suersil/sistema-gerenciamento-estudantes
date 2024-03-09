package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;

@Getter
public class ProfessorDTO {

    @NotBlank(message = "{nomeProfessor.not.blank}")
    private String nomeProfessor;

    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    @NotBlank(message = "{disciplinaLecionada.not.blank}")
    private String disciplinaLecionada;

    private Boolean estaAtivo;

    @JsonCreator
    public ProfessorDTO(@JsonProperty("nomeProfessor") String nomeProfessor,
                        @JsonProperty("email") String email,
                        @JsonProperty("disciplinaLecionada") String disciplinaLecionada,
                        @JsonProperty("estaAtivo")Boolean estaAtivo) {
        this.nomeProfessor = nomeProfessor;
        this.email = email;
        this.disciplinaLecionada = disciplinaLecionada;
        this.estaAtivo = estaAtivo;
    }

    //Transformar a request em entidade
    public Professor paraEntidade(){
        return new Professor(nomeProfessor, email, disciplinaLecionada, estaAtivo);
    }
}
