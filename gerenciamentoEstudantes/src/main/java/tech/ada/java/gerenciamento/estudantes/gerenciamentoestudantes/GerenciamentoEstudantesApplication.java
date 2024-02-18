package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller.ControllerTurma;

@SpringBootApplication
//@ComponentScan(basePackageClasses = ControllerTurma.class)
public class GerenciamentoEstudantesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoEstudantesApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
