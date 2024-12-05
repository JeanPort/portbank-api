package br.com.jean.portbank.dto;

import java.time.LocalDateTime;

public record ExtratoDTO(Integer numeroConta, LocalDateTime inicio, LocalDateTime fim) {
}
