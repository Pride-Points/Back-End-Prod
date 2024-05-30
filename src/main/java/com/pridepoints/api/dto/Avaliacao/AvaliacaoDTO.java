package com.pridepoints.api.dto.Avaliacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pridepoints.api.entities.Avaliacao;
import com.pridepoints.api.entities.Empresa;

import java.util.Date;

public class AvaliacaoDTO {

    private Long id;
    private double nota;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dtAvaliacao;
    private String tag;
    private String comentario;

    private boolean isShared;

    private String nomeAvaliador;

    private String resp;

    private String title;

    private Empresa empresa;


    public AvaliacaoDTO(){}

    public AvaliacaoDTO(Avaliacao entity) {
        this.id = entity.getId();
        this.nota = entity.getNota();
        this.dtAvaliacao = entity.getDtAvaliacao();
        this.tag = entity.getTag();
        this.comentario = entity.getComentario();
        this.isShared = entity.isShared();
        this.resp = entity.getResp();
        this.nomeAvaliador = entity.getNomeAvaliador();
        this.empresa = entity.getEmpresa();

    }

    public String getTitle() {
        if(title == null){
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNomeAvaliador() {
        return nomeAvaliador;
    }

    public void setNomeAvaliador(String nomeAvaliador) {
        this.nomeAvaliador = nomeAvaliador;
    }

    public String getResp() {
        if(resp == null){
            return "";
        }
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public Long getId() {
        return id;
    }

    public double getNota() {
        return nota;
    }

    public Date getDtAvaliacao() {
        return dtAvaliacao;
    }

    public String getTag() {
        return tag;
    }

    public String getComentario() {
        return comentario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setDtAvaliacao(Date dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
