package org.example.dominio;

import java.util.Random;


import org.example.entity.NeuralNetwork;
import org.example.entity.TicTacToeAI;


public class Individuo {

    private double aptidao;
    private double[] pesos;
    private Tabuleiro tabuleiro;

    public Individuo(boolean redeNeuralAleatoria, int difficultyLevel) {
        pesos = new double[237];
        Random random = new Random();

        // Inicializa os pesos da rede neural aleatoriamente
        if (redeNeuralAleatoria) {
            for (int i = 0; i < pesos.length; i++) {
                pesos[i] = random.nextInt(1000) ;
            }
        }
        if (redeNeuralAleatoria) {
            geraAptidao(difficultyLevel);
        }
    }

    // Calcula a aptidão do indivíduo, simulando uma partida
    public void geraAptidao(int difficultyLevel) {
        this.aptidao = calcularAptidao(difficultyLevel);
    }

    public double calcularAptidao(int difficultyLevel) {
        NeuralNetwork network = new NeuralNetwork(pesos);
        Tabuleiro tabuleiro = new Tabuleiro();
        int numeroDeJogadas = 0;
        int minimaxTurns = 0;
        int movesPerCycle = 4; // Every cycle has 4 moves

        while (!tabuleiro.verificaVencedor() && !tabuleiro.jogoEmpatado()) {
            int jogada1 = network.forward(tabuleiro.getArrayTabuleiro());
            boolean resultado1 = tabuleiro.jogada(jogada1, 1);
            numeroDeJogadas++;

            if (!resultado1) {
                this.aptidao = -18 + numeroDeJogadas;
                break;
            }

            if (tabuleiro.verificaVencedor()) {
                this.aptidao = 1;
                break;
            }

            int jogada2;
            if (minimaxTurns < difficultyLevel) {
                jogada2 = TicTacToeAI.findBestMove(tabuleiro.getArrayTabuleiroInt());
                minimaxTurns++;
            } else {
                Random random = new Random();
                do {
                    jogada2 = random.nextInt(9);
                } while (!tabuleiro.estaLivre(jogada2));
            }

            boolean resultado2 = tabuleiro.jogada(jogada2, -1);
            numeroDeJogadas++;

            if (numeroDeJogadas % movesPerCycle == 0) {
                minimaxTurns = 0;
            }

            if (tabuleiro.verificaVencedor()) {
                this.aptidao = -1 + numeroDeJogadas * 0.1;
                break;
            }
        }

        if (tabuleiro.jogoEmpatado()) {
            this.aptidao = 0;
        }

        this.tabuleiro = tabuleiro;
        return this.aptidao;
    }



    public double getAptidao() {
        return aptidao;
    }

    public void setPesos(double[] pesos) {
        this.pesos = pesos;
    }
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public double[] getPesos() {
        return pesos;
    }
}
