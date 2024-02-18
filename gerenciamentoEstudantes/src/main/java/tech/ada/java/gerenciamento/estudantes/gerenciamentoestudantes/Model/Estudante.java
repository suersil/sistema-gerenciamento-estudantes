package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
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
    private LocalDateTime dataDeCadastro;
    
    public Estudante(){
        this.dataDeCadastro= LocalDateTime.now(ZoneId.of("UTC"));
        this.ativo= true;
    }
}
