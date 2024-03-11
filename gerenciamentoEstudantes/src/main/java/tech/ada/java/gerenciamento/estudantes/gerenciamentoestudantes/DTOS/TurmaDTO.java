package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

//DTO
@Getter
public class TurmaDTO {

    @NotBlank(message = "{nomeTurma.not.blank}")
    private String nomeTurma;

    private Boolean estaAtiva;

    public Turma toEntity() {
        return new Turma(nomeTurma);
    }
}
