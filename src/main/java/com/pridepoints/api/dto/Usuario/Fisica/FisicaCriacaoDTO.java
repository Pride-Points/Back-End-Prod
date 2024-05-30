package com.pridepoints.api.dto.Usuario.Fisica;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


import java.time.LocalDate;

public class FisicaCriacaoDTO {
    @Size(min = 3)
    private String nome;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula," +
                    " uma letra minúscula, um número e ter no mínimo 8 caracteres.")
    private String senha;
    @Email
    private String email;
    private String orientacaoSexual;
    private String genero;

    @CPF
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private LocalDate dtNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrientacaoSexual() {
        return orientacaoSexual;
    }

    public void setOrientacaoSexual(String orientacaoSexual) {
        this.orientacaoSexual = orientacaoSexual;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    @Override
    public String toString() {
        return "FisicaCriacaoDTO{" +
                "nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", orientacaoSexual='" + orientacaoSexual + '\'' +
                ", genero='" + genero + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dtNascimento=" + dtNascimento +
                '}';
    }
}
