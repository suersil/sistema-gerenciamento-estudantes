package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class GerenciamentoEstudantesApplication {
    public static void main(String[] args) {
        SpringApplication.run(GerenciamentoEstudantesApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
