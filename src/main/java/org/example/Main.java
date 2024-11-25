package org.example;

import org.example.entity.TicTacToeAI;
import org.example.operacao.Algoritimo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Chama o algoritmo genético diretamente no console
        Algoritimo.setTaxaDeCrossover(0.9);
        Algoritimo.setTaxaDeMutacao(0.5);
        Algoritimo.setNumeroMaximoGeracoes(500);
        Algoritimo.setTamanhoPopulacao(150);
        //dificuldade = 5(media), dificuldade = 3(fácil), dificuldade = 9(difícil)
        Algoritimo.setDificuldade(1);
        Algoritimo.setElitismo(true);

        // Executa o algoritmo genético
        double[] melhoresPesos = Algoritimo.AG();

        // Exibe algum feedback no console após a execução, se necessário
        System.out.println("Algoritmo genético executado com sucesso!");

        System.out.println("pessos do melhor: " + Arrays.toString(melhoresPesos));

        System.out.println("Jogar contra o minimax (1) ou contra a rede neural (2)?");
        Scanner scanner = new Scanner(System.in);
        int escolha = scanner.nextInt();

        if (escolha == 1) {
            // Chama o jogo da velha contra o minimax
            TicTacToeAI.playGameAgainstMinMax();
        } else {
            TicTacToeAI.playGameAgainstMLP(melhoresPesos);
        }

    }
}
