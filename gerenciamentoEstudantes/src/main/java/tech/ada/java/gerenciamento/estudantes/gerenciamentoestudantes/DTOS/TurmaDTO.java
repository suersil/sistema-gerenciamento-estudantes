package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        return new Turma(nomeTurma, estaAtiva);
    }
    @JsonCreator
    public TurmaDTO(@JsonProperty("nomeTurma") String nomeTurma, @JsonProperty ("estaAtiva") Boolean estaAtiva) {
        this.nomeTurma = nomeTurma;
        this.estaAtiva = estaAtiva;
    }
}
