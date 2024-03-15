package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AlterarTurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.ArrayList;
import java.util.Collections;
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
    AlterarTurmaRequest turmaRequest;
    List<Turma> listaTurma;
    String nomeTurma= "5oAnoC";
    Boolean estaAtiva = true;

    @BeforeEach
    public void setup() {
        turma = new Turma(nomeTurma, estaAtiva);
        turmaDTO = new TurmaDTO(nomeTurma, estaAtiva);
        turmaRequest = new AlterarTurmaRequest(estaAtiva, nomeTurma);
    }

    @Test
    void deveCadastrarTurmaComSucesso() {
        when(repositorioTurma.save(any())).thenReturn(turmaDTO.toEntity());
        Turma turmaCadastrada = serviceTurma.cadastrarTurma(turmaDTO);
        assertNotNull(turmaCadastrada);
        assertEquals(nomeTurma, turmaCadastrada.getNomeTurma());
        assertEquals(estaAtiva, turmaCadastrada.getEstaAtiva());
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarBadRequestCadastrarTurma() {
        when(repositorioTurma.save(any())).thenThrow(new BadRequest("Bad Request ao Cadastrar Turmas"));
        assertThrows(BadRequest.class, () -> {
            serviceTurma.cadastrarTurma(turmaDTO);
        });
        verify(repositorioTurma, times(1)).save(any());
    }

    @Test
    void deveBuscarTurmasComSucesso() {
        when(repositorioTurma.findAll()).thenReturn(List.of(turma));
        List<Turma> listaTurmas = serviceTurma.buscarTurmas();
        assertNotNull(listaTurmas);
        assertEquals("Turma", listaTurmas.get(0).getClass().getSimpleName());
        assertEquals(1, listaTurmas.size());
        assertEquals(Turma.class, listaTurmas.get(0).getClass());
        verify(repositorioTurma).findAll();
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarNotFoundExceptionBuscarTurmas() {
        when(repositorioTurma.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceTurma.buscarTurmas();
        });
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveBuscarTurmaEspecificaComSucesso() throws Exception{
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.of(turma));
        Turma turmaBuscada = serviceTurma.buscarTurmaEspecifica(1L);
        assertNotNull(turmaBuscada);
        verify(repositorioTurma, times(1)).findById(1L);
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarNotFoundExceptionBuscarTurmaEspecifica() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceTurma.buscarTurmaEspecifica(1L);
        });
        verify(repositorioTurma, times(1)).findById(1L);
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveAlterarTurmaComSucesso() throws Exception{
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.of(turma));
        when(repositorioTurma.save(any())).thenReturn(turma);
        Turma turmaAtualizada = serviceTurma.alterarTurma(1L, turmaRequest);
        assertNotNull(turmaAtualizada);
        assertEquals(estaAtiva, turmaAtualizada.getEstaAtiva());
        assertEquals(nomeTurma, turmaAtualizada.getNomeTurma());
        verify(repositorioTurma, times(1)).findById(1L);
        verify(repositorioTurma, times(1)).save(any());
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarNotFoundExceptionAlterarTurma() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceTurma.alterarTurma(1L, turmaRequest);
        });
        verify(repositorioTurma, times(1)).findById(1L);
        verifyNoMoreInteractions(repositorioTurma);
    }


    @Test
    void deveAlterarTurmaCompletoComSucesso() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.of(turma));
        when(repositorioTurma.save(any())).thenReturn(turma);
        Turma turmaAtualizada = serviceTurma.alteraTurmaCompleto(1L, turmaRequest);
        assertNotNull(turmaAtualizada);
        assertEquals(estaAtiva, turmaAtualizada.getEstaAtiva());
        assertEquals(nomeTurma, turmaAtualizada.getNomeTurma());
        verify(repositorioTurma, times(1)).findById(1L);
        verify(repositorioTurma, times(1)).save(any());
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarNotFoundExceptionAlterarTurmaCompleto() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceTurma.alteraTurmaCompleto(1L, turmaRequest);
        });
        verify(repositorioTurma, times(1)).findById(1L);
        verifyNoMoreInteractions(repositorioTurma);
    }


    @Test
    void deveListarTurmaByEstaAtivaComSucesso() throws Exception{
        when(repositorioTurma.findTurmaByEstaAtiva(true)).thenReturn(List.of(turma));
        List<Turma> turmaFiltrada = serviceTurma.findTurmaByEstaAtiva(true);
        assertNotNull(turmaFiltrada);
        assertEquals(1, turmaFiltrada.size());
        assertFalse(turmaFiltrada.isEmpty());
        verify(repositorioTurma, times(1)).findTurmaByEstaAtiva(true);
        verifyNoMoreInteractions(repositorioTurma);
    }

    @Test
    void deveRetornarNotFoundExceptionListarTurmaByEstaAtiva(){
        when(repositorioTurma.findTurmaByEstaAtiva(true)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () ->{
            serviceTurma.findTurmaByEstaAtiva(true);
        });
        verify(repositorioTurma, times(1)).findTurmaByEstaAtiva(true);
        verifyNoMoreInteractions(repositorioTurma);
    }
}