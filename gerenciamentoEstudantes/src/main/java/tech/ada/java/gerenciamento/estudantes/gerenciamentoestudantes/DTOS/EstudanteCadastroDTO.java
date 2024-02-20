package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstudanteCadastroDTO {
    
    @NotBlank(message = "informe o nome do aluno")
    private String nomeAluno;
    
    @NotBlank
    private String nomeResponsavel;
    
    @NotBlank
    private String dataNascimento;
    
    @NotBlank
    private String contatoResponsavel;
    
 
}
