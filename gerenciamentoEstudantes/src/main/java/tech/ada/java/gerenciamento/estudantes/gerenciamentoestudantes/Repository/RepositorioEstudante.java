package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import java.util.List;

@Repository
public interface RepositorioEstudante extends JpaRepository <Estudante, Long> {

    public List<Estudante> findEstudantesByEstaAtivo(Boolean status);

    @Query("SELECT e FROM Estudante e WHERE e.nomeAluno LIKE %?1%") //Like = Nome parcial, parecido, apenas primeiro nome ou sobrenome.
    List<Estudante> findByNomeAlunoQuery(String nomeAluno);
}
