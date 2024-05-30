package com.pridepoints.api.dto.Avaliacao;

import com.pridepoints.api.entities.Avaliacao;
import com.pridepoints.api.entities.Fisica;

import java.util.List;
import java.util.stream.Collectors;

public class AvaliacaoMapper {

    public static List<AvaliacaoDTO> of(List<Avaliacao> avaliacoes){

        List<AvaliacaoDTO> avaliacoesDTO;

        avaliacoesDTO = avaliacoes.stream()
                .map(AvaliacaoMapper::of)
                .collect(Collectors.toList());

        return avaliacoesDTO;
    }

    public static Avaliacao of(AvaliacaoCriacaoDTO avaliacaoCriacaoDTO, Fisica fisica){
        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setNota(avaliacaoCriacaoDTO.getNota());
        avaliacao.setTag(avaliacaoCriacaoDTO.getTag());
        avaliacao.setComentario(avaliacaoCriacaoDTO.getComentario());
        avaliacao.setShared(avaliacaoCriacaoDTO.isShared());
        avaliacao.setNomeAvaliador(fisica.getNome());
        avaliacao.setResp(avaliacao.getResp());
        return avaliacao;
    }

    public static AvaliacaoDTO of(Avaliacao avaliacao){
        AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();

        avaliacaoDTO.setId(avaliacao.getId());
        avaliacaoDTO.setNota(avaliacao.getNota());
        avaliacaoDTO.setDtAvaliacao(avaliacao.getDtAvaliacao());
        avaliacaoDTO.setTag(avaliacao.getTag());
        avaliacaoDTO.setResp(avaliacao.getResp());
        avaliacaoDTO.setShared(avaliacao.isShared());
        avaliacaoDTO.setTitle(avaliacao.getTitle());
        avaliacaoDTO.setNomeAvaliador(avaliacao.getNomeAvaliador());
        avaliacaoDTO.setComentario(avaliacao.getComentario());

        return avaliacaoDTO;
    }

    public static AvaliacaoRespostaEmpresaDTO ofRespostaEmpresa(Avaliacao avaliacao){
        AvaliacaoRespostaEmpresaDTO avaliacaoRespostaEmpresaDTO = new AvaliacaoRespostaEmpresaDTO();

        avaliacaoRespostaEmpresaDTO.setResp(avaliacao.getResp());
        avaliacaoRespostaEmpresaDTO.setTitle(avaliacao.getTitle());

        return avaliacaoRespostaEmpresaDTO;
    }

}
