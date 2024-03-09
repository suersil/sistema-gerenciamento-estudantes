package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.config.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }

    @Test
    void listarEstudantesComSucesso(){
//        when(repositorioEstudante.findAll()).thenReturn(List.of(estudante));
//        ResponseEntity<List<Estudante>> response = controllerEstudante.listarTodosEstudantes();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    public void deveCadastrarEstudanteComSucesso() throws Exception {
        when(repositorioEstudante.save(Mockito.any())).
                thenReturn(estudante);
        when(modelMapper.map(Mockito.any(), Mockito.any())).
                thenReturn(estudante);

        ResponseEntity<Estudante> response = controllerEstudante.cadastrarEstudante(estudanteCadastroDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(estudante,response.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(estudante)))
                .andExpect(status().isCreated());
        verify(controllerEstudante, times(1)).cadastrarEstudante(Mockito.any());
}
//    @Test
//    void cadastrarEstudante() {
//    }

//    @Test
//    void listarTodosEstudantes() {
//    }

    @Test
    void filtrarStatusTurma() {
    }

    @Test
    void filtrarEstudanteId() {
    }

    @Test
    void filtrarEstudanteNome() {
    }

    @Test
    void editarTudoEstudante() {
    }

    @Test
    void atualizarEstudante() {
    }
}