package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "alunos")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean estaAtivo;
    private String nomeAluno;
    private String nomeResponsavel;
    private String dataNascimento;
    private String contatoResponsavel;
    private LocalDateTime dataDeCadastro;
    
    public Estudante(){
        this.dataDeCadastro= LocalDateTime.now(ZoneId.of("UTC"));
        this.estaAtivo = true;
    }
    
    public Estudante(String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel) {
        this.nomeAluno = nomeAluno;
        this.nomeResponsavel = nomeResponsavel;
        this.dataNascimento = dataNascimento;
        this.contatoResponsavel = contatoResponsavel;
        System.out.println("usou");
    }
}
