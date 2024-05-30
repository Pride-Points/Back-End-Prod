package com.pridepoints.api.dto.Empresa;

import com.pridepoints.api.entities.Empresa;

import java.util.List;
import java.util.stream.Collectors;

public class EmpresaMapper {

    public static Empresa of(EmpresaCriacaoDTO empresaCriacaoDTO){
        Empresa empresa = new Empresa();

        empresa.setNomeFantasia(empresaCriacaoDTO.getNomeFantasia());
        empresa.setCnpj(empresaCriacaoDTO.getCnpj());
        empresa.setCep(empresaCriacaoDTO.getCep());
        empresa.setNumero(empresaCriacaoDTO.getNumero());
        empresa.setCidade(empresaCriacaoDTO.getCidade());
        empresa.setEstado(empresaCriacaoDTO.getEstado());

        return empresa;
    }

    public static EmpresaFullDTO of(Empresa empresa){
        EmpresaFullDTO empresaFullDTO = new EmpresaFullDTO();

        empresaFullDTO.setId(empresa.getId());
        empresaFullDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaFullDTO.setCnpj(empresa.getCnpj());
        empresaFullDTO.setCep(empresa.getCep());
        empresaFullDTO.setNumero(empresa.getNumero());
        empresaFullDTO.setCidade(empresa.getCidade());
        empresaFullDTO.setEstado(empresa.getCidade());
        empresaFullDTO.setDono(empresa.getFuncionarios().get(0).getNome());

        return empresaFullDTO;
    }

    public static EmpresaMinDTO ofMin(Empresa empresa){
        EmpresaMinDTO empresaMinDTO = new EmpresaMinDTO();

        empresaMinDTO.setId(empresa.getId());
        empresaMinDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaMinDTO.setCidade(empresa.getCidade());
        empresaMinDTO.setEstado(empresa.getEstado());
        empresaMinDTO.setDono(empresa.getFuncionarios().get(0).getNome());

        return empresaMinDTO;
    }

    public static List<EmpresaMinDTO> ofListMin(List<Empresa> empresas){

        List<EmpresaMinDTO> empresasMinDTO = empresas.stream()
                .map(EmpresaMapper::ofMin)
                .collect(Collectors.toList());

        return empresasMinDTO;
    }

    public static List<EmpresaFullDTO> ofListMax(List<Empresa> empresas){

        List<EmpresaFullDTO> empresasFullDTO = empresas.stream()
                .map(EmpresaMapper::of)
                .collect(Collectors.toList());

        return empresasFullDTO;
    }

    public static EmpresaMediaDTO ofMedia(Empresa empresa){
        EmpresaMediaDTO empresaMediaDTO = new EmpresaMediaDTO();

        empresaMediaDTO.setId(empresa.getId());
        empresaMediaDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaMediaDTO.setCidade(empresa.getCidade());
        empresaMediaDTO.setEstado(empresa.getEstado());
        empresaMediaDTO.setDono(empresa.getFuncionarios().get(0).getNome());

        return empresaMediaDTO;
    }
    public static List<EmpresaFullDTO> ofListMax(List<Empresa> empresas){

        List<EmpresaFullDTO> empresasFullDTO = empresas.stream()
                .map(EmpresaMapper::of)
                .collect(Collectors.toList());

        return empresasFullDTO;
    }
}
