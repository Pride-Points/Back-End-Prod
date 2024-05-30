package com.pridepoints.api.dto.Usuario.Funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public class FuncionarioCriacaoDTO {
    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
               message = "A senha deve conter pelo menos uma letra maiúscula," +
                       " uma letra minúscula, um número e ter no mínimo 8 caracteres.")
    private String senha;
    private String cargo;

    private String cpf;
    @NotBlank
    private String tipoFuncionario;
    private Boolean isGerente;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getGerente() {
        return isGerente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public Boolean getIsGerente() {
        return isGerente;
    }

    public void setGerente(Boolean gerente) {
        isGerente = gerente;
    }
}
