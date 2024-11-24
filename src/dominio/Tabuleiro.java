package tabuleiro;

public class Tabuleiro {

    public int[][] tabuleiro;
    public int tamanho;

    public Tabuleiro(int tamanho) {
        this.tamanho = tamanho;
        tabuleiro = new int[tamanho][tamanho];
        zeraTabuleiro();
    }

    public void zeraTabuleiro() {
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                tabuleiro[x][y] = 0; 
            }
        }
    }

    public boolean estaLivre(int x, int y) {
        return tabuleiro[x][y] == 0; 
    }

    public boolean jogada(int posicao, int jogador) {
        int x = posicao % tamanho;  
        int y = posicao / tamanho;  

        // Verifica se a posição é válida e está livre
        if (posicao >= 0 && posicao < tamanho * tamanho && estaLivre(x, y)) {
            tabuleiro[x][y] = jogador;
            return true; 
        } else {
            return false; 
        }
    }

    public boolean verificaVencedor() {
        // Verifica linhas
        for (int y = 0; y < tamanho; y++) {
            if (tabuleiro[0][y] == tabuleiro[1][y] && tabuleiro[1][y] == tabuleiro[2][y] && tabuleiro[0][y] != 0) {
                return true; 
            }
        }

        // Verifica colunas
        for (int x = 0; x < tamanho; x++) {
            if (tabuleiro[x][0] == tabuleiro[x][1] && tabuleiro[x][1] == tabuleiro[x][2] && tabuleiro[x][0] != 0) {
                return true; 
            }
        }

        // Verifica diagonais
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0] != 0) {
            return true; 
        }
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2] != 0) {
            return true; 
        }

        return false; 
    }

    public boolean jogoEmpatado() {
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                if (tabuleiro[x][y] == 0) {
                    return false; 
                }
            }
        }
        return true; 
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        for (int y = 0; y < tamanho; y++) {
            for (int x = 0; x < tamanho; x++) {
                if (tabuleiro[x][y] == -1) {
                    r.append(" X");
                } else if (tabuleiro[x][y] == 1) {
                    r.append(" O");
                } else {
                    r.append("  ");
                }
                if (x < tamanho - 1) {
                    r.append(" |");
                }
            }
            r.append("\n");
            if (y < tamanho - 1) {
                r.append("---+---+---\n");
            }
        }
        return r.toString();
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }
}
