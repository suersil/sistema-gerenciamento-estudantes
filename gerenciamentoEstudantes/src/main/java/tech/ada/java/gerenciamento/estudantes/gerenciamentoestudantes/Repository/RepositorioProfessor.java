package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;

public interface RepositorioProfessor extends JpaRepository<Professor, Long> {
}
