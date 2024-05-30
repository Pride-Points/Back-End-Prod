package com.pridepoints.api.dto.Usuario.Fisica;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pridepoints.api.dto.Avaliacao.AvaliacaoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FisicaFullDTO {

    private Long id;
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private LocalDate dtNascimento;
    private String email;
    private String genero;

    private String orientacaoSexual;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate ultimaTrocaSenha;

    private List<AvaliacaoDTO> avaliacoes;

    public FisicaFullDTO() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getGenero() {
        return genero;
    }

    public String getOrientacaoSexual() {
        return orientacaoSexual;
    }

    public LocalDate getUltimaTrocaSenha() {
        return ultimaTrocaSenha;
    }

    public List<AvaliacaoDTO> getAvaliacoes() {
        return avaliacoes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setOrientacaoSexual(String orientacaoSexual) {
        this.orientacaoSexual = orientacaoSexual;
    }

    public void setUltimaTrocaSenha(LocalDate ultimaTrocaSenha) {
        this.ultimaTrocaSenha = ultimaTrocaSenha;
    }

    public void setAvaliacoes(List<AvaliacaoDTO> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}
