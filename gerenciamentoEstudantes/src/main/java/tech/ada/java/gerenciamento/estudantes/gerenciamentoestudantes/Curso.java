package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //Chave primária - auto-incrementar
    private Long id;
    private String nomeCurso;
    private String nomeProfessor;

    // Getters and setters
}
