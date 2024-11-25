package org.example.dominio;

public class Tabuleiro {

    public int[] tabuleiro;
    public int tamanho;

    public Tabuleiro() {
        tamanho = 3; // O tabuleiro será 3x3, mas representado como um vetor de 9 posições.
        tabuleiro = new int[tamanho * tamanho]; // Vetor de 9 posições
        zeraTabuleiro();
    }

    public void zeraTabuleiro() {
        // Inicializa o tabuleiro com valores 0
        for (int i = 0; i < tabuleiro.length; i++) {
            tabuleiro[i] = 0;
        }
    }

    public boolean estaLivre(int posicao) {
        return tabuleiro[posicao] == 0; // Verifica se a posição está livre
    }

    public boolean jogada(int posicao, int jogador) {
        // Verifica se a posição é válida e está livre
        if (posicao >= 0 && posicao < tabuleiro.length && estaLivre(posicao)) {
            tabuleiro[posicao] = jogador;
            return true; // Realiza a jogada
        } else {
            return false; // Posição inválida ou ocupada
        }
    }

    public boolean verificaVencedor() {

        for (int i = 0; i < tamanho; i++) {
            if (tabuleiro[i * tamanho] == tabuleiro[i * tamanho + 1] && tabuleiro[i * tamanho + 1] == tabuleiro[i * tamanho + 2] && tabuleiro[i * tamanho] != 0) {
                return true;
            }
        }


        for (int i = 0; i < tamanho; i++) {
            if (tabuleiro[i] == tabuleiro[i + tamanho] && tabuleiro[i + tamanho] == tabuleiro[i + 2 * tamanho] && tabuleiro[i] != 0) {
                return true;
            }
        }

        if (tabuleiro[0] == tabuleiro[4] && tabuleiro[4] == tabuleiro[8] && tabuleiro[0] != 0) {
            return true;
        }
        if (tabuleiro[2] == tabuleiro[4] && tabuleiro[4] == tabuleiro[6] && tabuleiro[2] != 0) {
            return true;
        }

        return false;
    }

    public boolean jogoEmpatado() {
        // Verifica se o jogo terminou em empate
        for (int i = 0; i < tabuleiro.length; i++) {
            if (tabuleiro[i] == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < tabuleiro.length; i++) {
            if (tabuleiro[i] == -1) {
                r.append(" X");
            } else if (tabuleiro[i] == 1) {
                r.append(" O");
            } else {
                r.append("  ");
            }
            if (i % tamanho != tamanho - 1) {
                r.append(" |");
            }
            if (i % tamanho == tamanho - 1 && i != tabuleiro.length - 1) {
                r.append("\n---+---+---\n");
            }
        }
        return r.toString();
    }

    public double[] getArrayTabuleiro() {
        double[] flatTabuleiro = new double[tabuleiro.length];
        for (int i = 0; i < tabuleiro.length; i++) {
            flatTabuleiro[i] = tabuleiro[i];
        }
        return flatTabuleiro;
    }

    public int[] getArrayTabuleiroInt(){
        return tabuleiro; // Retorna o vetor de inteiros
    }

    public int[] getTabuleiroVetor() {
        return tabuleiro;
    }
}
