package com.pridepoints.api.utilities.Fila;


public class FilaObj<T> {
    // Atributos
    private int tamanho;
    private T[] fila;

    // Construtor
    public FilaObj(int capacidade) {
        this.tamanho = 0;
        // Criar um array de tipo genérico
        this.fila = (T[]) new Object[capacidade];
    }

    // Métodos

    /* Método isEmpty() - retorna true se a fila está vazia e false caso contrário */
    public boolean isEmpty() {
        return tamanho == 0;
    }

    /* Método isFull() - retorna true se a fila está cheia e false caso contrário */
    public boolean isFull() {
        return tamanho == fila.length;
    }

    /* Método insert - recebe um elemento e insere esse elemento na fila
                       no índice tamanho, e incrementa tamanho
                       Lançar IllegalStateException caso a fila esteja cheia
     */
    public void insert(T elemento) {
        if (isFull()) {
            throw new IllegalStateException("A fila está cheia. Não é possível inserir.");
        }
        fila[tamanho] = elemento;
        tamanho++;
    }

    /* Método peek - retorna o primeiro elemento da fila, sem removê-lo */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return fila[0];
    }

    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
       Depois que a fila andar, "limpar" o ex-último elemento da fila, atribuindo null
     */
    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T elementoRemovido = fila[0];

        for (int i = 0; i < tamanho - 1; i++) {
            fila[i] = fila[i + 1];
        }

        fila[tamanho - 1] = null;
        tamanho--;

        return elementoRemovido;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe() {
        System.out.print("Fila: ");
        for (int i = 0; i < tamanho; i++) {
            System.out.print(fila[i] + " ");
        }
        System.out.println();
    }

    /* Usado nos testes  - complete para que fique certo */
    public int getTamanho(){
        return tamanho;
    }
}

