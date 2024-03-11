package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;

import java.util.List;

public interface ServiceProfessor {

    List<Professor> listarTodos();

    Professor cadastrarProfessor(ProfessorDTO professorDTO);

    Professor editarParcialProfessor(Long id, ProfessorRequest professorRequest) throws Exception;

    Professor atualizarProfessor(Long id, AtualizarProfessorRequest atualizarProfessorRequest) throws Exception;

    List<Professor> filtrarProfessorPorNome(String nomeProfessor);


}
