package br.com.jean.portbank.api;

import br.com.jean.portbank.dto.ExtratoDTO;
import br.com.jean.portbank.dto.PagamentoDTO;
import br.com.jean.portbank.dto.TransferenciaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoAPITest {

    @Autowired
    private MockMvc mvc;

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
    public void setup() {
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
    }

    @Test
    public void sholdPerformPayment() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String str = mapper.writeValueAsString(pagamentoValido);

        mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void sholdInvalidPaymentDueBalance()throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String str = mapper.writeValueAsString(pagamentoSaldoInsuficiente);

        mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void sholdInvalidPaymentDueDestinationAccount()throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String str = mapper.writeValueAsString(pagamentoContaInvalida);

        mvc.perform(MockMvcRequestBuilders.post("/pagamentos")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }
}
