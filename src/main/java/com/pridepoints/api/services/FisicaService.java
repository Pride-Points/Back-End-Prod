package com.pridepoints.api.services;

import com.pridepoints.api.dto.Autenticacao.UserDTO;
import com.pridepoints.api.dto.Autenticacao.UsuarioTokenDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaCriacaoDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaFullDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaMapper;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaMinDTO;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.repositories.EmpresaRepository;
import com.pridepoints.api.repositories.FisicaRepository;
import com.pridepoints.api.utilities.interfaces.iValidarTrocaDeSenha;
import com.pridepoints.api.utilities.security.GerenciadorTokenJwt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pridepoints.api.dto.Usuario.Fisica.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FisicaService implements iValidarTrocaDeSenha {


    private final FisicaRepository fisicaRepository;

    private final PasswordEncoder passwordEncoder;

    private final GerenciadorTokenJwt gerenciadorTokenJwt;


    private final AuthenticationManager authenticationManager;

    public FisicaService(FisicaRepository fisicaRepository,
                         PasswordEncoder passwordEncoder,
                         GerenciadorTokenJwt gerenciadorTokenJwt,
                         AuthenticationManager authenticationManager) {
        this.fisicaRepository = fisicaRepository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public FisicaFullDTO cadastrarUsuario(FisicaCriacaoDTO f){
        boolean consultaBanco = fisicaRepository.existsByEmail(f.getEmail());
        if(!consultaBanco){
            final Fisica novoUsuario = FisicaMapper.of(f);

            String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
            novoUsuario.setSenha(senhaCriptografada);

            Fisica result = fisicaRepository.save(novoUsuario);

            return FisicaMapper.of(result);
        }
            return null;
    }

    @Transactional
    public UsuarioTokenDTO autenticarFisica(UserDTO f){
        final UsernamePasswordAuthenticationToken credenciais =
                new UsernamePasswordAuthenticationToken(f.getEmail(),f.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credenciais);

        Optional<Fisica> fisicaAutenticada = fisicaRepository.findByEmail(f.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return fisicaAutenticada.map(fisica -> FisicaMapper.of(fisica, token)).orElse(null);
    }

    @Transactional
    public List<FisicaMinDTO> listarPessoasFisicas() {

        List<Fisica> pessoasFisicasList = fisicaRepository.findAll();

        return FisicaMapper.ofListMin(pessoasFisicasList);
    }

    @Transactional
    public FisicaFullDTO atualizarEmail(FisicaCriacaoDTO obj, Long id){
        Optional<Fisica> result = fisicaRepository.findById(id);

        if(result.isPresent()){
            Fisica usuarioBanco = result.get();
            usuarioBanco.setEmail(obj.getEmail());
            fisicaRepository.save(usuarioBanco);

            return FisicaMapper.of(usuarioBanco);
        }
            return null;
    }

    @Transactional
    public boolean removerPessoaFisica(Long id){

        if(fisicaRepository.existsById(id)){
            fisicaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void validatePasswordChange() {
        LocalDate quatroMesesAtras = LocalDate.now().minusMonths(4);
        List<Fisica> pessoasFisica = fisicaRepository.findAll();

        for (Fisica pessoaFisica: pessoasFisica){

            if(pessoaFisica.getUltimaTrocaSenha().isBefore(quatroMesesAtras));
                pessoaFisica.setForcarTrocaDeSenha(true);
                    fisicaRepository.save(pessoaFisica);
        }
    }

    public FisicaImgDTO salvarImg(Long idUser, FisicaImgDTO f) {
        Optional<Fisica> fisicaOpt = fisicaRepository.findById(idUser);

        if(fisicaOpt.isPresent()){
            Fisica userBanco = fisicaOpt.get();
            userBanco.setImgUser(f.getImgUser());

            fisicaRepository.save(userBanco);

            return f;
        }

        return null;
    }

    public FisicaImgDTO buscarImgUser(Long idUser) {
        Optional<Fisica> fisicaOpt = fisicaRepository.findById(idUser);

        if(fisicaOpt.isPresent()){
            Fisica userBanco = fisicaOpt.get();

            return new FisicaImgDTO(userBanco.getImgUser());
        }

        return null;
    }


    public boolean findUser(UserDTO usuario) {
        boolean exists = fisicaRepository.existsByEmail(usuario.getEmail());
        if(exists){
            return true;
        }
        return false;
    }
}
