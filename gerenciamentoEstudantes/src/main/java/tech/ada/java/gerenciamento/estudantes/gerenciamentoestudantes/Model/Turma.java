package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da turma não pode estar em  branco")
    private String nomeTurma;
    private Boolean estaAtiva;

    @OneToMany(mappedBy = "turma")
    private List<Estudante> estudantes;
    @ManyToMany
    @JoinTable(
            name = "turma_professor",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    @JsonIgnoreProperties({"listaTurmas"})
    private List<Professor> professores = new ArrayList<>();
    
    public Turma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
        this.estaAtiva = true;
    }

    public Turma() {
    }

    public Turma(String nomeTurma, Boolean estaAtiva) {
    }
}
