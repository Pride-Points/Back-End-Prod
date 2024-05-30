package com.pridepoints.api.dto.Evento;

import com.pridepoints.api.entities.Evento;

import java.util.List;
import java.util.stream.Collectors;

public class EventoMapper {

    public static Evento of(EventoCriacaoDTO eventoCriacaoDTO){
        Evento evento = new Evento();

        evento.setNome(eventoCriacaoDTO.getNome());
        evento.setImgEvento(eventoCriacaoDTO.getImgEvento());
        evento.setDescricaoEvento(eventoCriacaoDTO.getDescricaoEvento());
        evento.setDtEvento(eventoCriacaoDTO.getDtEvento());

        return evento;
    }

    public static EventoDTO of(Evento evento){
        EventoDTO eventoDTO = new EventoDTO();

        eventoDTO.setId(evento.getId());
        eventoDTO.setNome(evento.getNome());
        eventoDTO.setImgEvento(evento.getImgEvento());
        eventoDTO.setDescricaoEvento(evento.getDescricaoEvento());
        eventoDTO.setDtEvento(evento.getDtEvento());

        return eventoDTO;
    }

    public static List<EventoDTO> ofListDtos(List<Evento> eventos){

        List<EventoDTO> eventoDTOS;

        eventoDTOS = eventos.stream()
                .map(EventoMapper::of)
                .collect(Collectors.toList());

        return eventoDTOS;
    }
}
