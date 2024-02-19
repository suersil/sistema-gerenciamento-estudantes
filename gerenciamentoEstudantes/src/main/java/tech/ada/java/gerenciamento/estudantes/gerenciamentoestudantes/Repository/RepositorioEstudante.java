package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;

import java.util.List;

@Repository
public interface RepositorioEstudante extends JpaRepository <Estudante, Long> {

    List<Estudante> findByNomeAluno(String titulo);

    @Query("SELECT Estudante FROM Estudante WHERE Estudante.nomeAluno = ?1") //t - é o apelido que demos(forma generica), Diferente do "nativeQuery" (*)
    List<Estudante> findByNomeAlunoQuery(String titulo);
}
