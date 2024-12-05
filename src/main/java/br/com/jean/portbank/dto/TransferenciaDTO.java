package br.com.jean.portbank.dto;

import java.time.LocalDateTime;

public record TransferenciaDTO(Integer contaOrigem, Integer contaDestino, LocalDateTime dataHora, Double valor, String descricao) {
}
