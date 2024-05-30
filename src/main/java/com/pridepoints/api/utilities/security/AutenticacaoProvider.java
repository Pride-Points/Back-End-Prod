package com.pridepoints.api.utilities.security;

import com.pridepoints.api.services.AutenticacaoService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AutenticacaoProvider implements AuthenticationProvider {

    private final AutenticacaoService usuarioAutorizacaoService;
    private final PasswordEncoder passowordEncoder;

    public AutenticacaoProvider(AutenticacaoService usuarioAutorizacaoService, PasswordEncoder passowordEncoder) {
        this.usuarioAutorizacaoService = usuarioAutorizacaoService;
        this.passowordEncoder = passowordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       final String username = authentication.getName();
       final String password = authentication.getCredentials().toString();

        UserDetails userDetails = this.usuarioAutorizacaoService.loadUserByUsername(username);
        if(this.passowordEncoder.matches(password,userDetails.getPassword() )){
            return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("Usuario ou senha invalidos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
