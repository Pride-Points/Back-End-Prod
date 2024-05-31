package com.pridepoints.api.dto.Usuario.Fisica;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FisicaImgDTO {

    private String imgUser;

    @JsonCreator  // Indica que o Jackson deve usar este construtor para deserialização
    public FisicaImgDTO(@JsonProperty("imgUser") String imgUser) {  // Mapeia o campo JSON para o parâmetro do construtor
        this.imgUser = imgUser;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }
}
