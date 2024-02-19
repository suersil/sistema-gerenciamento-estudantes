package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

@Repository
public interface RepositorioTurma extends JpaRepository<Turma, Long> {
}
