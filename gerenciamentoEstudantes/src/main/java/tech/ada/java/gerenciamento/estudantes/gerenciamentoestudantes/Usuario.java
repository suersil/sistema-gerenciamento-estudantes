package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Matricula * Chave primária - auto-incrementado
    private Long id;
    private String username;
    private String password;
    
}
