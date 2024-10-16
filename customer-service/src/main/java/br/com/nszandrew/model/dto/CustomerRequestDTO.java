package br.com.nszandrew.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CustomerRequestDTO(
        @NotNull
        String fullName,
        @NotNull
        @Email
        String email,
        String accountType,
        @Past
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dateOfBirth,
        @NotNull @Size(min = 11, max = 11, message = "CPF precisa ter 11 digitos")
        String cpf,
        @NotNull
        String phone,
        String address) {
}
