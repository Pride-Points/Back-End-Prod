package com.pridepoints.api.services;

import com.pridepoints.api.dto.Empresa.*;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioMapper;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.repositories.EmpresaRepository;
import com.pridepoints.api.repositories.FuncionarioRepository;
import com.pridepoints.api.utilities.lista.ListaObj;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    EmpresaService {

    private final EmpresaRepository empresaRepository;

    private final FuncionarioRepository funcionarioRepository;

    public EmpresaService(EmpresaRepository empresaRepository,
                          FuncionarioRepository funcionarioRepository){
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    @Transactional
    public EmpresaFullDTO cadastrarEmpresa(Empresa e){
        boolean existEmp = empresaRepository.existsByCnpj(e.getCnpj());
        boolean existsFunc = funcionarioRepository.existsByEmail(e.getFuncionarios().get(0).getEmail());

        if(!existEmp && !existsFunc){
            Empresa result = empresaRepository.save(e);

            return EmpresaMapper.of(result);
        }
        return null;
    }


    @Transactional
    public List<EmpresaMinDTO> listarEmpresas() {
        List<Empresa> empresaList = empresaRepository.findAll();
        return EmpresaMapper.ofListMin(empresaList);
    }



    public EmpresaFullDTO buscarPorId(Long id) {

        Optional<Empresa> result = empresaRepository.findById(id);

        return result.map(EmpresaMapper::of).orElse(null);
    }

    public Empresa buscarPorIdTxt(Long id) {

        Optional<Empresa> result = empresaRepository.findById(id);

        return result.get();
    }


    public EmpresaFullDTO atualizarEmpresa(EmpresaCriacaoDTO novosDados, Long idEmpresa) {
        Optional<Empresa> result = empresaRepository.findById(idEmpresa);

        if(result.isPresent()){
            Empresa empresaAtualizada = EmpresaMapper.of(novosDados);

            return EmpresaMapper.of(empresaRepository.save(empresaAtualizada));
        }
        return null;
    }

    @Transactional
    public Long procurarPorCnpj(String cnpj) {

        Optional<Empresa> result = empresaRepository.findByCnpj(cnpj);

        if(result.isPresent()){
            return EmpresaMapper.of(result.get()).getId();
        } else {

            return null;
        }
    }

    public boolean deletarEmpresa(Long idEmpresa) {
        boolean exists = empresaRepository.existsById(idEmpresa);

        if(exists){
            empresaRepository.deleteById(idEmpresa);
            return true;
        }
        return false;
    }

    @Transactional
    public ListaObj<FuncionarioFullDTO> getFuncionariosDaEmpresa(String cnpj) {
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            List<Funcionario> funcionariosDaEmpresa = empresa.getFuncionarios();
            ListaObj<FuncionarioFullDTO> listaFuncionarios = new ListaObj<>(funcionariosDaEmpresa.size());
            List<FuncionarioFullDTO> funcionarios = FuncionarioMapper.of(funcionariosDaEmpresa);
                for (FuncionarioFullDTO funcionario : funcionarios) {
                listaFuncionarios.adiciona(funcionario);
            }

            return listaFuncionarios;
        } else {
            return null;
        }
    }
    @Transactional
    public List<Funcionario> getFuncionariosDaEmpresaa(String cnpj) {
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);

        if (empresaOptional.isPresent()) {
            Empresa empresa = empresaOptional.get();
            List<Funcionario> funcionariosDaEmpresa = empresa.getFuncionarios();
            return funcionariosDaEmpresa;
        } else {
            return null;
        }
    }


    public Optional<Double> calcularMediaAvaliacoes(Long empresaId) {
        return empresaRepository.calcularMediaAvaliacoes(empresaId);
    }

    public List<EmpresaFullDTO> listarEmpresasCompleto() {
        List<Empresa> empresaList = empresaRepository.findAll();
        return EmpresaMapper.ofListMax(empresaList);
    }

}
