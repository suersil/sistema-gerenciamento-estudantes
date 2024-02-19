package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;

import java.util.List;

@Repository
public interface RepositorioProfessor extends JpaRepository<Professor, Long> {

    public List<Professor> findProfessorsByNomeProfessor(String nomeProfessor);
}
