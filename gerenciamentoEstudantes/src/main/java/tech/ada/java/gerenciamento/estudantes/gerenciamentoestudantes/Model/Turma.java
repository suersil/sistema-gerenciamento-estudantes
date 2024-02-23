package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{nomeTurma.not.blank}")
    private String nomeTurma;

    private Boolean estaAtiva;
//    private String listaIdEstudantes;
//    private String listaIdProfessores;

    private boolean turmaAtiva;

//    private List<Estudante> listaEstudantes;
//    private List<Professor> listaProfessores;


    public Turma(String nomeTurma, boolean estaAtiva) {
        this.nomeTurma = nomeTurma;
        this.estaAtiva = estaAtiva;
    }

    public Turma() {
        this.estaAtiva = false;
    }
}
