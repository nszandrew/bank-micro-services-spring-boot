package br.com.nszandrew.exceptions;

import br.com.nszandrew.exceptions.custom.AccountNotFoundException;
import br.com.nszandrew.exceptions.custom.MaximumLimitException;
import br.com.nszandrew.exceptions.custom.PaymentAlreadyCompletedException;
import br.com.nszandrew.exceptions.custom.PaymentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Exception");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND, ex.getMessage(), "Account not found");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlePaymentNotFoundException(PaymentNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_FOUND, ex.getMessage(), "Payment id not found");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentAlreadyCompletedException.class)
    public final ResponseEntity<ExceptionResponse> handlePaymentNotFoundException(PaymentAlreadyCompletedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), "Payment already completed");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MaximumLimitException.class)
    public final ResponseEntity<ExceptionResponse> handleMaxLimitException(MaximumLimitException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST, ex.getMessage(), "Max limit");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}