package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS;

import java.util.Objects;

public record AtualizarProfessorRequest(String nomeProfessor, String email, String disciplinaLecionada, Boolean estaAtivo){

    //Transformar a request em entidade verificando se os atributos nao sao nulos
    public AtualizarProfessorRequest(String nomeProfessor, String email, String disciplinaLecionada, Boolean estaAtivo) {
        this.nomeProfessor = Objects.requireNonNull(nomeProfessor, "nomeProfessor é obrigatório");
        this.email = Objects.requireNonNull(email, "email é obrigatório");
        this.disciplinaLecionada = Objects.requireNonNull(disciplinaLecionada, "disciplinaLecionada é obrigatória");
        this.estaAtivo = Objects.requireNonNull(estaAtivo);
    }
}
