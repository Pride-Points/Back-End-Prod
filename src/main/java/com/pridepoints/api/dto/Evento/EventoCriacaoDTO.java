package com.pridepoints.api.dto.Evento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

public class EventoCriacaoDTO {
    @NotBlank
    private String nome;

    private String imgEvento;
    @Size(min = 20)
    private String descricaoEvento;

    private LocalDate dtEvento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImgEvento() {
        return imgEvento;
    }

    public void setImgEvento(String imgEvento) {
        this.imgEvento = imgEvento;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public LocalDate getDtEvento() {
        return dtEvento;
    }

    public void setDtEvento(LocalDate dtEvento) {
        this.dtEvento = dtEvento;
    }
}
