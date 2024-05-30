package com.pridepoints.api.dto.Evento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class EventoDTO {

    private Long id;
    private String nome;
    private String imgEvento;
    private String descricaoEvento;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dtEvento;

    public EventoDTO(){}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImgEvento() {
        return imgEvento;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public LocalDate getDtEvento() {
        return dtEvento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImgEvento(String imgEvento) {
        this.imgEvento = imgEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public void setDtEvento(LocalDate dtEvento) {
        this.dtEvento = dtEvento;
    }
}
