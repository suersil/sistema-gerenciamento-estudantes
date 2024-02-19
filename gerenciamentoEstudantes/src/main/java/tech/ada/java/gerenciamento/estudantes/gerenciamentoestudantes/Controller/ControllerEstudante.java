package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController("estudante")
public class ControllerEstudante {
    
    private final RepositorioEstudante repositorioEstudante;
    private final ModelMapper modelMapper;
    
    @Autowired
    public ControllerEstudante(RepositorioEstudante repositorioEstudante, ModelMapper modelMapper) {
        this.repositorioEstudante = repositorioEstudante;
        this.modelMapper = modelMapper;
    }
    
    @PostMapping("/estudante")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody @Valid EstudanteCadastroDTO request) {
        
        Estudante estudante = modelMapper.map(request, Estudante.class);
       
        Estudante novoEstudante = repositorioEstudante.save(estudante);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);
    }
    
    @GetMapping("/estudante")
    public ResponseEntity<List<Estudante>>listarTodosEstudantes(){
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }

    //By ID
    @PutMapping("/estudante/{id}") //AtualizandoTudo
    public ResponseEntity<Estudante> editarEstudante
            (@PathVariable("id") Long id, @RequestBody AtualizarEstudanteRequest atualizarEstudante) throws Exception {
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

        //Primeiro checar cadastro existente
        if (optionalEstudante.isPresent()) {
            return ResponseEntity.notFound().build();
        }

            // Se existir vamos fazer o get(by ID)
            Estudante estudanteExistente = optionalEstudante.get();

            estudanteExistente.setAtivo(atualizarEstudante.ativo());
            estudanteExistente.setNomeAluno(atualizarEstudante.nomeAluno());
            estudanteExistente.setDataNascimento(atualizarEstudante.dataNascimento());
            estudanteExistente.setNomeResponsavel(atualizarEstudante.nomeResponsavel());
            estudanteExistente.setContatoResponsavel(atualizarEstudante.contatoResponsavel());
            //estudanteExistente.setDataAtualizacaoCadastro(LocalDateTime.now());

            Estudante estudanteSalvo = repositorioEstudante.save(estudanteExistente);

            return ResponseEntity.ok(estudanteSalvo);

    }

    @PatchMapping("/estudante/{id}")
    public ResponseEntity<Estudante> alteraStatus(
            @PathVariable Long id,
            @RequestBody EstudanteStatusRequest request) throws Exception {
        // Buscar pelo metodo findById que retorna um Optional<TodoItem>
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

        // Verificamos se existe valor dentro do Optional
        if (optionalEstudante.isPresent()) {
            // Se existir - fazer o get()
            Estudante estudanteItemModificado = optionalEstudante.get();
            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if (request.ativo()) estudanteItemModificado.setAtivo(request.ativo());
            if (request.nomeAluno() != null) estudanteItemModificado.setNomeAluno(request.nomeAluno());
            if (request.nomeResponsavel() != null) estudanteItemModificado.setNomeResponsavel(request.nomeResponsavel());
            if (request.contatoResponsavel() != null) estudanteItemModificado.setContatoResponsavel(request.contatoResponsavel());;

            //Depois de atualizar - vamos salvar
            Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado);
            return ResponseEntity.ok(estudanteSalvo);
        }

            // Caso nao encontramos na valor no Optional retornamos o codigo 404 - nao encontrado
            return ResponseEntity.notFound().build();
    }
}
