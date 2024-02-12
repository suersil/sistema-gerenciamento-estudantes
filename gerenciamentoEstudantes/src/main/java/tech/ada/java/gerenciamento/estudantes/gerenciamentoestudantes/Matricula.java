package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import jakarta.persistence.*;

@Entity
@Table(name = "matricula")

public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //Chave Primária
    private Long id;
    private Long idAluno;
    private Long idCurso;

}
