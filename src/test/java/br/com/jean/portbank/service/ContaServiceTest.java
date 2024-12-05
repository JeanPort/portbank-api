package br.com.jean.portbank.service;

import br.com.jean.portbank.dto.ContaDTO;
import br.com.jean.portbank.service.conta.IContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContaServiceTest {

    //@Mock
    @Autowired
    private IContaService service;

    private ContaDTO contaValida;
    private ContaDTO contaInvalida;

    @BeforeEach
    public void setUp(){
        contaValida = new ContaDTO(1, 1, 100.0, 0.0, 10);
        contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 20);
        /*
        Mockito.when(service.cadastrarNovaConta(contaValida)).thenReturn(1);
        Mockito.when(service.cadastrarNovaConta(contaInvalida)).thenReturn(null);

         */
    }

    @Test
    public void deveriaAceitarContaComClienteExistente(){
        assertNotNull(service.cadastrarNovaConta(contaValida));
    }

    @Test
    public void deveriaRetornarNullComClienteInexistente(){
        assertNull(service.cadastrarNovaConta(contaInvalida));
    }
}
