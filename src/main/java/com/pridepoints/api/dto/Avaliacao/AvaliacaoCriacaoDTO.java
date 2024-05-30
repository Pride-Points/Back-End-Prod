package com.pridepoints.api.dto.Avaliacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

public class AvaliacaoCriacaoDTO {

    @NotNull
    private double nota;
    @NotBlank
    private String tag;
    private String comentario;

    private String resposta;

    private String title;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dtAvaliacao;

    private boolean isShared;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getComentario() {
        return comentario;
    }

    public Date getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(Date dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
