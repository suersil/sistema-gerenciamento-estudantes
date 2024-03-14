package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTurmaTest {
    @InjectMocks
    ServiceTurmaImpl serviceTurma;

    @Mock
    RepositorioTurma repositorioTurma;
    @Mock
    ModelMapper modelMapper;

    Turma turma;
    TurmaDTO turmaDTO;

    String nomeTurma= "5oAnoC";
    Boolean estaAtiva = true;

    @BeforeEach
    public void setup() {
        turma = new Turma(nomeTurma, estaAtiva);
        turmaDTO = new TurmaDTO(nomeTurma, estaAtiva);
    }


    @Test
    void cadastrarTurma() {
        when(repositorioTurma.save(any())).thenReturn(turmaDTO.toEntity());
        Turma turmaCadastrada = serviceTurma.cadastrarTurma(turmaDTO);
        assertNotNull(turmaCadastrada);
        assertEquals(nomeTurma, turmaCadastrada.getNomeTurma());
        assertEquals(estaAtiva, turmaCadastrada.getEstaAtiva());
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void buscarTurmas() {
        //when: config do mock, metodo que é chamado dentro do metodo de teste
        //thenReturn: o que esperamos ser retornado quando o metodo é chamado
        when(repositorioTurma.findAll()).thenReturn(List.of(turma));
        List<Turma> listaTurmas = serviceTurma.buscarTurmas();

        //assertions
        assertNotNull(listaTurmas); //assegurar que a lista nao é nula
        assertEquals("Turma", listaTurmas.get(0).getClass().getSimpleName());// Verifica o tipo do primeiro elemento da lista
        assertEquals(1, listaTurmas.size()); //assegurar que tamanho da lista esperado com o obtido/atual
        assertEquals(Turma.class, listaTurmas.get(0).getClass()); //assegurar que o objeto esperado é do mesmo tipo que o obj obtido

        // Verifica se o método findAll foi chamado apenas uma vez
        verify(repositorioTurma).findAll();
        // Verifica se não houve mais interações com o mock repositorioTurma
        verifyNoMoreInteractions(repositorioTurma);

    }

    @Test
    void buscarTurmaEspecifica() {
    }

    @Test
    void alterarTurma() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.of(turma));

    }

    @Test
    void alteraTurmaCompleto() {
    }

    @Test
    void findTurmaByEstaAtiva() {
    }
}