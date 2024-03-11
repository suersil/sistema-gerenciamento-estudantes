package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AlterarTurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;

import java.util.List;

public interface ServiceTurma {

    Turma cadastrarTurma(TurmaDTO turmaDTO) throws BadRequest;

    List<Turma> buscarTurmas();

    Turma buscarTurmaEspecifica(Long id) throws Exception;

    Turma alterarTurma(Long id, AlterarTurmaRequest alterarTurmaRequest) throws Exception;

    Turma alteraTurmaCompleto(Long id, AlterarTurmaRequest alterarTurmaRequest);

    List<Turma> findTurmaByEstaAtiva(Boolean status);
}
