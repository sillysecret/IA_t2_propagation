package org.example;

import org.example.operacao.Algoritimo;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ihm.Frame frame = new ihm.Frame();
                // Chama o algoritmo genético
                Algoritimo.setN(9);
                Algoritimo.setTaxaDeCrossover(0.7);
                Algoritimo.setTaxaDeMutacao(0.01);
                Algoritimo.setNumeroMaximoGeracoes(1000);
                Algoritimo.setTamanhoPopulacao(50);
                Algoritimo.setElitismo(true);
                
                Algoritimo.AG(frame);
            }
        });
    }
}
