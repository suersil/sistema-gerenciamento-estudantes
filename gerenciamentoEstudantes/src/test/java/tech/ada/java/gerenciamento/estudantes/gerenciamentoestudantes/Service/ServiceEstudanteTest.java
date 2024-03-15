package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller.ControllerEstudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller.ControllerEstudanteTest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarEstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.EstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceEstudante;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

       /*  mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(estudanteCadastroDTO)))
                .andExpect(status().isCreated()); */
    }

    @Test
    public void deveListarEstudantesComSucesso() throws Exception {
        ServiceEstudante serviceEstudanteMock = mock(ServiceEstudante.class);
        //para retornar uma lista vazia
        when(serviceEstudanteMock.listarTodosEstudantes()).thenReturn(Collections.emptyList());

        ControllerEstudante controllerEstudante = new ControllerEstudante(serviceEstudanteMock);

        ResponseEntity<List<Estudante>> response = controllerEstudante.listarTodosEstudantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
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

    @Test
    public void devefiltrarEstudanteIdComSucesso() {
        Long id = 1L;
        Estudante estudante = new Estudante();
        estudante.setId(id);
        Optional<Estudante> optionalEstudante = Optional.of(estudante);

        when(repositorioEstudante.findById(id)).thenReturn(optionalEstudante);

        Optional<Estudante> response = serviceEstudante.filtrarEstudanteId(id);

        assertEquals(optionalEstudante, response);
    }

    @Test
    public void deveFiltrarEstudanteNome() {
        String nome = "testeNome";
        when(repositorioEstudante.findByNomeAlunoQuery(nome)).thenReturn(List.of(estudante));

       List<Estudante> response = serviceEstudante.filtrarEstudanteNome(nome);

        
        assertNotNull(response);
        assertEquals(estudante, response.getFirst());
    }

    //PUT
    @Test
    public void deveEditarTudoEstudante() {
        Long id = 1L;
        when(repositorioEstudante.findById(id)).thenReturn(Optional.of(estudante));

        EstudanteCadastroDTO  atualizarRequest = new EstudanteCadastroDTO( "NovoNomeTeste", "NovaDataTeste", "NovoNomeResponsavelTeste", "NovoContatoResponsavelTeste");
        try {
            serviceEstudante.editarTudoEstudante(id, atualizarRequest);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Patch
    @Test
    public void deveAtualizarEstudante() {
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
}
