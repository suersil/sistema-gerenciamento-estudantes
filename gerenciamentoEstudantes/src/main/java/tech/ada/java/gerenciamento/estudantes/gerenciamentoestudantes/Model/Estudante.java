package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "alunos")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean estaAtivo;
    @Setter
    @Getter
    private String nomeAluno;
    private String dataNascimento;
    private String nomeResponsavel;
    private String contatoResponsavel;
    @CreationTimestamp
    private LocalDateTime dataDeCadastro;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    //Turma turma;
    
    public Estudante(Boolean estaAtivo) {
        this.estaAtivo = true;
    }
    
    //Construtor - Editando dados cadastrados // PUT
    public Estudante(boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel,
                     String contatoResponsavel){
        this.estaAtivo = estaAtivo ;
        this.nomeAluno = nomeAluno;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.contatoResponsavel = contatoResponsavel;
        this.dataDeCadastro= LocalDateTime.now(ZoneId.of("UTC"));
    }
}
