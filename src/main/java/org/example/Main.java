package org.example;

import org.example.entity.TicTacToeAI;
import org.example.operacao.Algoritimo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Algoritimo.setTaxaDeCrossover(0.9);
        Algoritimo.setTaxaDeMutacao(0.5);
        Algoritimo.setNumeroMaximoGeracoes(100);
        Algoritimo.setTamanhoPopulacao(50);
        //dificuldade = 5(media), dificuldade = 3(fácil), dificuldade = 9(difícil)
        Algoritimo.setDificuldade(5);
        Algoritimo.setElitismo(true);

        double[] melhoresPesos = Algoritimo.AG();

        System.out.println("Algoritmo genético executado com sucesso!");

        System.out.println("pessos do melhor: " + Arrays.toString(melhoresPesos));

        System.out.println("Jogar contra o minimax (1) ou contra a rede neural (2) ou passar pessos a serem usados (3)?");
        Scanner scanner = new Scanner(System.in);
        int escolha = scanner.nextInt();

        if (escolha == 1) {
            TicTacToeAI.playGameAgainstMinMax();
        } else if(escolha == 2){
            TicTacToeAI.playGameAgainstMLP(melhoresPesos);
        } else if (escolha == 3) {
            System.out.println("passar os pesos separados por ,:");
            scanner.next();
            String pessos = scanner.nextLine();

            String[] pesosString = pessos.split(",");

            double[] pesos = new double[pesosString.length];
            for (int i = 0; i < pesosString.length; i++)
                pesos[i] = Double.parseDouble(pesosString[i]);

            TicTacToeAI.playGameAgainstMLP(pesos);

        }

    }
}
