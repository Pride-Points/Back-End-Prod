package com.pridepoints.api.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;

    private String cnpj;

    private String cep;

    private int numero;

    private String cidade;
    private String estado;



    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Avaliacao> avaliacoes;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventos;

    public Empresa() {
        this.funcionarios = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void adicionarFuncionario(Funcionario funcionario){
       this.funcionarios.add(funcionario);
    }

    public void adicionarAvaliacao(Avaliacao avaliacao){
        this.avaliacoes.add(avaliacao);
    }

    public void adicionarEvento(Evento evento){
        this.eventos.add(evento);
    }
}
