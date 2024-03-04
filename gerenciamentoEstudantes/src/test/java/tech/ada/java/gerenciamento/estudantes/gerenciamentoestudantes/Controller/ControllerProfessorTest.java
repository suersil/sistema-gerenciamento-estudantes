package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerProfessorTest {

    @InjectMocks //injetar a classe controllerProfessor para os nossos testes
    ControllerProfessor controllerProfessor;

    @Mock //mockar classes que usamos na controller
    RepositorioProfessor repositorioProfessor;
    @Mock
    ModelMapper modelMapper;
    //RepositorioTurma turmaRepositorio;


    //instanciar a classe que usamos como retorno em alguns metodos
    Professor professor;
    ProfessorDTO professorDTO;
    List<Professor> listaProfessores;

    @BeforeEach //annotation para dados que podem ser usados em todos os testes
    public void setup() {
        professor = new Professor("Brunno Nogueira",
                                    "brunno@ada.com.br",
                                    "Progamacao Web",
                                    true);

        professorDTO = new ProfessorDTO("Brunno Nogueira",
                                    "brunno@ada.com.br",
                                    "Progamacao Web",
                                    true);

//        professor2 = new Professor("Igor Magalhaes",
//                "igor@ada.com.br",
//                "POO",
//                true);
//
//        listaProfessores = new ArrayList<>(); //inicializar a lista
//        listaProfessores.add(professor);
//        listaProfessores.add(professor2);
    }

    @Test
    void deveCadastrarProfessorComSucesso(){
        when(repositorioProfessor.save(any())).thenReturn(professor);

        ResponseEntity<Professor> responseEntity = controllerProfessor.cadastrarProfessor(professorDTO);

        assertNotNull(responseEntity);
        assertEquals(ResponseEntity.class, responseEntity.getClass());
        assertEquals(professor, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED ,responseEntity.getStatusCode());

        verifyNoMoreInteractions(repositorioProfessor);
    }

    @Test
    void deveListarProfessoresComSucesso() {
        //when: config do mock, metodo que é chamado dentro do metodo de teste
        //thenReturn: o que esperamos ser retornado quando o metodo é chamado
        when(repositorioProfessor.findAll()).thenReturn(List.of(professor));

        //chamada do metodo da controller
        ResponseEntity<List<Professor>> responseEntity = controllerProfessor.listarTodos();

        //assertions
        assertNotNull(responseEntity); //assegurar que a response nao é nula
        assertEquals(1, responseEntity.getBody().size()); //assegurar que tamanho da lista esperado com o obtido/atual
        assertEquals(Professor.class, responseEntity.getBody().get(0).getClass()); //assegurar que o objeto esperado é do mesmo tipo que o obj obtido
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); //assegurar que o resultado esperado com o resultado obtido/atual
        assertEquals(List.of(professor), responseEntity.getBody()); //assegurar que os dados da lista esperada com o da lista recebida

        // Verifica se o método findAll foi chamado apenas uma vez
        verify(repositorioProfessor).findAll();
        // Verifica se não houve mais interações com o mock repositorioProfessor
        verifyNoMoreInteractions(repositorioProfessor);

    }

    @Test
    void deveRetornarResourceNotFoundExceptionQuandoListarProfessores(){
        //Config do comportamento do mock
        //lancar uma exception quando chamar o metodo findAll
        when(repositorioProfessor.findAll()).thenThrow(new ResourceNotFoundException("lista de professores"));

        try {
            controllerProfessor.listarTodos();
        } catch (Exception ex) {
            assertEquals(ResourceNotFoundException.class, ex.getClass()); //comparar tipo da exceptio lancada
            assertEquals("Não há registros de lista de professores no sistema.", ex.getMessage()); //comparar msg lancada
        }

        assertThrows(ResourceNotFoundException.class, () -> {
            controllerProfessor.listarTodos();}); //Verificacar se a excecao é lançada quando o metodo retorna uma lista vazia
        verifyNoMoreInteractions(repositorioProfessor); //verificar se nao houve mais interacao com o repository

    }

    @Test
    void editarTudoProfessor() {
    }

    @Test
    void atualizarProfessor() {
    }

    @Test
    void filtrarProfessorPorNome() {
    }
}