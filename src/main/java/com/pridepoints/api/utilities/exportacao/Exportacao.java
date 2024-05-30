package com.pridepoints.api.utilities.exportacao;

import com.pridepoints.api.dto.Usuario.Funcionario.FuncionarioFullDTO;
import com.pridepoints.api.entities.Funcionario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Exportacao {
    public void gravaArquivoFuncionarios(List<Funcionario> lista, String nomeArq) {
        int contaRegDados = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Monta o registro de header
        String header = "00Funcionarios";
        header += LocalDateTime.now().format(formatter);
        header += "01";

        // Grava o registro de header
        gravaRegistro(header, nomeArq);

        // Grava os registros de dados (registros de corpo)
        for (Funcionario funcionario : lista) {
            String corpo = "02";
            corpo += String.format("%06d", funcionario.getId());
            corpo += String.format("%-15.15s", funcionario.getCargo());
            corpo += String.format("%-11.11s", funcionario.getCpf());
            corpo += String.format("%-15.15s", funcionario.getTipoFuncionario());
            corpo += String.format("%-5.5s", funcionario.isGerente());
            corpo += String.format("%-5.5s", funcionario.isAtivo());
            corpo += String.format("%05d", funcionario.getEmpresa().getId());
            corpo += String.format("%-255.255s", funcionario.getNome());
            corpo += String.format("%-255.255s", funcionario.getEmail());
            corpo += String.format("%-10.10s", funcionario.getUltimaTrocaSenha());
            // Grava o registro de corpo
            gravaRegistro(corpo, nomeArq);
            // Incrementa o contador de registros de dados gravados
            contaRegDados++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d", contaRegDados);

        gravaRegistro(trailer, nomeArq);
    }
    public void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

        // Abre o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        } catch (IOException erro) {
            System.out.println("Erro na abertura do arquivo");
        }

        // Grava o registro e fecha o arquivo
        try {
            if (saida != null) {
                saida.append(registro).append("\n");
                saida.close();
            }
        } catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
        }
    }

}
