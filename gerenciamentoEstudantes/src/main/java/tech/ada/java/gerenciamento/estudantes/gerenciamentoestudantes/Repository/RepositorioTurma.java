package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

import java.util.List;

public interface RepositorioTurma extends JpaRepository<Turma, Long> {

    public List<Turma> findTurmaByEstaAtiva(Boolean status);
}
