package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.EstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceEstudante;

@ExtendWith(MockitoExtension.class)
public class ControllerEstudanteTest {
    @Mock
    private ServiceEstudante serviceEstudante;
    @Mock
    ModelMapper modelMapper;
    Estudante estudante;
    EstudanteCadastroDTO estudanteDto;
    EstudanteRequest estudanteRequest;
    List<Estudante> estudantes;
    LocalDateTime data = LocalDateTime.now();
    Turma turma;

    @InjectMocks
    private ControllerEstudante controllerEstudante;
    private MockMvc mockMvc; 

   @BeforeEach
    public void setup() {
        turma = new Turma();
        estudante = new Estudante(true, "Joao","02.12.122",
         "Alguem", "99929929",data, turma);

        estudanteDto = new EstudanteCadastroDTO("Joao",
                "Alguem",
                "02.12.20",
                "123456789");
        estudanteRequest = new EstudanteRequest(true,"João", "Alguém","111222333",1L);
        modelMapper = new ModelMapper();
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
    public void cadastrarEstudanteComSucessoHttpTest() throws Exception {
        Estudante estudante = modelMapper.map(estudanteDto, Estudante.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDto)))
                        .andExpect(status().isCreated()); //Para corresponder ao status 201
    }

    @Test
    void retornarBadRequestCadastrandoEstudante() throws Exception {
        when(serviceEstudante.cadastrarEstudante(any())).thenThrow(new BadRequest("BAD REQUEST"));

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDto)))
                        .andExpect(status().isBadRequest()); // status 400 Bad Request.

        verify(serviceEstudante, times(1)).cadastrarEstudante(any());
    }

    @Test
    void listarTodosComSucessoHttpTest() throws Exception {

        Estudante estudante = new Estudante();
        estudante.setNomeAluno("Fulaninho");

        when(serviceEstudante.listarTodosEstudantes()).thenReturn(List.of(estudante));

        mockMvc.perform(MockMvcRequestBuilders.get("/estudante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$.[0].nomeAluno", equalTo("Fulaninho")));

        verify(serviceEstudante, times(1)).listarTodosEstudantes();
    }
    
    @Test
    void filtrarStatusEstudanteComSucessoHttp() throws Exception {
       
        List<Estudante> estudantesAtivos = List.of(
                new Estudante(true,"Melissa","02.02.02","Luiz","123456789", data, turma),
                new Estudante(true,"Melissa2","02.02.02","Luiz","123456789", data, turma)
        );
        when(serviceEstudante.filtrarStatusEstudante(true)).thenReturn(estudantesAtivos);
        
        
        mockMvc.perform(MockMvcRequestBuilders.get("/estudante?status=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
    
    @Test
    void filtrarEstudanteIdComSucessoHttp() throws Exception {
        Long id = 1L;
        Estudante estudante = new Estudante();
        estudante.setId(id);
        estudante.setNomeAluno("Fulano");
        
        when(serviceEstudante.filtrarEstudanteId(id)).thenReturn(Optional.of(estudante));
        mockMvc.perform(MockMvcRequestBuilders.get("/estudante/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeAluno", equalTo("Fulano")));
        
        verify(serviceEstudante, times(1)).filtrarEstudanteId(id);
    }
    
    @Test
    void filtrarEstudanteNomeComSucessoHttp() throws Exception {
        String nomeAluno = "Fulano";
        List<Estudante> estudantes = new ArrayList<>();
        estudantes.add(new Estudante(true, nomeAluno, "02.02.02", "Luiz", "123456789", LocalDateTime.now(), new Turma()));
        when(serviceEstudante.filtrarEstudanteNome(nomeAluno)).thenReturn(estudantes);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/estudante")
                        .param("nomeAluno", nomeAluno))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$.[0].nomeAluno", equalTo(nomeAluno)));
        verify(serviceEstudante, times(1)).filtrarEstudanteNome(nomeAluno);
    }
    
    @Test
    void editarTudoEstudanteComSucessoHttp() throws Exception {
        Long idEstudante = 1L;
        
        Estudante estudanteAtualizado = modelMapper.map(estudanteDto, Estudante.class);
        estudanteAtualizado.setId(idEstudante);
        estudanteAtualizado.setTurma(new Turma());
        estudanteAtualizado.setDataAtualizacao(LocalDateTime.now());
        
        when(serviceEstudante.editarTudoEstudante(eq(idEstudante), any()))
                .thenReturn(estudanteAtualizado);
        
        mockMvc.perform(MockMvcRequestBuilders.put("/estudante/{id}", idEstudante)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDto)))
                .andExpect(status().isOk());
        verify(serviceEstudante, times(1)).editarTudoEstudante(eq(idEstudante), any());
    }
    
    @Test
    public void atualizarEstudanteComSucessoHttpTest() throws Exception {
        Long id = 1L;
        Estudante estudanteAtualizado = modelMapper.map(estudanteDto, Estudante.class);
        estudanteAtualizado.setId(id);
        
        when(serviceEstudante.atualizarEstudante(id, estudanteRequest)).thenReturn(estudanteAtualizado);
       
        mockMvc.perform(MockMvcRequestBuilders.patch("/estudante/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id", equalTo(id.intValue())));
        
        verify(serviceEstudante, times(1)).atualizarEstudante(id, estudanteRequest);
    }
    
   @Test
    void atualizarEstudanteComException() throws Exception {
        when(serviceEstudante.atualizarEstudante(anyLong(), any())).thenThrow(new ResourceNotFoundException("Estudante", "ID", 1L));
        
        mockMvc.perform(MockMvcRequestBuilders.patch("/estudante/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteRequest)))
                .andExpect(status().isNotFound());
        
        verify(serviceEstudante, times(1)).atualizarEstudante(anyLong(), any());
    }
    
  
}
