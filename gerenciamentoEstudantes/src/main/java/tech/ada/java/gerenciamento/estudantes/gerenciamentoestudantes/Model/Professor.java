package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "professores")

public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Chave Primária
    private Long id;
    private String nomeProfessor;
    private String email;
    private String disciplinaLecionada;
    private Boolean estaAtivo;
    @ManyToMany(mappedBy = "professores")
    @JsonIgnoreProperties({"professores", "estudantes"})
    private Set<Turma> listaTurmas = new HashSet<>();

    //apagar quando o modelmapper funcionar
    public Professor() {
    }

    public Professor(String nomeProfessor, String email, String disciplinaLecionada, Boolean estaAtivo) {
        this.nomeProfessor = nomeProfessor;
        this.email = email;
        this.disciplinaLecionada = disciplinaLecionada;
        this.estaAtivo = estaAtivo;
    }

    public void AdicionarTurma (Turma turma){
        listaTurmas.add(turma);
        turma.getProfessores().add(this);
    }

}
