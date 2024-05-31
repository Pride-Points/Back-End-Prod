package com.pridepoints.api.controller;

import com.pridepoints.api.dto.Autenticacao.UserDTO;
import com.pridepoints.api.dto.Autenticacao.UsuarioTokenDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaCriacaoDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaFullDTO;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaMinDTO;
import com.pridepoints.api.services.FisicaService;
import com.pridepoints.api.services.FuncionarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.pridepoints.api.dto.Usuario.Fisica.FisicaImgDTO;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FisicaController {
    private final FisicaService fisicaService;
    private final FuncionarioService funcionarioService;

    public FisicaController(FisicaService fisicaService,
                            FuncionarioService funcionarioService){
        this.fisicaService = fisicaService;
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDTO> loginUsuario(@Valid @RequestBody UserDTO usuario){
        UsuarioTokenDTO result = null;
        boolean existsFisica = fisicaService.findUser(usuario);
        boolean existsFunc = funcionarioService.findUser(usuario);

        if(existsFisica){
            result = fisicaService.autenticarFisica(usuario);
        }

        if(existsFunc){
            result = funcionarioService.autenticarFuncionario(usuario);
        }

        if(result == null){
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(result);
        }
    }

    @PatchMapping("/{indice}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_FISICA')")
    public ResponseEntity<FisicaFullDTO> atualizarEmail(@RequestBody FisicaCriacaoDTO obj, @PathVariable Long indice){
            FisicaFullDTO result =  fisicaService.atualizarEmail(obj, indice);
            if(result == null){
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.status(200).body(result);
            }
    }

    @PostMapping
    public ResponseEntity<FisicaFullDTO> cadastrarUsuario(@Valid @RequestBody FisicaCriacaoDTO f){
            FisicaFullDTO result = fisicaService.cadastrarUsuario(f);
            if(result == null){
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(201).body(result);
            }
    }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<FisicaMinDTO>> listarUsuarios(){
        List<FisicaMinDTO> listaUsuarios = fisicaService.listarPessoasFisicas();
        if(listaUsuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }
            return ResponseEntity.status(200).body(listaUsuarios);
    }

    @DeleteMapping("/{indice}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_FISICA')")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long indice){
        boolean result = fisicaService.removerPessoaFisica(indice);
        if(result){
            return ResponseEntity.status(200).build();
        }
            return ResponseEntity.status(404).build();
    }

    @PostMapping("/imagem-perfil/{idUser}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<FisicaImgDTO> salvarImg(@PathVariable Long idUser, @RequestBody FisicaImgDTO f){
        FisicaImgDTO result = fisicaService.salvarImg(idUser, f);


        if(result == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("imagem-perfil/{idUser}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_FISICA')")
    public ResponseEntity<FisicaImgDTO> buscarImgUser(@PathVariable Long idUser){
        FisicaImgDTO result = fisicaService.buscarImgUser(idUser);


        if(result == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(result);
    }
}
