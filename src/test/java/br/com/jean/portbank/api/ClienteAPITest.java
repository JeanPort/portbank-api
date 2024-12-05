package br.com.jean.portbank.api;

import br.com.jean.portbank.controller.ClienteController;
import br.com.jean.portbank.dto.ClienteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteAPITest {

    @Autowired
    private MockMvc mvc;


    private ClienteDTO reqValido;
    private ClienteDTO reqInvalido;
    private ClienteDTO reqEmailDuplicada;
    private ClienteDTO reqCpfDuplicada;
    private ClienteDTO reqTelefoneDuplicada;

    @BeforeEach
    public void setup() {

        reqValido = new ClienteDTO("clienteNovo", "clientenovo@gmail", "02999010095", "51999231639", "123456789");
        reqInvalido = new ClienteDTO("Cliente Invalido", null, null, null, null);
        reqEmailDuplicada = new ClienteDTO("Cliente email duplicado", "jean@gmail", "01199510011", "51111111111", "senha");
        reqCpfDuplicada = new ClienteDTO("Cliente cpf duplicado", "cpf@gmail.com", "01111111111", "52222222222", "senha123");
        reqTelefoneDuplicada = new ClienteDTO("Cliente telefone duplicado", "telefone@gmail.com", "03333333326", "51999999999", "senha55");
    }

    @Test
    public void sholdCallApiForValidCliente() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(reqValido);

        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void sholdNotCallApiForClienteEmailDuplicated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(reqEmailDuplicada);

        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void sholdNotCallApiForClienteCpfDuplicated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(reqCpfDuplicada);

        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void sholdNotCallApiForClienteTelefoneDuplicated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(reqTelefoneDuplicada);

        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void sholdNotCallApiForClienteInvalid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(reqInvalido);

        mvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }
}
