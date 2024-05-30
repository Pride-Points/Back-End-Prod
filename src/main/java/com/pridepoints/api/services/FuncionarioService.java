package com.pridepoints.api.services;

import com.pridepoints.api.dto.Autenticacao.UserDTO;
import com.pridepoints.api.dto.Autenticacao.UsuarioTokenDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioCriacaoDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioMapper;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioUpdateDTO;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.repositories.EmpresaRepository;
import com.pridepoints.api.repositories.FuncionarioRepository;
import com.pridepoints.api.utilities.interfaces.iValidarTrocaDeSenha;
import com.pridepoints.api.utilities.lista.ListaObj;
import com.pridepoints.api.utilities.ordenacao.Ordenacao;
import com.pridepoints.api.utilities.security.GerenciadorTokenJwt;
import com.pridepoints.api.utilities.pesquisaBinaria.PesquisaBinaria;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService implements iValidarTrocaDeSenha {


    private final FuncionarioRepository funcionarioRepository;

    private final EmpresaRepository empresaRepository;


    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              EmpresaRepository empresaRepository,
                              EmailService emailService,
                              AuthenticationManager authenticationManager,
                              PasswordEncoder passwordEncoder,
                              GerenciadorTokenJwt gerenciadorTokenJwt){
        this.funcionarioRepository = funcionarioRepository;
        this.empresaRepository = empresaRepository;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    @Transactional
    public FuncionarioFullDTO cadastrarFuncionario(FuncionarioCriacaoDTO funcionario, Long idEmpresa){

        boolean existsByEmail = funcionarioRepository.existsByEmail(funcionario.getEmail());
        Optional<Empresa> consultaBancoEmpresa = empresaRepository.findById(idEmpresa);

        if(!existsByEmail && consultaBancoEmpresa.isPresent()){
            String senhaCriptografada = passwordEncoder.encode(funcionario.getSenha());
            funcionario.setSenha(senhaCriptografada);
            Funcionario funcionarioMapeado = FuncionarioMapper.of(funcionario);
            funcionarioMapeado.setEmpresa(consultaBancoEmpresa.get());

           Funcionario result = funcionarioRepository.save(funcionarioMapeado);
            return FuncionarioMapper.ofFull(result);
        }
        return null;
    }

    @Override
    @Scheduled(cron = "0 0 0 1 */2 ?") // Agendar a cada 2 meses
    public void validatePasswordChange() throws AddressException {
            LocalDate doisMesesAtras = LocalDate.now().minusMonths(2);
            List<Funcionario> funcionarios = funcionarioRepository.findAll();

            for (Funcionario f: funcionarios){
                if(f.getUltimaTrocaSenha() == null || f.getUltimaTrocaSenha().isBefore(doisMesesAtras)){
                    emailService.enviarNotificacaoAlterarSenha(f);
                }
            }
        }

    @Transactional
    public List<FuncionarioFullDTO> listarFuncionarios() {

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();

            return FuncionarioMapper.of(funcionarioList);
    }


    @Transactional
    public List<FuncionarioFullDTO> listarFuncionariosAtivos(Long idEmpresa) {
        List<Funcionario> ativosList = funcionarioRepository.findByEmpresaIdAndIsAtivoTrue(idEmpresa);
        return FuncionarioMapper.of(ativosList);
    }

    @Transactional
    public List<FuncionarioFullDTO> listarFuncionariosInativos(Long idEmpresa) {
        List<Funcionario> ativosList = funcionarioRepository.findByEmpresaAndIsAtivoFalse(idEmpresa);

        if(ativosList.isEmpty()){
            return null;
        }

        return FuncionarioMapper.of(ativosList);
    }
    @Transactional
    public FuncionarioFullDTO listarPorId(Long idFunc) {
        Optional<Funcionario> func = funcionarioRepository.findById(idFunc);

        if(func.isPresent()){
            return FuncionarioMapper.ofFull(func.get());
        }
        return null;
    }

    @Transactional
    public List<FuncionarioFullDTO> listarFuncionariosOrdenadosPorNome() {
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();

        Ordenacao ordenacaoNome = new Ordenacao();
        List<Funcionario> funcionarioOrdenado = ordenacaoNome.ordenaAlfabeticamente(funcionarioList);

        return FuncionarioMapper.of(funcionarioOrdenado);
    }

    @Transactional
    public List<FuncionarioFullDTO> listarFuncionariosOrdenadosPorCPF() {
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        Ordenacao ordenacao = new Ordenacao();
        ordenacao.ordenaPorCPF(funcionarioList);

        return FuncionarioMapper.of(funcionarioList);
    }

    @Transactional
    public FuncionarioFullDTO buscarFuncionarioPorCPF(String cpf) {
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        PesquisaBinaria pesquisaBinaria = new PesquisaBinaria();
        Ordenacao ordenacao = new Ordenacao();
        ordenacao.ordenaPorCPF(funcionarioList);

        int indiceFuncionario = pesquisaBinaria.pesquisaBinariaPorCPF(funcionarioList, cpf);
       
        if (indiceFuncionario != -1) {
            Funcionario funcionario =   funcionarioList.get(indiceFuncionario);
            return FuncionarioMapper.ofFull(funcionario);
        } else {
            return null; // ou outra resposta apropriada
        }
    }

    public boolean findUser(UserDTO usuario) {
        boolean exists = funcionarioRepository.existsByEmailAndIsAtivoTrue(usuario.getEmail());
        if(exists){
            return true;
        }
        return false;
    }

    @Transactional
    public UsuarioTokenDTO autenticarFuncionario(UserDTO usuario) {
        final UsernamePasswordAuthenticationToken credenciais =
                new UsernamePasswordAuthenticationToken(usuario.getEmail(),usuario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credenciais);

        Optional<Funcionario> funcOpt = funcionarioRepository.findByEmail(usuario.getEmail());
        Optional<Empresa> empresa = empresaRepository.findByFuncionariosEmail(usuario.getEmail());
        final Long idEmpresa = empresa.get().getId();
        final String cnpj = empresa.get().getCnpj();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return funcOpt.map(funcionario -> FuncionarioMapper.of(funcionario, idEmpresa, cnpj, token)).orElse(null);
    }


    public boolean deletarFunc(Long idEmpresa, Long idFunc) {
        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        if(empresa.isPresent()){
            List<Funcionario> funcionarios = empresa.get().getFuncionarios();

            for (Funcionario func : funcionarios) {
                if(Objects.equals(func.getId(), idFunc)){
                    func.setIsAtivo(false);
                    funcionarioRepository.save(func);
                    return true;
                }
            }
        }
        return false;
    }

    @Transactional
    public FuncionarioFullDTO encontrarFuncionarioPorCpf(String cpf) {
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        PesquisaBinaria pesquisaBinaria = new PesquisaBinaria();
        Funcionario funcionarioEncontrado = pesquisaBinaria.binarySearch(funcionarios, cpf);

        if (funcionarioEncontrado != null) {
            return FuncionarioMapper.ofFull(funcionarioEncontrado);
        }

        return null;
    }

    @Transactional
    public List<FuncionarioFullDTO> salvarFuncionarios(List<Funcionario> listaLida) {
      List<Funcionario> funcionariosSalvos = funcionarioRepository.saveAll(listaLida);

      return FuncionarioMapper.of(funcionariosSalvos);
    }
    @Transactional
    public List<FuncionarioFullDTO> listarFuncionarioPeloIdEmpresa(Long id) {
        List<Funcionario> funcionarioList = funcionarioRepository.findByEmpresa_Id(id);
        return FuncionarioMapper.of(funcionarioList);
    }

    public FuncionarioUpdateDTO updateFuncionario(Long id, FuncionarioCriacaoDTO funcionarioRequest) {
        Optional<Funcionario> funcOpt = funcionarioRepository.findById(id);

        if(funcOpt.isPresent()){
            Funcionario funcionario = funcOpt.get();

            funcionario.setNome(funcionarioRequest.getNome());
            funcionario.setCargo(funcionarioRequest.getCargo());
            funcionario.setEmail(funcionarioRequest.getEmail());

            return FuncionarioMapper.of(funcionarioRepository.save(funcionario));

        }

        return null;
    }
}
