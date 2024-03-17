package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import java.util.List;
import java.util.Optional;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.EstudanteRequest;



public interface ServiceEstudante {

    Estudante cadastrarEstudante(EstudanteCadastroDTO request);
   
    List<Estudante> listarTodosEstudantes();
   
    List<Estudante> filtrarStatusEstudante(Boolean status);

    Optional<Estudante> filtrarEstudanteId(Long id);
   
    List<Estudante> filtrarEstudanteNome(String nomeAluno);
     
    Estudante editarTudoEstudante(Long id, EstudanteCadastroDTO request);

    Estudante atualizarEstudante(Long id, EstudanteRequest request);

 }


    

