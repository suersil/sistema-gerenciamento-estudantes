package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import jakarta.persistence.*;

@Entity
@Table(name = "professores")

public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Chave Primária
    private Long id;
    private String nomeProfessor;
    private String email;
    private String disciplinaLecionada;

}
