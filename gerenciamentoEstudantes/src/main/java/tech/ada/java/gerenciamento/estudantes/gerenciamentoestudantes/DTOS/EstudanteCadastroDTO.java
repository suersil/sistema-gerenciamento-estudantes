package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstudanteCadastroDTO {
    
    @NotBlank
    private String nomeAluno;
    @NotBlank
    private String nomeResponsavel;
    @NotBlank
    private String dataNascimento;
 
    private String contatoResponsavel;
}
