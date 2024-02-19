package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeTurma;
    private boolean turmaAtiva;
//    private String listaIdEstudantes;
//    private String listaIdProfessores;


    public Turma(String nomeTurma, boolean turmaAtiva) {
        this.nomeTurma = nomeTurma;
        this.turmaAtiva = turmaAtiva;
    }

    public Turma() {
        this.turmaAtiva = false;
    }
}
