package com.pridepoints.api.utilities.importacao;

import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.entities.Empresa;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.services.EmpresaService;
import com.pridepoints.api.services.FuncionarioService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportacaoTxt {

    private final FuncionarioService funcionarioService;
    private final EmpresaService empresaService;

    public ImportacaoTxt(FuncionarioService funcionarioService, EmpresaService empresaService) {
        this.funcionarioService = funcionarioService;
        this.empresaService = empresaService;
    }

    public List<FuncionarioFullDTO> leArquivoTxt(MultipartFile file, long empresaId) {
        List<Funcionario> listaLida = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String registro;
            while ((registro = entrada.readLine()) != null) {
                String tipoRegistro = registro.substring(0, 2);

                if (tipoRegistro.equals("02")) {
                    long id = Long.parseLong(registro.substring(2, 8).trim());
                    String cargo = registro.substring(8, 23).trim();
                    String cpf = registro.substring(23, 34).trim();
                    String tipoFuncionario = registro.substring(34, 49).trim();
                    boolean isGerente = registro.substring(49, 54).trim().equalsIgnoreCase("true");
                    boolean isAtivo = registro.substring(54, 59).trim().equalsIgnoreCase("true");
                    String nome = registro.substring(64, 319).trim();
                    String email = registro.substring(319, 574).trim();
                    String ultimaTrocaSenha = registro.substring(574, 584).trim();
                    System.out.println(ultimaTrocaSenha);
                    LocalDate dataFormatada = LocalDate.parse(ultimaTrocaSenha, formatter);

                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(id);
                    funcionario.setCargo(cargo);
                    funcionario.setCpf(cpf);
                    funcionario.setTipoFuncionario(tipoFuncionario);
                    funcionario.setIsGerente(isGerente);

                    Empresa empresaBanco = empresaService.buscarPorIdTxt(empresaId);
                    if(empresaBanco != null){
                        funcionario.setEmpresa(empresaBanco);
                    }
                    funcionario.setIsAtivo(isAtivo);
                    funcionario.setNome(nome);
                    funcionario.setEmail(email);
                    funcionario.setUltimaTrocaSenha(dataFormatada);

                    listaLida.add(funcionario);
                }
            }
        } catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo");
        }

        // Aqui seria possível salvar a lista no BD
        List<FuncionarioFullDTO> funcionariosDTO = funcionarioService.salvarFuncionarios(listaLida);
        return funcionariosDTO;
    }
}
