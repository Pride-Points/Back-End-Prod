package com.pridepoints.api.dto.Empresa;

public class EmpresaFullDTO {
    private Long id;
    private String nomeFantasia;
    private String cnpj;
    private String cep;
    private int numero;
    private String cidade;
    private String estado;
    private String Dono;


    public EmpresaFullDTO(){}

    public Long getId() {
        return id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCep() {
        return cep;
    }

    public int getNumero() {
        return numero;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getDono(){ return Dono; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDono(String dono) {
        if(dono == null) {
            Dono = "";
        }
        Dono = dono;
    }
}
