package br.com.nszandrew.exception;

import br.com.nszandrew.exception.custom.AccountNotFoundException;
import br.com.nszandrew.exception.custom.InsufficientFundsException;
import br.com.nszandrew.exception.custom.StatusAccountIsClosed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusAccountIsClosed.class)
    public final ResponseEntity<ExceptionResponse> CustomerNotFoundException(StatusAccountIsClosed ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.CONFLICT, ex.getMessage(), "Status account is CLOSED, try again later or call support");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public final ResponseEntity<ExceptionResponse> InsufficientFundsException(InsufficientFundsException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST, ex.getMessage(), "Please add more funds in your account");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> AccountNotFoundException(AccountNotFoundException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND, ex.getMessage(), "Account not found");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
