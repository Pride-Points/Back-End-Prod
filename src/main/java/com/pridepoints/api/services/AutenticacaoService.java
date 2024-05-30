package com.pridepoints.api.services;

import com.pridepoints.api.dto.Autenticacao.FuncionarioDetalhesDTO;
import com.pridepoints.api.dto.Autenticacao.PessoaFisicaDetalhesDTO;
import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.repositories.FisicaRepository;
import com.pridepoints.api.repositories.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AutenticacaoService implements UserDetailsService {

    private final FisicaRepository fisicaRepository;
    private final FuncionarioRepository funcionarioRepository;
    public AutenticacaoService(FisicaRepository fisicaRepository, FuncionarioRepository funcionarioRepository){
        this.fisicaRepository = fisicaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    System.out.println("Procurando Funcionário com email: " + email);
    Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
    if (funcionario.isPresent()) {
        return new FuncionarioDetalhesDTO(funcionario.get());
    }

    System.out.println("Procurando Física com email: " + email);
    Optional<Fisica> fisica = fisicaRepository.findByEmail(email);
    if (fisica.isPresent()) {
        return new PessoaFisicaDetalhesDTO(fisica.get());
    }

    throw new UsernameNotFoundException(String.format("Usuário com email: %s não encontrado", email));
}

}
