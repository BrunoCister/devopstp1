package br.edu.infnet.DevOpsTP1.controller;

import br.edu.infnet.DevOpsTP1.application.FuncionarioService;
import br.edu.infnet.DevOpsTP1.domain.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody Funcionario funcionario) {

        Funcionario funcionarioCriado = new Funcionario();
        funcionarioCriado.setNome(funcionario.getNome());
        funcionarioCriado.setEmail(funcionario.getEmail());

        funcionarioService.save(funcionarioCriado);

        return new ResponseEntity<>(funcionarioCriado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {

        List<Funcionario> funcionarios = funcionarioService.findAll();

        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Funcionario> buscarFuncionario(@PathVariable UUID id) {

        Funcionario funcionario = funcionarioService.findById(id);

        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Funcionario> patchFuncionario(@PathVariable UUID id, @RequestBody Funcionario funcionario) {

        Funcionario funcionarioModificado = funcionarioService.patch(id, funcionario);

        return new ResponseEntity<>(funcionarioModificado, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Funcionario> deletarFuncionario(@PathVariable UUID id) {

        funcionarioService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
