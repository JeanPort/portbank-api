package br.com.jean.portbank.service;

import br.com.jean.portbank.dto.ExtratoDTO;
import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.TransferenciaDTO;
import br.com.jean.portbank.exception.InvalidAccountException;
import br.com.jean.portbank.exception.InvalidDataIntervalException;
import br.com.jean.portbank.exception.NotEnoughBalanceException;
import br.com.jean.portbank.service.transacao.ITransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransacaoServiceTest {

    //@Mock
    @Autowired
    private ITransacaoService service;
    private PagamentoDTO pagamentoValido;
    private PagamentoDTO pagamentoSaldoInsuficiente;
    private PagamentoDTO pagamentoContaInvalida;

    private TransferenciaDTO transferenciaValida;
    private TransferenciaDTO transferenciaOrigemInvalida;
    private TransferenciaDTO transferenciaDestinoInvalido;
    private TransferenciaDTO transferenciaSaldoInsuficiente;

    private ExtratoDTO extratoValido;
    private ExtratoDTO extratoContaInvalida;
    private ExtratoDTO extratoDataInvalida;

    @BeforeEach
    public void setup(){
        pagamentoValido = new PagamentoDTO(10, LocalDateTime.parse("2024-09-11T21:00:00"), "123456", "Teste de pagamento", 100.0);
        pagamentoSaldoInsuficiente = new PagamentoDTO(10, LocalDateTime.parse("2024-09-11T21:00:00"), "123456", "Teste de pagamento", 10000.0);
        pagamentoContaInvalida = new PagamentoDTO(100, LocalDateTime.parse("2024-09-11T21:00:00"), "123456", "Teste de pagamento", 100.0);

        transferenciaValida = new TransferenciaDTO(10,11, LocalDateTime.parse("2024-09-11T21:00:00"), 100.00, "Teste" );
        transferenciaOrigemInvalida = new TransferenciaDTO(100,11, LocalDateTime.parse("2024-09-11T21:00:00"), 100.00, "Teste" );
        transferenciaDestinoInvalido = new TransferenciaDTO(10,200, LocalDateTime.parse("2024-09-11T21:00:00"), 100.00, "Teste" );
        transferenciaSaldoInsuficiente = new TransferenciaDTO(10,11, LocalDateTime.parse("2024-09-11T21:00:00"), 10000.00, "Teste" );

        extratoValido = new ExtratoDTO(10, LocalDateTime.parse("2024-08-10T21:00:00"), LocalDateTime.parse("2024-09-11T21:00:00"));
        extratoContaInvalida = new ExtratoDTO(100, LocalDateTime.parse("2024-08-10T21:00:00"), LocalDateTime.parse("2024-09-11T21:00:00"));
        extratoDataInvalida = new ExtratoDTO(10, LocalDateTime.parse("2024-09-11T21:00:00"), LocalDateTime.parse("2024-08-10T21:00:00"));
//        Mockito.when(service.efetuarPagamento(pagamentoSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);
//        Mockito.when(service.efetuarPagamento(pagamentoContaInvalida)).thenThrow(InvalidAccountException.class);
//        Mockito.when(service.efetuarPagamento(pagamentoValido)).thenReturn(true);
//
//        Mockito.when(service.efetuarTransferencia(transferenciaValida)).thenReturn(true);
//        Mockito.when(service.efetuarTransferencia(transferenciaOrigemInvalida)).thenThrow(InvalidAccountException.class);
//        Mockito.when(service.efetuarTransferencia(transferenciaDestinoInvalido)).thenThrow(InvalidAccountException.class);
//        Mockito.when(service.efetuarTransferencia(transferenciaSaldoInsuficiente)).thenThrow(NotEnoughBalanceException.class);

    }

    @Test
    public void deveriaEfetuarPagamento(){
        assertTrue(service.efetuarPagamento(pagamentoValido));
    }

    @Test
    public void deveriaLancarExeptionPorSaldoInsuficiente(){
        assertThrows(NotEnoughBalanceException.class, () -> service.efetuarPagamento(pagamentoSaldoInsuficiente));
    }

    @Test
    public void deveriaLancarExeptionPorContaInvalida(){
        assertThrows(InvalidAccountException.class, () -> service.efetuarPagamento(pagamentoContaInvalida));
    }

    @Test
    public void deveriaEfetuarTransferencia(){
        assertTrue(service.efetuarTransferencia(transferenciaValida));
    }

    @Test
    public void sholdCheckInvalidSourceAccount(){
        assertThrows(InvalidAccountException.class, () -> service.efetuarTransferencia(transferenciaOrigemInvalida));
    }

    @Test
    public void sholdCheckInvalidDestinationAccount(){
        assertThrows(InvalidAccountException.class, () -> service.efetuarTransferencia(transferenciaDestinoInvalido));
    }

    @Test
    public void sholdCheckAccountBallanceInsuficient(){
        assertThrows(NotEnoughBalanceException.class, () -> service.efetuarTransferencia(transferenciaSaldoInsuficiente));
    }

    @Test
    public void sholdRetrieveExtract(){
        assertNotNull(service.getExtrato(extratoValido));
    }

    @Test
    public void sholdCheckInvalidAccountNoExtract(){
        assertThrows(InvalidAccountException.class, () -> service.getExtrato(extratoContaInvalida));
    }

    @Test
    public void sholdCheckDateInterval(){
        assertThrows(InvalidDataIntervalException.class, () -> service.getExtrato(extratoDataInvalida));
    }
}
