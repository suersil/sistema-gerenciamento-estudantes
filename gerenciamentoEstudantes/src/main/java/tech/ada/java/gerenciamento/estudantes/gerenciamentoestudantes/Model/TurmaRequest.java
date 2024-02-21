package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TurmaRequest {
    private String nomeTurma;
    private Boolean estaAtiva;
    public Turma toEntity() {
        return new Turma(nomeTurma, estaAtiva);
    }
}
