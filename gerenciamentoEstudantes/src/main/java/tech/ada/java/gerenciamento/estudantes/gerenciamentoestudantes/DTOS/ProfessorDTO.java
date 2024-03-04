package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;

@Getter
@AllArgsConstructor
public class ProfessorDTO {

    @NotBlank(message = "{nomeProfessor.not.blank}")
    private String nomeProfessor;

    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
    private String email;

    @NotBlank(message = "{disciplinaLecionada.not.blank}")
    private String disciplinaLecionada;

    private Boolean estaAtivo;

    //Transformar a request em entidade
    public Professor paraEntidade(){
        return new Professor(nomeProfessor, email, disciplinaLecionada, estaAtivo);
    }
}
