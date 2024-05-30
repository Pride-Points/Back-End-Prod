package com.pridepoints.api.controller;


import com.pridepoints.api.dto.Empresa.*;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioMapper;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.services.EmpresaService;
import com.pridepoints.api.services.FuncionarioService;
import com.pridepoints.api.utilities.multiclasse.EmpresaDonoRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final FuncionarioService funcionarioService;

    public EmpresaController(EmpresaService empresaService,
                             PasswordEncoder passwordEncoder, FuncionarioService funcionarioService) {
        this.empresaService = empresaService;
        this.passwordEncoder = passwordEncoder;
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<EmpresaFullDTO> cadastrarEmpresa(@Valid @RequestBody EmpresaDonoRequest request) {
        Empresa empresa = EmpresaMapper.of(request.getEmpresa());
        String senhaCriptografada = passwordEncoder.encode(request.getFuncionario().getSenha());
        request.getFuncionario().setSenha(senhaCriptografada);
        Funcionario dono = FuncionarioMapper.of(request.getFuncionario());
        dono.setEmpresa(empresa);
        empresa.adicionarFuncionario(dono);
        EmpresaFullDTO result = empresaService.cadastrarEmpresa(empresa);
        if (result != null) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(409).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpresaMinDTO>> listarEmpresas() {
        List<EmpresaMinDTO> listaDeEmpresas = empresaService.listarEmpresas();
        if (listaDeEmpresas == null || listaDeEmpresas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaDeEmpresas);
    }
    @GetMapping("/completo")
    public ResponseEntity<List<EmpresaFullDTO>> listarEmpresasCompleto() {
        List<EmpresaFullDTO> listaDeEmpresas = empresaService.listarEmpresasCompleto();
        if (listaDeEmpresas == null || listaDeEmpresas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listaDeEmpresas);
    }
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaFullDTO> buscarPorId(@PathVariable Long id) {
        EmpresaFullDTO result = empresaService.buscarPorId(id);

        if (result == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{idEmpresa}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmpresaFullDTO> atualizarEmpresa(@Valid @RequestBody EmpresaCriacaoDTO
                                                                   novosDados,
                                                           @PathVariable Long idEmpresa) {
        EmpresaFullDTO result = empresaService.atualizarEmpresa(novosDados, idEmpresa);

        if (result == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(result);
        }
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> removerEmpresa(@PathVariable Long idEmpresa) {

        boolean removeu = empresaService.deletarEmpresa(idEmpresa);

        if (removeu) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/funcionarios-por-cnpj")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<FuncionarioFullDTO>> getFuncionariosDaEmpresa(@RequestParam String cnpj) {
        Long empresaId = empresaService.procurarPorCnpj(cnpj);

        if (empresaId != null) {
            List<FuncionarioFullDTO> funcionarios = funcionarioService.listarFuncionarioPeloIdEmpresa(empresaId);
            return ResponseEntity.ok(funcionarios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/media/{id}")
    public ResponseEntity<Double> obterMedia(@PathVariable Long id) {
        Optional<Double> mediaAvaliacoesOptional = empresaService.calcularMediaAvaliacoes(id);

        if (mediaAvaliacoesOptional.isPresent()) {
            Double mediaAvaliacoes = mediaAvaliacoesOptional.get();
            return ResponseEntity.ok(mediaAvaliacoes);
        } else {
            Double avaliacaoZerada = 0.0;
            return ResponseEntity.ok(avaliacaoZerada);
        }
    }


}
