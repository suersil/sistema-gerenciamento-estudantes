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
    private String dataNascimento;
    private String nomeResponsavel;
    private String contatoResponsavel;
    private LocalDateTime dataDeCadastro;
    private LocalDateTime dataAtualizacaoCadastro;

    //Turma turma;

    public Estudante(){
        this.dataDeCadastro= LocalDateTime.now(ZoneId.of("UTC"));
        this.estaAtivo = true;
    }

    //Editando dados cadastrados
    public Estudante(String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel){
        this.nomeAluno = nomeAluno;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.contatoResponsavel = contatoResponsavel;
        this.dataAtualizacaoCadastro = LocalDateTime.now(ZoneId.of("UTC"));

        this.dataDeCadastro= LocalDateTime.now(ZoneId.of("UTC"));
        this.estaAtivo= true;
    }
}
