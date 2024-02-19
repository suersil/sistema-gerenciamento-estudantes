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
    private String dataNascimento;
    private String nomeResponsavel;
    private String contatoResponsavel;
    private LocalDateTime dataDeCadastro;
    private LocalDateTime dataAtualizacaoCadastro;

    public Estudante(){
        this.ativo= false;
    }

    //Editando dados cadastrados
    public Estudante(boolean ativo, String nomeAluno, String dataNascimento, String nomeResponsavel, String contatoResponsavel){
        this.ativo = ativo;
        this.nomeAluno = nomeAluno;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.contatoResponsavel = contatoResponsavel;
        this.dataAtualizacaoCadastro = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
