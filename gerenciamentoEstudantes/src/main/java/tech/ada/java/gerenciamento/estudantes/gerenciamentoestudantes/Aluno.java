package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //Matricula * Chave primária - auto-incrementado
    private Long id;
    private String nomeAluno;

    private String email;
    private String dataNascimento;
    private String numeroTelefone;

    // Getters and setters
}
