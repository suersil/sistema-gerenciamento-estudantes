package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    //RepositorioTurma turmaRepositorio;


    //instanciar a classe que usamos como retorno em alguns metodos
    Professor professor1;
    Professor professor2;
    List<Professor> listaProfessores;

    @BeforeEach //annotation para dados que podem ser usados em todos os testes
    public void setup() {
        professor1 = new Professor("Brunno Nogueira",
                                    "brunno@ada.com.br",
                                    "Progamacao Web",
                                    true);

        professor2 = new Professor("Igor Magalhaes",
                "igor@ada.com.br",
                "POO",
                true);

        listaProfessores = new ArrayList<>(); //inicializar a lista
        listaProfessores.add(professor1);
        listaProfessores.add(professor2);
    }

    @Test
    void deveListarProfessoresComSucesso() {
        //when: config do mock, metodo que é chamado dentro do metodo de teste
        //thenReturn: o que esperamos ser retornado quando o metodo é chamado
        when(repositorioProfessor.findAll()).thenReturn(listaProfessores);

        //chamada do metodo da controller
        ResponseEntity<List<Professor>> responseEntity = controllerProfessor.listarTodos();

        //assertions
        //comparar o resultado obtido com o resultado esperado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(listaProfessores, responseEntity.getBody());

        // Verifica se o método findAll foi chamado apenas uma vez
        verify(repositorioProfessor).findAll();
        // Verifica se não houve mais interações com o mock repositorioProfessor
        verifyNoMoreInteractions(repositorioProfessor);

    }

    @Test
    void listarProfessoresComListaVazia(){
        //Mock de uma lista vazia de professores
        List<Professor> listaProfessores = new ArrayList<>();

        //Config do comportamento do mock
        when(repositorioProfessor.findAll()).thenReturn(listaProfessores);

        //Verificacao da excecao
        assertThrows(ResourceNotFoundException.class, () -> {
            controllerProfessor.listarTodos();
        });
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