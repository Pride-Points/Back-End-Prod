package com.pridepoints.api.services;

import com.pridepoints.api.dto.Evento.EventoCriacaoDTO;
import com.pridepoints.api.dto.Evento.EventoDTO;
import com.pridepoints.api.dto.Evento.EventoMapper;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Evento;
import com.pridepoints.api.repositories.EmpresaRepository;
import com.pridepoints.api.repositories.EventoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;


    private final EmpresaRepository empresaRepository;

    public EventoService(EventoRepository eventoRepository,
                         EmpresaRepository empresaRepository){
        this.eventoRepository = eventoRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public List<EventoDTO> listarEventos() {
        List<Evento> result = eventoRepository.findAll();

            return EventoMapper.ofListDtos(result);
        }

    @Transactional
    public EventoDTO adicionarEvento(EventoCriacaoDTO eventoCriacaoDTO, Long idEmpresa) {
        Optional<Empresa> result = empresaRepository.findById(idEmpresa);

        if(result.isPresent()){
            Evento eventoMapeado = EventoMapper.of(eventoCriacaoDTO);
            Empresa empresa = result.get();
            eventoMapeado.setEmpresa(empresa);

            return EventoMapper.of(eventoRepository.save(eventoMapeado));
        }

        return null;
    }
    @Transactional
    public EventoDTO atualizarEvento(EventoCriacaoDTO eventoAtualizado, Long idEmpresa, Long idEvento) {
        Optional<Empresa> result = empresaRepository.findById(idEmpresa);

        if(result.isPresent()){
            Empresa empresa = result.get();
            boolean exists = eventoRepository.existsById(idEvento);

            if(exists){
                Evento eventoMapeado = EventoMapper.of(eventoAtualizado);
                eventoMapeado.setId(idEvento);
                eventoMapeado.setEmpresa(empresa);

                return EventoMapper.of(eventoRepository.save(eventoMapeado));
            }
        }
        return null;
    }

    @Transactional
    public boolean removerEvento(Long idEvento) {
        boolean exists = eventoRepository.existsById(idEvento);
        if(exists){
            eventoRepository.deleteById(idEvento);
            return true;
        }

        return false;
    }

    public List<EventoDTO> listarEventosDaEmpresa(Long idEmpresa) {
        List<Evento> result = eventoRepository.findByEmpresaId(idEmpresa);
        if(result == null || result.isEmpty()){
            return new ArrayList<>();
        }
        if(!result.isEmpty()){

            List<EventoDTO> eventos = EventoMapper.ofListDtos(result);

            return eventos;
        }
        return null;
    }

    public EventoDTO buscarEventoPorId(Long idEmpresa, Long idEvento) {

        boolean exists = empresaRepository.existsById(idEmpresa);

        if(exists){
            Optional<Evento> resultEvent = eventoRepository.findByEmpresaIdAndId(idEmpresa, idEvento);
            if(resultEvent.isPresent()){
                return EventoMapper.of(resultEvent.get());
            }
        }
        return null;
    }
}
