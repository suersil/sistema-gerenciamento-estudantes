package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Chave primária - auto-incrementar
    private Long id;
    private String nomeTurma;
    private boolean ativa;
    private String listaIdEstudantes;

    // Getters and setters
}
