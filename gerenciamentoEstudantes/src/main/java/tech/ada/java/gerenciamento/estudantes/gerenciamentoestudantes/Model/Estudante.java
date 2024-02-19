package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Matricula * Chave primária - auto-incrementado
    private Long id;
    private boolean ativo;
    private String nomeAluno;
    private String nomeResponsavel;
    private String dataNascimento;
    private String contatoResponsavel;

    // Getters and setters
}
