package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import jakarta.validation.constraints.NotBlank;


public class EstudanteCadastroDTO {
    
    @NotBlank(message = "o nome do aluno é obrigatório")
    private String nomeAluno;
    
    @NotBlank
    private String nomeResponsavel;
    
    @NotBlank
    private String dataNascimento;
    
    @NotBlank
    private String contatoResponsavel;

}
