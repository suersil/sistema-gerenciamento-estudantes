package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import lombok.Getter;

@Getter
public class ProfessorRequest {

    private String nomeProfessor;
    private String email;
    private String disciplinaLecionada;
    private Boolean estaAtivo;

    //Transformar a request em entidade
    public Professor paraEntidade(){
        return new Professor(nomeProfessor, email, disciplinaLecionada, estaAtivo);
    }
}
