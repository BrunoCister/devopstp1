package br.edu.infnet.DevOpsTP1.service;

import br.edu.infnet.DevOpsTP1.application.FuncionarioService;
import br.edu.infnet.DevOpsTP1.domain.Funcionario;
import br.edu.infnet.DevOpsTP1.repository.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Test
    public void deve_buscar_todos_os_funcionarios() {

        Funcionario funcionario = new Funcionario();

        funcionario.setId(UUID.randomUUID());
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);

        given(this.funcionarioRepository.findAll()).willReturn(funcionarios);

        List<Funcionario> expected = this.funcionarioService.findAll();
        Assertions.assertArrayEquals(funcionarios.toArray(), expected.toArray());
    }

    @Test
    public void deve_buscar_funcionario_por_id() {

        UUID idFuncionario = UUID.randomUUID();

        Funcionario funcionario = new Funcionario();

        funcionario.setId(idFuncionario);
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        Optional<Funcionario> optionalFuncionario = Optional.of(funcionario);
        given(this.funcionarioRepository.findById(idFuncionario)).willReturn(optionalFuncionario);

        Optional<Funcionario> funcionarioExpected = this.funcionarioService.findById(idFuncionario);
        Assertions.assertTrue(funcionarioExpected.isPresent());
        Assertions.assertSame(funcionarioExpected, optionalFuncionario);
    }

    @Test
    public void should_create_band_success() {

        UUID idFuncionario = UUID.randomUUID();

        Funcionario funcionario = new Funcionario();

        funcionario.setId(idFuncionario);
        funcionario.setNome("Nome teste");
        funcionario.setEmail("email@email.com");

        this.funcionarioService.save(funcionario);

        verify(this.funcionarioRepository, times(1)).save(funcionario);

    }
}
