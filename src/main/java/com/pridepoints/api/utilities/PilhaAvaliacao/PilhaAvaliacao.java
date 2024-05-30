package com.pridepoints.api.utilities.PilhaAvaliacao;

import com.pridepoints.api.entities.Avaliacao;


public class PilhaAvaliacao <T> {
    private T[] pilha;
    private int topo;

    public PilhaAvaliacao(int capacidade){
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    public Boolean isEmpty() {
        return topo <= -1;
    }

    public Boolean isFull() {
        boolean cheio = false;
        if(pilha.length -1 == topo){
            cheio = true;
        }
        return cheio;
    }

    public void push(T info) {
        if (isFull()){
            throw new IllegalStateException("Pilha cheia!");
        }
        pilha[++topo] = info;
    }

    public T pop() {
        T aux = pilha[topo];
        --topo;
        return aux;
    }

    public T peek() {
        if(isEmpty()){
            return null;
        }
        return pilha[topo];
    }

    public void exibe() {
        for (int i = 0; i < topo; i++) {
            System.out.println(pilha[i]);
        }
    }

    public int getTopo() {
        return topo;
    }
}
