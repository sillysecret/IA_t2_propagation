package dominio;

import java.util.Random;
import org.example.entity.NeuralNetwork;
import tabuleiro.Tabuleiro;

public class Individuo {

    private double aptidao;
    private double[] pesos;

    // Construtor da classe Individuo, inicializando a rede neural e os pesos
    public Individuo(boolean redeNeuralAleatoria) {
        pesos = new double[180];  // Cromossomo com 180 pesos
        Random random = new Random();

        // Inicializa os pesos da rede neural aleatoriamente
        if (redeNeuralAleatoria) {
            for (int i = 0; i < pesos.length; i++) {
                pesos[i] = random.nextDouble() * 2 - 1; // Valores entre -1 e 1
            }
        }

        if (redeNeuralAleatoria) {
            geraAptidao();  // Chama a função para calcular a aptidão
        }
    }

    // Calcula a aptidão do indivíduo, simulando uma partida
    public void geraAptidao() {
        this.aptidao = calcularAptidao();
    }

    // Função que avalia a aptidão, simulando uma partida de Jogo da Velha
    public double calcularAptidao() {

        NeuralNetwork network = new NeuralNetwork(pesos); 
        Minimax minimax = new Minimax(); //tem que fazer
        Tabuleiro tabuleiro = new Tabuleiro(3);  

        // Simulação de uma partida até o final
        while (!tabuleiro.verificaVencedor() && !tabuleiro.jogoEmpatado()) {
            // Jogada do jogador 1 (usando Minimax para calcular a jogada)
            int jogada1 = minimax.play(tabuleiro.getTabuleiro(), 1);  // Jogador X (1)
            boolean resultado1 = tabuleiro.jogada(jogada1, 1);


            // Verifica vitória após a jogada
            if (tabuleiro.verificaVencedor()) {
                this.aptidao = 1;  
                break;
            }

            // Jogada do jogador 2 (oponente -1)
            int jogada2 = minimax.play(tabuleiro.getTabuleiro(), -1);  // Jogador O (-1)
            boolean resultado2 = tabuleiro.jogada(jogada2, -1);
            if (!resultado2) {
                this.aptidao = -100;  
                break;
            }

            if (tabuleiro.verificaVencedor()) {
                this.aptidao = -1;  
                break;
            }
        }

        // Se o jogo terminar empatado
        if (!tabuleiro.verificaVencedor() && tabuleiro.jogoEmpatado()) {
            this.aptidao = 0;  // Empate
        }

        return this.aptidao;  
    }


    public double getAptidao() {
        return aptidao;
    }

    public double[] getPesos() {
        return pesos;
    }
}
