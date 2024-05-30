package com.pridepoints.api.controller;

import com.pridepoints.api.dto.Evento.EventoCriacaoDTO;
import com.pridepoints.api.dto.Evento.EventoDTO;
import com.pridepoints.api.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {


    private final EventoService eventoService;

    public EventoController(EventoService eventoService){
        this.eventoService = eventoService;
    }


    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos(){

        List<EventoDTO> result = eventoService.listarEventos();

        if(result.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<EventoDTO>> listarEventosDaEmpresa(@PathVariable Long idEmpresa){
        List<EventoDTO> result = eventoService.listarEventosDaEmpresa(idEmpresa);

        if(result.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/empresa/{idEmpresa}/{idEvento}")
    public ResponseEntity<EventoDTO> buscarEventoPorId(@PathVariable Long idEmpresa,
                                                       @PathVariable Long idEvento){
        EventoDTO result = eventoService.buscarEventoPorId(idEmpresa, idEvento);

        if(result == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/{idEmpresa}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<EventoDTO> adicionarEvento(@Valid @RequestBody EventoCriacaoDTO eventoCriacaoDTO, @PathVariable Long idEmpresa){
        EventoDTO result = eventoService.adicionarEvento(eventoCriacaoDTO, idEmpresa);

        if(result == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(201).body(result);
    }

    @PutMapping("/{idEmpresa}/{idEvento}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<EventoDTO> atualizarEvento(@Valid @RequestBody EventoCriacaoDTO eventoAtualizado,
                                                     @PathVariable Long idEmpresa,
                                                     @PathVariable Long idEvento){

        EventoDTO result = eventoService.atualizarEvento(eventoAtualizado, idEmpresa, idEvento);

        if(result ==  null){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);

    }

    @DeleteMapping("/{idEvento}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<Void> removerEvento(@PathVariable Long idEvento){

        boolean removeu = eventoService.removerEvento(idEvento);

        if(removeu){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}

