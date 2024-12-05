package br.com.jean.portbank.api;

import br.com.jean.portbank.dto.ContaDTO;
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
public class ContaAPITest {

    @Autowired
    private MockMvc mvc;

    private ContaDTO contaValida;
    private ContaDTO contaInvalida;

    @BeforeEach
    public void setUp(){
        contaValida = new ContaDTO(1, 1, 100.0, 0.0, 10);
        contaInvalida = new ContaDTO(1, 1, 100.0, 0.0, 200);

    }

    @Test
    public void sholdCallApiToCreateConta() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(contaValida);
        mvc.perform(MockMvcRequestBuilders.post("/contas")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void sholdNotCallApiToCreateConta() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(contaInvalida);
        mvc.perform(MockMvcRequestBuilders.post("/contas")
                .contentType("application/json")
                .content(str)).andExpect(MockMvcResultMatchers.status().is(400));
    }
}
