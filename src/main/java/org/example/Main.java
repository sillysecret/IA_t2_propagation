package org.example;

import org.example.operacao.Algoritimo;

public class Main {
    public static void main(String[] args) {
        // Chama o algoritmo genético diretamente no console
        Algoritimo.setN(9);
        Algoritimo.setTaxaDeCrossover(0.9);
        Algoritimo.setTaxaDeMutacao(0.05);
        Algoritimo.setNumeroMaximoGeracoes(5000000);
        Algoritimo.setTamanhoPopulacao(250);
        Algoritimo.setElitismo(true);

        // Executa o algoritmo genético
        Algoritimo.AG();

        // Exibe algum feedback no console após a execução, se necessário
        System.out.println("Algoritmo genético executado com sucesso!");
    }
}
