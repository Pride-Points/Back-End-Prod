package com.pridepoints.api.dto.Usuario.Fisica;

import com.pridepoints.api.dto.Autenticacao.UsuarioTokenDTO;
import com.pridepoints.api.dto.Avaliacao.AvaliacaoMapper;
import com.pridepoints.api.entities.Fisica;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FisicaMapper {

    public static Fisica of (FisicaCriacaoDTO fisicaCriacaoDTO){
        Fisica usuario = new Fisica();

        usuario.setNome(fisicaCriacaoDTO.getNome());
        usuario.setSenha(fisicaCriacaoDTO.getSenha());
        usuario.setEmail(fisicaCriacaoDTO.getEmail());
        usuario.setOrientacaoSexual(fisicaCriacaoDTO.getOrientacaoSexual());
        usuario.setCpf(fisicaCriacaoDTO.getCpf());
        usuario.setGenero(fisicaCriacaoDTO.getGenero());
        usuario.setDtNascimento(fisicaCriacaoDTO.getDtNascimento());

        return usuario;
    }

    public static FisicaFullDTO of(Fisica fisica){
        FisicaFullDTO usuario = new FisicaFullDTO();

        usuario.setId(fisica.getId());
        usuario.setNome(fisica.getNome());
        usuario.setEmail(fisica.getEmail());
        usuario.setOrientacaoSexual(fisica.getOrientacaoSexual());
        usuario.setGenero(fisica.getGenero());
        usuario.setDtNascimento(fisica.getDtNascimento());
        if(fisica.getAvaliacoesUsuario() == null) {
            usuario.setAvaliacoes(Collections.emptyList());
        } else {
            usuario.setAvaliacoes(fisica.getAvaliacoesUsuario().stream()
                    .map(AvaliacaoMapper::of)
                    .collect(Collectors.toList()));
        }
        usuario.setUltimaTrocaSenha(fisica.getUltimaTrocaSenha());
        return usuario;
    }

    public static FisicaMinDTO ofMin(Fisica fisica){
        FisicaMinDTO usuario = new FisicaMinDTO();

        usuario.setId(fisica.getId());
        usuario.setNome(fisica.getNome());
        usuario.setEmail(fisica.getEmail());

        return usuario;
    }

    public static List<FisicaMinDTO> ofListMin(List<Fisica> usuarios){

        List<FisicaMinDTO> usuariosDTO = usuarios.stream()
                .map(FisicaMapper::ofMin)
                .collect(Collectors.toList());

        return usuariosDTO;
    }
    public static List<FisicaFullDTO> ofListFull(List<Fisica> usuarios){

        List<FisicaFullDTO> usuariosDTO = usuarios.stream()
                .map(FisicaMapper::of)
                .collect(Collectors.toList());

            return usuariosDTO;
    }

    public static UsuarioTokenDTO of(Fisica fisica, String token){
        UsuarioTokenDTO usuarioTokenDTO = new UsuarioTokenDTO();
        usuarioTokenDTO.setUserId(fisica.getId());
        usuarioTokenDTO.setEmail(fisica.getEmail());
        usuarioTokenDTO.setNome(fisica.getNome());
        usuarioTokenDTO.setToken(token);

        return usuarioTokenDTO;
    }
}
