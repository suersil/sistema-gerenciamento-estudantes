package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(NoHandlerFoundException exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException exception, WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    
}
