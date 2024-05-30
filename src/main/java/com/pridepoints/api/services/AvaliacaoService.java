package com.pridepoints.api.services;

import com.pridepoints.api.dto.Avaliacao.AvaliacaoCriacaoDTO;
import com.pridepoints.api.dto.Avaliacao.AvaliacaoDTO;
import com.pridepoints.api.dto.Avaliacao.AvaliacaoMapper;
import com.pridepoints.api.entities.Avaliacao;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.repositories.AvaliacaoRepository;
import com.pridepoints.api.repositories.EmpresaRepository;
import com.pridepoints.api.repositories.FisicaRepository;
import com.pridepoints.api.utilities.ordenacao.Ordenacao;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {


    private final AvaliacaoRepository avaliacaoRepository;

    private final EmpresaRepository empresaRepository;

    private final FisicaRepository fisicaRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            EmpresaRepository empresaRepository,
                            FisicaRepository fisicaRepository){
        this.avaliacaoRepository = avaliacaoRepository;
        this.empresaRepository = empresaRepository;
        this.fisicaRepository = fisicaRepository;
    }

    @Transactional
    public AvaliacaoDTO publicarAvaliacaoDaEmpresa(AvaliacaoCriacaoDTO avaliacao,
                                                   Long empresaId,
                                                   Long usuarioId){

        Optional<Empresa> resultEmpresa = empresaRepository.findById(empresaId);
        Optional<Fisica> resultFisica = fisicaRepository.findById(usuarioId);

        if(resultFisica.isPresent() && resultEmpresa.isPresent()){

            Avaliacao novaAvaliacao = AvaliacaoMapper.of(avaliacao, resultFisica.get());

            novaAvaliacao.setEmpresa(resultEmpresa.get());
            novaAvaliacao.setPessoaFisica(resultFisica.get());



            return AvaliacaoMapper.of(avaliacaoRepository.save(novaAvaliacao));
        }
            return null;
    }

    @Transactional
    public List<AvaliacaoDTO> listarTodasAvaliacoes() {
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        return AvaliacaoMapper.of(avaliacaoList);
    }

    public List<AvaliacaoDTO> listarAvaliacoesDaEmpresa(Long id) {

        List<Avaliacao> result = avaliacaoRepository.findByEmpresaId(id);

        if(!result.isEmpty()){

            return AvaliacaoMapper.of(result);
        }

        return null;
    }

    @Transactional
    public AvaliacaoDTO atualizarAvaliacaoDaEmpresa(AvaliacaoCriacaoDTO novaAvaliacao,
                                                    Long idAvaliacao,
                                                    Long idEmpresa,
                                                    Long idUsuario) {
        Optional<Empresa> empresaBanco = empresaRepository.findById(idEmpresa);
        Optional<Fisica> usuarioBanco = fisicaRepository.findById(idUsuario);

        if(empresaBanco.isPresent() && usuarioBanco.isPresent()){
            Empresa empresaEncontrada = empresaBanco.get();
            Fisica usuarioEncontrado = usuarioBanco.get();

            boolean existe = avaliacaoRepository.existsById(idAvaliacao);

            if(existe){
                Avaliacao avaliacaoConvertida = AvaliacaoMapper.of(novaAvaliacao, usuarioEncontrado);
            avaliacaoConvertida.setId(idAvaliacao);
            avaliacaoConvertida.setEmpresa(empresaEncontrada);
            avaliacaoConvertida.setPessoaFisica(usuarioEncontrado);
            avaliacaoConvertida.setDtAvaliacao(new Date());

                return AvaliacaoMapper
                    .of(avaliacaoRepository
                            .save(avaliacaoConvertida));

            }
        }

        return null;
    }

    @Transactional
    public Avaliacao buscarAvaliacaoDeUsuario(Long idAvaliacao, Long idUsuario, Long idEmpresa){
        Optional<Empresa> empresaBanco = empresaRepository.findById(idEmpresa);
        Optional<Fisica> usuarioBanco = fisicaRepository.findById(idUsuario);

        if(empresaBanco.isPresent() && usuarioBanco.isPresent()){
            Empresa empresaEncontrada = empresaBanco.get();
            Fisica usuarioEncontrado = usuarioBanco.get();

            Optional<Avaliacao> avaliacao = avaliacaoRepository.findById(idAvaliacao);

            if(avaliacao.isPresent()){
                return avaliacao.get();

            }
        }
        
        return null;
    }


    @Transactional
    public boolean deletarAvaliacaoDaEmpresa(Long idAvaliacao) {

        boolean exists = avaliacaoRepository.existsById(idAvaliacao);

        if(exists){
            avaliacaoRepository.deleteById(idAvaliacao);
            return true;
        }
        return false;
    }


    public List<AvaliacaoDTO> listarAvaliacoesDoUsuario(Long idUsuario) {
        List<Avaliacao> result = avaliacaoRepository.findByPessoaFisicaId(idUsuario);

        if(!result.isEmpty()){

            return AvaliacaoMapper.of(result);
        }

        return null;
    }

    @Transactional
    public Integer contarAvaliacoes(Long idUsuario){
        return avaliacaoRepository.countByPessoaFisicaId(idUsuario);
    }


    @Transactional
    public List<AvaliacaoDTO> listarAvaliacoesPorMenorNota(Long idEmpresa) {
        Optional<Empresa> result = empresaRepository.findById(idEmpresa);
        Ordenacao ordenacaoNota = new Ordenacao();
        if (result.isPresent()) {
            Empresa empresaBanco = result.get();
            List<Avaliacao> avaliacoesEmpresa = empresaBanco.getAvaliacoes();

            return AvaliacaoMapper.of(ordenacaoNota.ordenaPorMenorNota(avaliacoesEmpresa));

        }
        return null;
    }

    @Transactional
    public List<AvaliacaoDTO> listarAvaliacoesPorMaiorNota(Long idEmpresa) {
        Optional<Empresa> result = empresaRepository.findById(idEmpresa);
        Ordenacao ordenacaoNota = new Ordenacao();
        if (result.isPresent()) {
            Empresa empresaBanco = result.get();
            List<Avaliacao> avaliacoesEmpresa = empresaBanco.getAvaliacoes();

            return AvaliacaoMapper.of(ordenacaoNota.ordenaPorMaiorNota(avaliacoesEmpresa));

        }
        return null;
    }


    public Avaliacao postResposta(Long idAvaliacao, AvaliacaoCriacaoDTO resposta) {
        Avaliacao avaliacao = avaliacaoRepository.findById(idAvaliacao)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        avaliacao.setResp(resposta.getResposta());
        avaliacao.setTitle(resposta.getTitle());
        return avaliacaoRepository.save(avaliacao);
    }
}
