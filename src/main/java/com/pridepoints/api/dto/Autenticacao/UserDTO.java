package com.pridepoints.api.dto.Autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
            message = "A senha deve conter pelo menos uma letra maiúscula," +
                    " uma letra minúscula, um número e ter no mínimo 8 caracteres.")
    private String senha;
    @Email
    private String email;


    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
