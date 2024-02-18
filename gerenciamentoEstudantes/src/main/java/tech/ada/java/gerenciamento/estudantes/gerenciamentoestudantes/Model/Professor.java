package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    //apagar quando o modelmapper funcionar
    public Professor() {
    }

    public Professor(String nomeProfessor, String email, String disciplinaLecionada) {
        this.nomeProfessor = nomeProfessor;
        this.email = email;
        this.disciplinaLecionada = disciplinaLecionada;
    }

}
