package com.pridepoints.api.utilities.lista;

import java.util.ArrayList;
import java.util.List;

public class ListaObj<T> {
    private T[] vetor;
    private int nroElementos;

    public ListaObj(int tamanho) {
        vetor = (T[]) new Object[tamanho];
        nroElementos = 0;
    }

    public boolean adiciona(T elemento) {
        if (nroElementos < vetor.length) {
            vetor[nroElementos] = elemento;
            nroElementos++;
            return true;
        }
        return false;
    }

    public T getElemento(int indice) {
        if (indice >= 0 && indice < nroElementos) {
            return vetor[indice];
        }
        return null;
    }

    public int getTamanho() {
        return nroElementos;
    }
}
