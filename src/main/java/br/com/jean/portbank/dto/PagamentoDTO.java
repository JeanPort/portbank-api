package br.com.jean.portbank.dto;

import java.time.LocalDateTime;

public record PagamentoDTO(
        Integer numeroConta,
        LocalDateTime dataHora,
        String numDoc,
        String descricao,
        Double valor) {

}
