package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;

public class ControllerTurma {

    private final RepositorioTurma repositorioTurma;
    private final ModelMapper modelMapper;

    public ControllerTurma(RepositorioTurma repositorioTurma, ModelMapper modelMapper) {
        this.repositorioTurma = repositorioTurma;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/turma", params = "status")
    public ResponseEntity<List<Turma>> filtrarStatusTurma(@RequestParam Boolean status){
        List<Turma> statusTurmaFiltrada;

        if(status){
            statusTurmaFiltrada = repositorioTurma.findTurmaByEstaAtiva(true);
        }else{
            statusTurmaFiltrada = repositorioTurma.findTurmaByEstaAtiva(false);
        }

        if(!statusTurmaFiltrada.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(statusTurmaFiltrada);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }

}
