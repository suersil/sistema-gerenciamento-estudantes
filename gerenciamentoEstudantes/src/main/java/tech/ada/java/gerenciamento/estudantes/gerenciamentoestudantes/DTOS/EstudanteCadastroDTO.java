package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import java.util.Objects;

public class EstudanteCadastroDTO {
    private String nomeAluno;
    private String nomeResponsavel;
    private String dataNascimento;
    private String contatoResponsavel;
    private Boolean estaAtivo;
    
    public EstudanteCadastroDTO(String nomeAluno, String nomeResponsavel, String dataNascimento, String contatoResponsavel, Boolean estaAtivo) {
        this.nomeAluno = Objects.requireNonNull(nomeAluno, "Informe o nome do aluno");
        this.nomeResponsavel = Objects.requireNonNull(nomeResponsavel, "Informe o nome do responsável");
        this.dataNascimento = Objects.requireNonNull(dataNascimento, "Informe a data de nascimento");
        this.contatoResponsavel = Objects.requireNonNull(contatoResponsavel, "Informe o contato do responsável");
        this.estaAtivo = estaAtivo;
    }
    
    public String getNomeAluno() {
        return nomeAluno;
    }
    
    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    
    public String getNomeResponsavel() {
        return nomeResponsavel;
    }
    
    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }
    
    public String getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getContatoResponsavel() {
        return contatoResponsavel;
    }
    
    public void setContatoResponsavel(String contatoResponsavel) {
        this.contatoResponsavel = contatoResponsavel;
    }

    public boolean estaAtivo() {
        return estaAtivo;
    }
    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }
}
