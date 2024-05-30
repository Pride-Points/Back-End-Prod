package com.pridepoints.api.services;

import com.pridepoints.api.entities.Avaliacao;

import com.pridepoints.api.utilities.PilhaAvaliacao.PilhaAvaliacao;
import org.springframework.stereotype.Service;

@Service
public class PilhaService {
    private PilhaAvaliacao<Avaliacao> novaPilha;
    private final AvaliacaoService avaliacaoService;

    public PilhaService(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }


    public void aplicarTamanhoPilha(Integer count){
        if(novaPilha == null || novaPilha.isEmpty()) {
            novaPilha = new PilhaAvaliacao(count);
        }
    }

    public void guardar(Long idAvaliacao, Long idUsuario, Long idEmpresa ){
        novaPilha.push(avaliacaoService.buscarAvaliacaoDeUsuario(idAvaliacao,idUsuario,idEmpresa));
    }

    public Avaliacao retirar(){
        return novaPilha.pop();
    }
    public Avaliacao espiar(){
        return novaPilha.peek();
    }
}
