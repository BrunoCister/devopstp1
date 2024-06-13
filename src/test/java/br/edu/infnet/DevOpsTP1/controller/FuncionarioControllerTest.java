package br.edu.infnet.DevOpsTP1.controller;

import br.edu.infnet.DevOpsTP1.application.FuncionarioService;
import br.edu.infnet.DevOpsTP1.domain.Funcionario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@AutoConfigureMockMvc
@WebMvcTest(controllers = FuncionarioController.class)
public class FuncionarioControllerTest {

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Test
    public void deve_buscar_todos_os_funcionarios() throws Exception {

        Funcionario funcionario = new Funcionario();

        funcionario.setId(UUID.randomUUID());
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);

        given(this.funcionarioService.findAll()).willReturn(funcionarios);

        mvc.perform(get("/funcionarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$[0].id").value(funcionario.getId().toString()));
    }

    @Test
    public void deve_buscar_funcionario_por_id() throws Exception {

        UUID idFuncionario = UUID.randomUUID();

        Funcionario funcionario = new Funcionario();

        funcionario.setId(idFuncionario);
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        Optional<Funcionario> optionalFuncionario = Optional.of(funcionario);

        given(this.funcionarioService.findById(idFuncionario)).willReturn(optionalFuncionario);

        mvc.perform(get("/funcionarios/" + idFuncionario)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.id").value(funcionario.getId().toString()));
    }

    @Test
    public void deve_buscar_funcionario_por_id_not_found() throws Exception {

        UUID idFuncionario = UUID.randomUUID();

        Funcionario funcionario = new Funcionario();

        funcionario.setId(idFuncionario);
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        given(this.funcionarioService.findById(idFuncionario)).willReturn(Optional.empty());

        mvc.perform(get("/funcionario/" + idFuncionario)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    public void deve_criar_funcionario() throws Exception {

        Funcionario funcionario = new Funcionario();

        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        mvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.email").value(funcionario.getEmail()));
    }
}
