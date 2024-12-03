package br.com.jean.portbank.cliente.service;

import br.com.jean.portbank.dto.ClienteDTO;
import br.com.jean.portbank.service.cliente.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteServiceTest {

    private ClienteDTO reqValido;
    private ClienteDTO reqInvalido;
    private ClienteDTO reqEmailDuplicada;
    private ClienteDTO reqCpfDuplicada;
    private ClienteDTO reqTelefoneDuplicada;
    private Integer idValido;

    //@Mock
    @Autowired
    private IClienteService service;

    @BeforeEach
    public void setUp(){
        reqValido = new ClienteDTO("cliente","cliente@gmail","01234567890","51559999999","1234!");
        reqInvalido = new ClienteDTO("Cliente Invalido", null, null, null, null);
        reqEmailDuplicada = new ClienteDTO("Cliente email duplicado","jean@gmail", "01199510011", "51111111111", "senha");
        reqCpfDuplicada = new ClienteDTO("Cliente cpf duplicado","cpf@gmail.com", "01111111111", "52222222222", "senha123");
        reqTelefoneDuplicada = new ClienteDTO("Cliente telefone duplicado","telefone@gmail.com", "03333333326", "51999999999", "senha55");
        idValido = 1;
        /*
        Mockito.when(service.cadastrarCliente(reqValido)).thenReturn(idValido);
        Mockito.when(service.cadastrarCliente(reqInvalido)).thenThrow(new ConstraintViolationException(null));
        Mockito.when(service.cadastrarCliente(reqEmailDuplicada)).thenReturn(null);
        Mockito.when(service.cadastrarCliente(reqCpfDuplicada)).thenReturn(null);
        Mockito.when(service.cadastrarCliente(reqTelefoneDuplicada)).thenReturn(null);

        */
    }

    @Test
    void deveCadastrarClienteValido(){
        assertNotNull(service.cadastrarCliente(reqValido));
    }

    @Test
    void deveRejeitarClienteInvalido(){
        assertThrows(DataIntegrityViolationException.class, () -> service.cadastrarCliente(reqInvalido));
    }

    @Test
    void deveRetornarNullEmailDuplicado() {
        assertNull(service.cadastrarCliente(reqEmailDuplicada));
    }

    @Test
    void deveRetornarNullCpfDuplicado(){
        assertNull(service.cadastrarCliente(reqCpfDuplicada));
    }

    @Test
    void deveRetornarNullTelefoneDuplicado(){
        assertNull(service.cadastrarCliente(reqTelefoneDuplicada));
    }
}
