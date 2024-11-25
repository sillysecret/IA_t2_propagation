package org.example.dominio;

import java.util.Random;


import org.example.entity.NeuralNetwork;
import org.example.entity.TicTacToeAI;


public class Individuo {

    private double aptidao;
    private double[] pesos;
    private Tabuleiro tabuleiro;

    // Construtor da classe Individuo, inicializando a rede neural e os pesos
    public Individuo(boolean redeNeuralAleatoria) {
        pesos = new double[180];  // Cromossomo com 180 pesos
        Random random = new Random();

        // Inicializa os pesos da rede neural aleatoriamente
        if (redeNeuralAleatoria) {
            for (int i = 0; i < pesos.length; i++) {
                pesos[i] = random.nextDouble() * 2; // Valores entre -1 e 1
            }
        }
        if (redeNeuralAleatoria) {
            geraAptidao();
        }
    }

    // Calcula a aptidão do indivíduo, simulando uma partida
    public void geraAptidao() {
        this.aptidao = calcularAptidao();
    }

    // Função que avalia a aptidão, simulando uma partida de Jogo da Velha
    public double calcularAptidao() {

        NeuralNetwork network = new NeuralNetwork(pesos); 
        TicTacToeAI minimax = new TicTacToeAI();
        Tabuleiro tabuleiro = new Tabuleiro();

        int numeroDeJogadas = 0;
        // Simulação de uma partida até o final
        while (!tabuleiro.verificaVencedor() && !tabuleiro.jogoEmpatado()) {
            //usa rede para fazer a jogada
            int jogada1 = network.forward(tabuleiro.getArrayTabuleiro());  // redeneural X (1)

            boolean resultado1 = tabuleiro.jogada(jogada1, 1);
            numeroDeJogadas++;

            if (!resultado1) {
                this.aptidao = -18 + numeroDeJogadas;
                break;
            }

            // Verifica vitória após a jogada
            if (tabuleiro.verificaVencedor()) {
                this.aptidao = 1;  
                break;
            }

            if(numeroDeJogadas == 1 || numeroDeJogadas == 3){
                // Jogada do jogador 2 (oponente -1)
                int jogada2 = TicTacToeAI.findBestMove(tabuleiro.getArrayTabuleiroInt());  // minimax O (-1)
                boolean resultado2 = tabuleiro.jogada(jogada2, -1);
            }else {
                //usa um numero aleatorio para fazer a jogada entre 0 a 8;
                Random random = new Random();
                int jogada2 = random.nextInt(9);
                boolean resultado2 = tabuleiro.jogada(jogada2, -1);
            }



            numeroDeJogadas++;

            //verifica se o minimax venceu apos a jogada
            if (tabuleiro.verificaVencedor()) {
                this.aptidao = -1 + numeroDeJogadas * 0.1;
                break;
            }

        }
        // Se o jogo terminar empatado
        if (!tabuleiro.verificaVencedor() && tabuleiro.jogoEmpatado()) {
            this.aptidao = 0;  // Empate
        }
        //System.out.println(tabuleiro.toString());
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
