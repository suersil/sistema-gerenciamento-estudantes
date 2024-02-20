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
    private Boolean estaAtiva;
//    private String listaIdEstudantes;
//    private String listaIdProfessores;


    public Turma(String nomeTurma, boolean estaAtiva) {
        this.nomeTurma = nomeTurma;
        this.estaAtiva = estaAtiva;
    }

    public Turma() {
        this.estaAtiva = false;
    }
}
