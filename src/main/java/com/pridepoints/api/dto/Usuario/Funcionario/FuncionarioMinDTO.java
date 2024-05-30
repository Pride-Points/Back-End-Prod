package com.pridepoints.api.dto.Usuario.Funcionario;

public class FuncionarioMinDTO {
    private Long id;
    private String nome;
    private String cargo;

    public FuncionarioMinDTO() {
    }
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
