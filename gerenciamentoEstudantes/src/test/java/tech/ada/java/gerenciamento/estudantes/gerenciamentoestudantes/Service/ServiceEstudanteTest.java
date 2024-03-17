package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceEstudanteTest {

    @InjectMocks
    ServiceEstudanteImpl serviceEstudante;
    @Mock
    RepositorioEstudante repositorioEstudante;
    @Mock
    RepositorioTurma turmaRepositorio;
    @Mock
    ModelMapper modelMapper;

    Estudante estudante;
    EstudanteCadastroDTO estudanteCadastroDTO;
    Turma turma;
    LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        estudante = new Estudante(true,"Joao",
                "02.12.20",
                "Alguem",
                "123456789",data,turma);

        estudanteCadastroDTO = new EstudanteCadastroDTO("Joao",
                "Alguem",
                "02.12.20",
                "123456789");
        mockMvc = MockMvcBuilders.standaloneSetup(serviceEstudante).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deveCadastrarEstudanteComSucesso() throws Exception {
        when(repositorioEstudante.save(Mockito.any())).thenReturn(estudante);
        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(estudante);

        Estudante response = serviceEstudante.cadastrarEstudante(estudanteCadastroDTO);

        assertNotNull(response);
        assertEquals(estudante, response);

        assertEquals(estudante.getNomeAluno(), response.getNomeAluno());
        assertEquals(estudante.getNomeResponsavel(), response.getNomeResponsavel());
        assertEquals(estudante.getDataNascimento(), response.getDataNascimento());
        assertEquals(estudante.getContatoResponsavel(), response.getContatoResponsavel());
        assertEquals(estudante.getEstaAtivo(), response.getEstaAtivo());

        verifyNoMoreInteractions(repositorioEstudante);

       /*  mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(estudanteCadastroDTO)))
                .andExpect(status().isCreated()); */
    }

    @Test
    public void deveListarEstudantesComSucesso() throws Exception {
        when(repositorioEstudante.findAll()).thenReturn(List.of(estudante));
        List<Estudante> response = serviceEstudante.listarTodosEstudantes();

        assertNotNull(response);
        assertEquals("Estudante", response.get(0).getClass().getSimpleName());
        assertEquals(1, response.size());
        assertEquals(Estudante.class, response.get(0).getClass());

        verify(repositorioEstudante).findAll();
        verifyNoMoreInteractions(repositorioEstudante);
    }

    //ResourceNotFoundException
    @Test
    public void deveListarEstudantesComNotFoundException() throws Exception {
        when(repositorioEstudante.findAll()).thenReturn(Collections.emptyList());

        try {
            serviceEstudante.listarTodosEstudantes();
            fail("Expected ResourceNotFoundException to be thrown");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Não há registros de lista de estudantes no sistema.", ex.getMessage());
        }
        verify(repositorioEstudante).findAll();
        verifyNoMoreInteractions(repositorioEstudante);
    }

    @Test
    public void deveFiltrarStatusEstudanteAtivo() {
        when(repositorioEstudante.findEstudantesByEstaAtivo(true)).thenReturn(List.of(estudante));

        List<Estudante> response = serviceEstudante.filtrarStatusEstudante(true);
        
        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    public void deveFiltrarStatusEstudanteInativo() {
        when(repositorioEstudante.findEstudantesByEstaAtivo(false)).thenReturn(List.of(estudante));

       List<Estudante> response = serviceEstudante.filtrarStatusEstudante(false);

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    //ResourceNotFoundException
    @Test
    public void deveFiltrarStatusEstudanteComNotFoundExcepction() {
        Mockito.when(repositorioEstudante.findEstudantesByEstaAtivo(Mockito.anyBoolean()))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            serviceEstudante.filtrarStatusEstudante(true);
            serviceEstudante.filtrarStatusEstudante(false);
        });
    }

    @Test
    public void deveFiltrarEstudanteIdComSucesso() {
        Estudante estudante = new Estudante();
        estudante.setId(anyLong());
        Optional<Estudante> optionalEstudante = Optional.of(estudante);

        when(repositorioEstudante.findById(1L)).thenReturn(optionalEstudante);

        Optional<Estudante> response = serviceEstudante.filtrarEstudanteId(1L);

        assertEquals(optionalEstudante, response);
    }

    //ResourceNotFoundException
    @Test
    public void deveFiltrarEstudanteIdComNotFoundException(){
        Optional<Estudante> optionalEstudante = Optional.empty();

        when(repositorioEstudante.findById(1L)).thenReturn(optionalEstudante);

        assertThrows(ResourceNotFoundException.class, () -> serviceEstudante.filtrarEstudanteId(1L));
    }

    //IllegalArgumentException
    @Test
    public void deveFiltrarEstudanteIdNullIllegalArgumentException(){
        Long estudanteId = null;
        assertThrows(IllegalArgumentException.class, () -> serviceEstudante.filtrarEstudanteId(estudanteId));
    }

    @Test
    public void deveFiltrarEstudanteNomeComSucesso() {
        when(repositorioEstudante.findByNomeAlunoQuery(anyString())).thenReturn(List.of(estudante));

       List<Estudante> response = serviceEstudante.filtrarEstudanteNome("NomeAlunoTeste");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(estudante, response.getFirst());

        verify(repositorioEstudante, times(1)).findByNomeAlunoQuery("NomeAlunoTeste");
        verifyNoMoreInteractions(repositorioEstudante);
    }

    //ResourceNotFoundException
    @Test
    public void deveFiltrarEstudanteNomeComNotFoundException() {
        String nomeAlunoTeste = "nomeAlunoTeste";

        when(repositorioEstudante.findByNomeAlunoQuery(eq(nomeAlunoTeste))).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> {
            serviceEstudante.filtrarEstudanteNome(nomeAlunoTeste);
        });

        verify(repositorioEstudante, times(1)).findByNomeAlunoQuery(eq(nomeAlunoTeste));
        verifyNoMoreInteractions(repositorioEstudante);
    }

    //PUT
    @Test
    public void deveEditarTudoEstudanteComSucesso() {
        when(repositorioEstudante.findById(anyLong())).thenReturn(Optional.of(estudante));

        EstudanteCadastroDTO  atualizarRequest = new EstudanteCadastroDTO( "NovoNomeTeste", "NovaDataTeste", "NovoNomeResponsavelTeste", "NovoContatoResponsavelTeste");
        try {
            serviceEstudante.editarTudoEstudante(1L, atualizarRequest);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //PUT - ResourceNotFoundException
    @Test
    public void deveEditarTudoEstudanteComNotFoundException() {
        EstudanteCadastroDTO  atualizarRequest = new EstudanteCadastroDTO( "NovoNomeTeste", "NovaDataTeste", "NovoNomeResponsavelTeste", "NovoContatoResponsavelTeste");

        // Simulando que o estudante não foi encontrado
        when(repositorioEstudante.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            serviceEstudante.editarTudoEstudante(1L, atualizarRequest);
        });
    }

    //Patch
    @Test
    public void deveAtualizarEstudanteComSucesso() {
        Long idEstudante = 1L;
        EstudanteRequest request = new EstudanteRequest(true, "NovoNomeAluno", "NovoNomeResponsavel",
                "NovoContatoResponsavel", 1L);

        Estudante estudanteExistente = new Estudante();
        estudanteExistente.setId(1L);
        estudanteExistente.setNomeAluno("NomeAntigo");
        estudanteExistente.setNomeResponsavel("ResponsavelAntigo");
        estudanteExistente.setContatoResponsavel("ContatoAntigo");
        estudanteExistente.setEstaAtivo(false);

        Turma turma = new Turma();
        turma.setId(1L);

        when(repositorioEstudante.findById(idEstudante)).thenReturn(Optional.of(estudanteExistente));
        when(turmaRepositorio.findById(1L)).thenReturn(Optional.of(turma));
        when(repositorioEstudante.save(any(Estudante.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Estudante estudanteAtualizado = serviceEstudante.atualizarEstudante(idEstudante, request);

        assertNotNull(estudanteAtualizado);
        assertEquals(request.nomeAluno(), estudanteAtualizado.getNomeAluno());
        assertEquals(request.nomeResponsavel(), estudanteAtualizado.getNomeResponsavel());
        assertEquals(request.contatoResponsavel(), estudanteAtualizado.getContatoResponsavel());
        assertEquals(request.estaAtivo(), estudanteAtualizado.getEstaAtivo());
    }

    //PATCH - ResourceNotFoundException
    @Test
    public void deveAtualizarEstudanteComNotFoundException() {
        Long idEstudante = 1L;
        EstudanteRequest request = new EstudanteRequest(true, "NovoNomeAluno", "NovoNomeResponsavel",
                "NovoContatoResponsavel", 1L);

        // Simulando que o estudante não foi encontrado
        when(repositorioEstudante.findById(idEstudante)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            serviceEstudante.atualizarEstudante(idEstudante, request);
        });
    }
}
