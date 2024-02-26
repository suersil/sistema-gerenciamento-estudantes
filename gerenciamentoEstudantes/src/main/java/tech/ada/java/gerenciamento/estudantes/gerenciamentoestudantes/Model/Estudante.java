package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name="nome_completo", nullable = false)

    @NotBlank(message = "{nomeAluno.not.blank}")
    private String nomeAluno;

    @NotBlank(message = "{dataNascimento.not.blank}")
    private String dataNascimento;

    @NotBlank(message = "{nomeResponsavel.not.blank}")
    private String nomeResponsavel;

    @NotBlank(message = "{contatoResponsavel.not.blank}")
    private String contatoResponsavel;

    @CreationTimestamp
    private LocalDateTime dataDeCadastro;
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "turma_id")
    @JsonIgnore
    private Turma turma;
    
    public Estudante(Boolean estaAtivo) {
        this.estaAtivo = true;
    }

    //Construtor - Editando dados cadastrados // PUT
    public Estudante(boolean estaAtivo, String nomeAluno, String dataNascimento, String nomeResponsavel,
                     String contatoResponsavel, LocalDateTime dataDeCadastro, Turma turma) {
        this.estaAtivo = estaAtivo;
        this.nomeAluno = nomeAluno;
        this.dataNascimento = dataNascimento;
        this.nomeResponsavel = nomeResponsavel;
        this.contatoResponsavel = contatoResponsavel;
        this.dataDeCadastro = LocalDateTime.now(ZoneId.of("UTC"));
        this.turma = turma;
    }
}
