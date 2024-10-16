package br.com.nszandrew.model.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @NotNull
        String accountType,
        Long id) {
}
