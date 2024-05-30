package com.pridepoints.api.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tb_fisica")
public class Fisica extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orientacaoSexual;

    private String genero;

    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private LocalDate dtNascimento;

    private boolean forcarTrocaDeSenha = false;

    @OneToMany(mappedBy = "pessoaFisica")
    private List<Avaliacao> avaliacoesUsuario;

    public Fisica() {
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

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public boolean isForcarTrocaDeSenha() {
        return forcarTrocaDeSenha;
    }

    public void setForcarTrocaDeSenha(boolean forcarTrocaDeSenha) {
        this.forcarTrocaDeSenha = forcarTrocaDeSenha;
    }

    public List<Avaliacao> getAvaliacoesUsuario() {
        return avaliacoesUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void adicionarAvaliacao(Avaliacao novaAvaliacao){
        this.avaliacoesUsuario.add(novaAvaliacao); }

}
