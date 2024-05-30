package com.pridepoints.api.dto.Usuario.Funcionario;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FuncionarioFullDTO {
    private Long id;
    private String nome;
    private String email;
    private String cargo;
    private String empresa;
    private boolean isGerente;
    private boolean isAtivo;

    private String tipoFuncionario;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate ultimaTrocaSenha;

    public FuncionarioFullDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        if(email == null){
            return "";
        }
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public LocalDate getUltimaTrocaSenha() {
        return ultimaTrocaSenha;
    }

    public Boolean isGerente() {
        return isGerente;
    }

    public Boolean isAtivo() {
        return isAtivo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setGerente(boolean gerente) {
        isGerente = gerente;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public void setUltimaTrocaSenha(LocalDate ultimaTrocaSenha) {
        this.ultimaTrocaSenha = ultimaTrocaSenha;
    }
}
