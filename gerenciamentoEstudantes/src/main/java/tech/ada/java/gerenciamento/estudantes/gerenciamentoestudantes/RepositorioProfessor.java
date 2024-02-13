package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMatricula extends JpaRepository<Matricula, Long> {
}
