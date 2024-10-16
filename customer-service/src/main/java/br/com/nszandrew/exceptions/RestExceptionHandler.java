package br.com.nszandrew.exceptions;

import br.com.nszandrew.exceptions.custom.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Exception");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public final ResponseEntity<ExceptionResponse> InternalServerErrorException(Exception ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Erro interno do servidor");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> CustomerNotFoundException(Exception ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND, ex.getMessage(), "Cliente não encontrado");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
