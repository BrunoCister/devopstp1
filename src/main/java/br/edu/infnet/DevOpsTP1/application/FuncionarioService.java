package br.edu.infnet.DevOpsTP1.application;

import br.edu.infnet.DevOpsTP1.domain.Funcionario;
import br.edu.infnet.DevOpsTP1.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(UUID id) {
        return funcionarioRepository.findById(id).get();
    }

    public Funcionario patch(UUID id, Funcionario funcionario) {

        Funcionario result = funcionarioRepository.findById(id).get();
        result.setNome(funcionario.getNome());
        result.setEmail(funcionario.getEmail());

        return result;
    }

    public void delete(UUID id) {
        funcionarioRepository.deleteById(id);
    }
}
