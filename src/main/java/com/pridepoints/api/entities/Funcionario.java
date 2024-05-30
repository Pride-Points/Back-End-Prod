package com.pridepoints.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cargo;
    private String cpf;
    private String tipoFuncionario;
    @Column(columnDefinition = "BIT")
    private boolean isGerente;
    @Column(columnDefinition = "BIT")
    private boolean isAtivo = true;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public Funcionario() {
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isGerente() {
        return this.isGerente;
    }

    public void setIsGerente(boolean gerente) {
        isGerente = gerente;
    }

    public boolean isAtivo() {
        return isAtivo;
    }

    public void setIsAtivo(boolean ativo) {
        isAtivo = ativo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(String tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setGerente(boolean gerente) {
        isGerente = gerente;
    }

    public void setAtivo(boolean ativo) {
        isAtivo = ativo;
    }
}
