package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarEstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class ControllerEstudanteTest {

    @InjectMocks
    ControllerEstudante controllerEstudante;

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
                "123456789", true);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerEstudante).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void listarEstudantesComSucesso(){
        when(repositorioEstudante.findAll()).thenReturn(List.of(estudante));
        ResponseEntity<List<Estudante>> response = controllerEstudante.listarTodosEstudantes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    public void deveCadastrarEstudanteComSucesso() throws Exception {
        when(repositorioEstudante.save(Mockito.any())).thenReturn(estudante);
        when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(estudante);

        ResponseEntity<Estudante> response = controllerEstudante.cadastrarEstudante(estudanteCadastroDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(estudante, response.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(estudanteCadastroDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deveFiltrarStatusTurmaAtivo() {
        when(repositorioEstudante.findEstudantesByEstaAtivo(true)).thenReturn(List.of(estudante));

        ResponseEntity<List<Estudante>> response = controllerEstudante.filtrarStatusTurma(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void deveFiltrarStatusTurmaInativo() {
        when(repositorioEstudante.findEstudantesByEstaAtivo(false)).thenReturn(List.of(estudante));

        ResponseEntity<List<Estudante>> response = controllerEstudante.filtrarStatusTurma(false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void deveFiltrarEstudanteId() {
        when(repositorioEstudante.findById(1L)).thenReturn(Optional.of(estudante));

        ResponseEntity<Optional<Estudante>> response = controllerEstudante.filtrarEstudanteId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudante, response.getBody().get());
    }

    @Test
    public void deveFiltrarEstudanteNome() {
        String nome = "testeNome";
        when(repositorioEstudante.findByNomeAlunoQuery(nome)).thenReturn(List.of(estudante));

        ResponseEntity<List<Estudante>> response = controllerEstudante.filtrarEstudanteNome(nome);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(estudante, response.getBody().get(0));
    }

    //PUT
    @Test
    public void deveEditarTudoEstudante() {
        Long id = 1L;
        when(repositorioEstudante.findById(id)).thenReturn(Optional.of(estudante));

        AtualizarEstudanteRequest atualizarRequest = new AtualizarEstudanteRequest(true, "NovoNomeTeste", "NovaDataTeste", "NovoNomeResponsavelTeste", "NovoContatoResponsavelTeste");
        try {
            controllerEstudante.editarTudoEstudante(id, atualizarRequest);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

