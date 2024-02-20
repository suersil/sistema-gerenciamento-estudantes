package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

import java.util.List;

@Repository
public interface RepositorioTurma extends JpaRepository<Turma, Long> {

    public List<Turma> findTurmaByEstaAtiva(Boolean status);

}
