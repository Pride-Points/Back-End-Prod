package com.pridepoints.api.controller;
import com.pridepoints.api.dto.Autenticacao.FuncionarioDetalhesDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioCriacaoDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioMinDTO;
import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioUpdateDTO;
import com.pridepoints.api.services.EmpresaService;
import com.pridepoints.api.services.FuncionarioService;
import com.pridepoints.api.utilities.importacao.ImportacaoTxt;
import com.pridepoints.api.entities.Funcionario;
import com.pridepoints.api.utilities.exportacao.Exportacao;
import com.pridepoints.api.utilities.Fila.FilaObj;
import com.pridepoints.api.utilities.lista.ListaObj;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.pridepoints.api.utilities.download.DownloadCSV;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {


    private final FuncionarioService funcionarioService;
    private final EmpresaService empresaService;

    public FuncionarioController(FuncionarioService funcionarioService, EmpresaService empresaService) {
        this.funcionarioService = funcionarioService;
        this.empresaService = empresaService;
    }


    @SecurityRequirement(name = "Bearer")
    @GetMapping
    public ResponseEntity<List<FuncionarioFullDTO>> listarFuncionarios() {

        List<FuncionarioFullDTO> result = funcionarioService.listarFuncionarios();

        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ativos/{idEmpresa}")
    public ResponseEntity<List<FuncionarioFullDTO>> listarAtivos(@PathVariable Long idEmpresa) {
        List<FuncionarioFullDTO> result = funcionarioService.listarFuncionariosAtivos(idEmpresa);
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/inativos/{idEmpresa}")
    public ResponseEntity<List<FuncionarioFullDTO>> listarInativos(Long idEmpresa) {

        List<FuncionarioFullDTO> result = funcionarioService.listarFuncionariosInativos(idEmpresa);

        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{idFunc}")
    public ResponseEntity<FuncionarioFullDTO> listarPorId(@PathVariable Long idFunc) {
        FuncionarioFullDTO fullDTO = funcionarioService.listarPorId(idFunc);
        if (fullDTO == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(fullDTO);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/ordenados")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<FuncionarioFullDTO>> listarFuncionariosOrdenadosPorNome() {
        List<FuncionarioFullDTO> result = funcionarioService.listarFuncionariosOrdenadosPorNome();
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }
    @GetMapping("/ordenados-por-cpf")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<FuncionarioFullDTO>> listarFuncionariosOrdenadosPorCPF() {
        List<FuncionarioFullDTO> result = funcionarioService.listarFuncionariosOrdenadosPorCPF();
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }



    @SecurityRequirement(name = "Bearer")
    @PostMapping("/{empresaId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FuncionarioFullDTO> cadastrarFuncionario(@Valid @RequestBody FuncionarioCriacaoDTO f,
                                                                   @PathVariable Long empresaId) {

        FuncionarioFullDTO result = funcionarioService.cadastrarFuncionario(f, empresaId);

        if (result == null) {
            return ResponseEntity.status(409).build();
        } else {
            return ResponseEntity.status(201).body(result);
        }
    }
    @SecurityRequirement(name = "Bearer")
    @PostMapping("/importacao/{empresaId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<FuncionarioFullDTO>> cadastrarFuncionariosTxt(
            @PathVariable Long empresaId,
            @RequestParam MultipartFile file
    ) {
        // Verifique se o arquivo é nulo ou vazio antes de prosseguir
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<FuncionarioFullDTO> result = new ImportacaoTxt(funcionarioService, empresaService).leArquivoTxt(file, empresaId);

        if (result.isEmpty()) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(201).body(result);
        }
    }



    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{idEmpresa}/{idFunc}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletarFunc(@PathVariable Long idEmpresa, @PathVariable Long idFunc) {
        boolean result = funcionarioService.deletarFunc(idEmpresa, idFunc);

        if (result) {
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
        @SecurityRequirement(name = "Bearer")
        @GetMapping("/cpf")
        public ResponseEntity<FuncionarioFullDTO> encontrarFuncionarioPorCpf(@RequestParam String cpf){
            FuncionarioFullDTO result = funcionarioService.encontrarFuncionarioPorCpf(cpf);

            if (result != null) {
                return ResponseEntity.status(200).body(result);
            } else {
                return ResponseEntity.status(404).build();
            }
        }
    @GetMapping("/por-cpf")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<FuncionarioFullDTO> buscarFuncionarioPorCPF(@RequestParam String cpf) {
        FuncionarioFullDTO funcionario = funcionarioService.buscarFuncionarioPorCPF(cpf);
        if (funcionario != null) {
            return ResponseEntity.status(200).body(funcionario);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/exportar-txt")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Resource> exportarFuncionariosParaTXT(@RequestParam String cnpj) {
        List<Funcionario> funcionarios = empresaService.getFuncionariosDaEmpresaa(cnpj);
        Exportacao exportar = new Exportacao();
        if (funcionarios == null) {
            return ResponseEntity.notFound().build();
        }


        if (funcionarios == null || funcionarios.isEmpty()) {
            // Se não houver funcionários, retorne uma resposta vazia com status 204
            return ResponseEntity.noContent().build();
        } else {
            // Gere um arquivo TXT com os funcionários
            String txtFilename = "funcionarios.txt";
            exportar.gravaArquivoFuncionarios(funcionarios, txtFilename);

            // Crie um recurso FileSystemResource para o arquivo TXT gerado
            Resource resource = new FileSystemResource(txtFilename);

            // Configure os cabeçalhos da resposta para indicar o tipo de mídia e o nome do arquivo
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=funcionarios.txt");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

            // Retorne a resposta com o arquivo TXT como anexo
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        }
    }


    @GetMapping("/download-csv")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Resource> downloadCSVFuncionarios(@RequestParam String cnpj) {
        ListaObj<FuncionarioFullDTO> funcionarios = empresaService.getFuncionariosDaEmpresa(cnpj);
        if (funcionarios == null || funcionarios.getTamanho() == 0) {
            // Se não houver funcionários, retorne uma resposta vazia com status 204
            return ResponseEntity.noContent().build();
        } else {
            try {
                List<FuncionarioFullDTO> funcionariosList = new ArrayList<>();

                int tamanho = funcionarios.getTamanho();
                for (int i = 0; i < tamanho; i++) {
                    FuncionarioFullDTO funcionario = funcionarios.getElemento(i);
                    funcionariosList.add(funcionario);
                }
                // Gere um arquivo CSV com os funcionários
                String csvFilename = "funcionarios.csv";
                DownloadCSV<FuncionarioFullDTO> csvExporter = new DownloadCSV<>();
                csvExporter.exportToCSV(funcionariosList, csvFilename);

                //Instancia um objeto do tipo fila e armazena o nome do arquivo como para
                FilaObj<String> fila= new FilaObj<>(10);
                fila.insert(csvFilename);
                System.out.println("ARQUIVO SENDO PROCESSADO....");
                fila.exibe();

                // Crie um recurso FileSystemResource para o arquivo CSV gerado
                Resource resource = new FileSystemResource(csvFilename);

                // Configure os cabeçalhos da resposta para indicar o tipo de mídia e o nome do arquivo
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=funcionarios.csv");
                headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

                // Armazena o resultado do ResponseEntity em uma variavel
                ResponseEntity<Resource> responseEntity = ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);


                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    fila.poll();
                    return responseEntity;

                }else{
                    fila.poll();
                    return responseEntity;
                }



            } catch (IOException e) {
                // Em caso de erro ao criar o arquivo CSV, retorne uma resposta de erro 500
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FuncionarioUpdateDTO> atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioCriacaoDTO funcionarioRequest) {

        FuncionarioUpdateDTO funcionarioAtualizado = funcionarioService.updateFuncionario(id, funcionarioRequest);

        if(funcionarioAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}