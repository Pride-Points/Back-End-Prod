package com.pridepoints.api.dto.Autenticacao;

import com.pridepoints.api.entities.Fisica;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.utilities.security.CustomAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FuncionarioDetalhesDTO implements UserDetails {
    private final String email;
    private final String senha;
    private final String nome;

    private final String permissaoFunc;

    public FuncionarioDetalhesDTO(Funcionario funcionario) {
        this.email = funcionario.getEmail();
        this.senha = funcionario.getSenha();
        this.nome = funcionario.getNome();
        this.permissaoFunc = funcionario.getTipoFuncionario();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(permissaoFunc.equalsIgnoreCase("Admin")){
            authorities.add(new CustomAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new CustomAuthority("ROLE_USER"));
        }
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
