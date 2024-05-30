package com.pridepoints.api.dto.Empresa;

public class EmpresaMinDTO {
    private Long id;
    private String nomeFantasia;
    private String cidade;
    private String estado;
    private String dono;

    public EmpresaMinDTO(){}

    public Long getId() {
        return id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getDono() {
        return dono;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }
}
