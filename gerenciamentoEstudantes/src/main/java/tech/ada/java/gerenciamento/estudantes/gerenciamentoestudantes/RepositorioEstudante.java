package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAluno extends JpaRepository<Aluno, Long> {
}
