package br.com.jean.portbank.cliente.service;

import br.com.jean.portbank.dto.ClienteDTO;
import br.com.jean.portbank.service.cliente.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ClienteServiceTest {

    private ClienteDTO reqValido;
    private ClienteDTO reqInvalido;
    private Integer idValido;

    @Mock
    private IClienteService service;

    @BeforeEach
    public void setUp(){
        reqInvalido = new ClienteDTO("Jean","jean@gmail","01234567890","51999999999","1234");
        reqInvalido = new ClienteDTO("Cliente Invalido", null, null, null, null);
        idValido = 1;

        Mockito.when(service.cadastrarCliente(reqValido)).thenReturn(idValido);
        Mockito.when(service.cadastrarCliente(reqInvalido)).thenReturn(null);
    }

    @Test
    void deveCadastrarClienteValido(){
        assertEquals(service.cadastrarCliente(reqValido), idValido);
    }

    @Test
    void deveRejeitarClienteInvalido(){
        assertNull(service.cadastrarCliente(reqInvalido));
    }
}
