package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import lombok.Getter;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

import java.time.LocalDate;

@Getter
public class TurmaRequest {
    private String nomeTurma;
    private Boolean estaAtiva;
    public Turma toEntity() {
        return new Turma(nomeTurma, estaAtiva);
    }
}
