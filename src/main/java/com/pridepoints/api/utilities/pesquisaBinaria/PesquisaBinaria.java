package com.pridepoints.api.utilities.pesquisaBinaria;

import com.pridepoints.api.entities.Funcionario;

import java.util.List;

public class PesquisaBinaria {

    public long formatarDadoCpf(String cpf) {
        // tudo que for diferente entre 0 e 9 ele transforma em espaço vazio
        String numericCpf = cpf.replaceAll("[^0-9]", "");

        // pega a String e transforma em Long para a gente consegui fazer a pesquisa binaria
        return Long.parseLong(cpf);
    }
    public Funcionario binarySearch(List<Funcionario> funcionarios, String cpf) {
        int left = 0;
        int right = funcionarios.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Funcionario funcionario = funcionarios.get(mid);

            int comparacao = cpf.compareTo(funcionario.getCpf());

            if (comparacao == 0) {
                // Encontrou o funcionário com o CPF desejado.
                return funcionario;
            } else if (comparacao < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // Funcionário com o CPF não encontrado na lista.
        return null;
    }

    public int pesquisaBinariaPorCPF(List<Funcionario> funcionarios, String cpf) {
        long numericCpf = formatarDadoCpf(cpf);

        int esquerda = 0;
        int direita = funcionarios.size() - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            long cpfNoMeio = formatarDadoCpf(funcionarios.get(meio).getCpf());

            if (cpfNoMeio == numericCpf) {
                return meio; // Encontrou o CPF
            }

            if (cpfNoMeio < numericCpf) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return -1;
    }



}
