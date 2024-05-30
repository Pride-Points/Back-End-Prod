package com.pridepoints.api.dto.Autenticacao;

import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.utilities.security.CustomAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PessoaFisicaDetalhesDTO implements UserDetails {
    private final String email;
    private final String senha;
    private final String nome;

    public PessoaFisicaDetalhesDTO(Fisica fisica) {
        this.email = fisica.getEmail();
        this.senha = fisica.getSenha();
        this.nome = fisica.getNome();
    }


    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new CustomAuthority("ROLE_FISICA"));

        // Adicione outras autorizações conforme necessário
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
