package org.example.operacao;

import org.example.dominio.Individuo;
import org.example.dominio.Populacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Algoritimo {

    private static int N;
    private static double taxaDeCrossover;
    private static double taxaDeMutacao;
    private static int numeroMaximoGeracoes;
    private static int tamanhoPopulacao;
    private static boolean elitismo;

    public static void AG() {
        // Executa o algoritmo genético diretamente, sem interface gráfica
        int geracao = 1;

        Populacao populacao = new Populacao(getTamanhoPopulacao(), true);
        populacao.ordenaPopulacao();

        System.out.println("Geração " + geracao + ":");
        System.out.println("Melhor: " + populacao.getIndivduo(0).getAptidao() + " (" + populacao.getIndivduo(0).getAptidao() + ")");
        System.out.println("Média: " + populacao.getMediaAptidao());
        System.out.println("Pior: " + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + " (" + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + ")");
        System.out.println("-------------------------------------");

        while (geracao < getNumeroMaximoGeracoes()) {
            geracao++;

            populacao = Algoritimo.novaGeracao(populacao);

            System.out.println("Geração " + geracao + ":");
            System.out.println("Melhor: " + populacao.getIndivduo(0).getAptidao() + " (" + populacao.getIndivduo(0).getAptidao() + ")");
            //System.out.println("Pessos do melhor: " + Arrays.toString(populacao.getIndivduo(0).getPesos()));
            System.out.println("Média: " + populacao.getMediaAptidao());
            System.out.println("Pior: " + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + " (" + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + ")");
            for (int i = 0; i < populacao.getTamPopulacao(); i++) {
                System.out.print(populacao.getIndivduo(i).getAptidao() + " ");
            }
            System.out.println("-------------------------------------");

            if (populacao.getMediaAptidao() == 0) {
                break;
            }
        }
    }

    public static Populacao novaGeracao(Populacao populacao) {
        Random r;
        Populacao novaPopulacao = new Populacao(populacao.getTamPopulacao(), false);

        if (isElitismo()) {
            novaPopulacao.setIndividuo(populacao.getIndivduo(0));
        }

        for (int i = 0; i < novaPopulacao.getTamPopulacao(); i++) {

            Individuo pais[] = new Individuo[2];
            Individuo filhos[] = new Individuo[2];

            pais[0] = selecaoTorneio(populacao);
            pais[1] = selecaoTorneio(populacao);

            r = new Random();
            if (r.nextDouble() <= taxaDeCrossover) {
                filhos = Algoritimo.crossover(pais[0], pais[1]);
                filhos[0].geraAptidao();
                filhos[1].geraAptidao();
            } else {
                filhos[0] = pais[0];
                filhos[1] = pais[1];
            }

            // Mutação a cada 100 indivíduos
            if (i % 100 == 0) {
                mutacao(filhos[0]);
                mutacao(filhos[1]);
            }

            novaPopulacao.setIndividuo(filhos[0]);
            novaPopulacao.setIndividuo(filhos[1]);
        }

        novaPopulacao.ordenaPopulacao();
        return novaPopulacao;
    }

    public static Individuo[] crossover(Individuo pai1, Individuo pai2) {
        Random r = new Random();
        Individuo filhos[] = new Individuo[2];

        filhos[0] = new Individuo(false);
        filhos[1] = new Individuo(false);

        double[] cromossomo1 = pai1.getPesos();
        double[] cromossomo2 = pai2.getPesos();
        double[] mediaGeometrica = new double[180];
        double[] mediaAritmetica = new double[180];
        for (int i = 0; i < 180; i++) {
            mediaGeometrica[i] = Math.sqrt(cromossomo1[i] * cromossomo2[i]);
            mediaAritmetica[i] = (cromossomo1[i] + cromossomo2[i]) / 2;
        }

        filhos[0].setPesos(mediaGeometrica);
        filhos[1].setPesos(mediaAritmetica);

        filhos[0].geraAptidao();
        filhos[1].geraAptidao();

        return filhos;
    }

    public static Individuo selecaoTorneio(Populacao populacao) {
        Random r = new Random();
        Populacao populacaoIntermediaria = new Populacao(2, false);

        populacaoIntermediaria.setIndividuo(populacao.getIndivduo(r.nextInt(populacao.getTamPopulacao())));
        r = new Random();
        populacaoIntermediaria.setIndividuo(populacao.getIndivduo(r.nextInt(populacao.getTamPopulacao())));

        populacaoIntermediaria.ordenaPopulacao();

        int pos;

        r = new Random();
        if (populacaoIntermediaria.getIndivduo(0).getAptidao() > populacaoIntermediaria.getIndivduo(1).getAptidao()) {
            pos = 0;
        } else {
            pos = 1;
        }

        return populacaoIntermediaria.getIndivduo(pos);
    }
    public static Individuo mutacao(Individuo individuo) {
        Random r = new Random();
        double[] cromossomo = individuo.getPesos();

        for (int i = 0; i < cromossomo.length; i++) {
            cromossomo[i] = cromossomo[i] + r.nextGaussian() * taxaDeMutacao;
        }

        individuo.setPesos(cromossomo);
        return individuo;
    }

    public static double getTaxaDeCrossover() {
        return taxaDeCrossover;
    }

    public static void setTaxaDeCrossover(double taxaDeCrossover) {
        Algoritimo.taxaDeCrossover = taxaDeCrossover;
    }

    public static double getTaxaDeMutacao() {
        return taxaDeMutacao;
    }

    public static void setTaxaDeMutacao(double taxaDeMutacao) {
        Algoritimo.taxaDeMutacao = taxaDeMutacao;
    }

    public static int getNumeroMaximoGeracoes() {
        return numeroMaximoGeracoes;
    }

    public static void setNumeroMaximoGeracoes(int numeroMaximoGeracoes) {
        Algoritimo.numeroMaximoGeracoes = numeroMaximoGeracoes;
    }

    public static int getN() {
        return N;
    }

    public static void setN(int N) {
        Algoritimo.N = N;
    }

    public static int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public static void setTamanhoPopulacao(int tamanhoPopulacao) {
        Algoritimo.tamanhoPopulacao = tamanhoPopulacao;
    }

    public static boolean isElitismo() {
        return elitismo;
    }

    public static void setElitismo(boolean elitismo) {
        Algoritimo.elitismo = elitismo;
    }
}
