package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import java.util.Objects;

public record AtualizarProfessorRequest(String nomeProfessor, String email, String disciplinaLecionada, boolean estaAtivo){

    //Transformar a request em entidade verificando se os atributos nao sao nulos
    public AtualizarProfessorRequest(String nomeProfessor, String email, String disciplinaLecionada, boolean estaAtivo) {
        this.nomeProfessor = Objects.requireNonNull(nomeProfessor, "nomeProfessor é obrigatório");
        this.email = Objects.requireNonNull(email, "email é obrigatório");
        this.disciplinaLecionada = Objects.requireNonNull(disciplinaLecionada, "disciplinaLecionada é obrigatória");
        this.estaAtivo = Objects.requireNonNull(estaAtivo);
    }
}
