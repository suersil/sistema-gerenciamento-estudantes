package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarEstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.EstudanteRequest;



public interface ServiceEstudante {

    public ResponseEntity<Estudante> cadastrarEstudante(EstudanteCadastroDTO request);
   
    public ResponseEntity<List<Estudante>> listarTodosEstudantes();
   
    public ResponseEntity<List<Estudante>> filtrarStatusEstudante(Boolean status);

    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(Long id);
   
    ResponseEntity<List<Estudante>> filtrarEstudanteNome(String nomeAluno);
     
    ResponseEntity<Estudante> editarTudoEstudante(Long id, EstudanteCadastroDTO request);   

    ResponseEntity<Estudante> atualizarEstudante(Long id, EstudanteRequest request);

 }


    

