package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;

import java.util.List;

@Repository
public interface RepositorioEstudante extends JpaRepository <Estudante, Long> {

    List<Estudante> findByNomeAluno(String nomeAluno);

    @Query("SELECT e FROM Estudante e WHERE e.nomeAluno = ?1")
    List<Estudante> findByNomeAlunoQuery(String nomeAluno);
}
