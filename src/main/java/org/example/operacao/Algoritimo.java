package org.example.operacao;

import org.example.dominio.Individuo;
import org.example.dominio.Populacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Algoritimo {

    private static double taxaDeCrossover;
    private static double taxaDeMutacao;
    private static int numeroMaximoGeracoes;
    private static int tamanhoPopulacao;
    private static boolean elitismo;

    private static int dificuldade;
    public static double[] AG() {
        // Executa o algoritmo genético diretamente, sem interface gráfica
        int geracao = 1;

        Populacao populacao = new Populacao(getTamanhoPopulacao(), true,dificuldade);
        populacao.ordenaPopulacao();

        System.out.println("Geração " + geracao + ":");
        System.out.println("Melhor: " + populacao.getIndivduo(0).getAptidao() + " (" + populacao.getIndivduo(0).getAptidao() + ")");
        System.out.println("Média: " + populacao.getMediaAptidao());
        System.out.println("Pior: " + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + " (" + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + ")");
        System.out.println("-------------------------------------");

        while (geracao < getNumeroMaximoGeracoes()) {
            geracao++;

            populacao = Algoritimo.novaGeracao(populacao,geracao);

            if(geracao == getNumeroMaximoGeracoes()/2)
                dificuldade = 3;

            if (geracao == (int)(getNumeroMaximoGeracoes() * 0.75))
                dificuldade = 9;

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
                System.out.println("Melhor indivíduo: " + Arrays.toString(populacao.getIndivduo(0).getPesos()));
                break;
            }


        }
        System.out.println("Melhor indivíduo: " + Arrays.toString(populacao.getIndivduo(0).getPesos()));
        return populacao.getIndivduo(0).getPesos();
    }

    public static Populacao novaGeracao(Populacao populacao, int geracao) {
        Random r;
        Populacao novaPopulacao = new Populacao(populacao.getTamPopulacao(), false,dificuldade);

        if (isElitismo()) {
            novaPopulacao.setIndividuo(populacao.getIndivduo(0));
        }

        for (int i = 0; i < novaPopulacao.getTamPopulacao(); i++) {

            Individuo[] pais = new Individuo[2];
            Individuo filho;

            pais[0] = selecaoTorneio(populacao, geracao);
            pais[1] = selecaoTorneio(populacao, geracao);

            r = new Random();
            if (r.nextDouble() <= taxaDeCrossover) {
                filho = Algoritimo.crossover(pais[0], pais[1]);
            } else {
                filho = pais[0];
            }

            // Mutação a cada 100 indivíduos
            if (i % 25 == 0) {
                mutacao(filho);

            }

            novaPopulacao.setIndividuo(filho);
        }

        novaPopulacao.ordenaPopulacao();
        return novaPopulacao;
    }

    // Média Geométrica (crossover)
    public static Individuo crossover(Individuo pai1, Individuo pai2) {
        Random r = new Random();
        Individuo filhos = new Individuo(false,dificuldade);

        double[] cromossomo1 = pai1.getPesos();
        double[] cromossomo2 = pai2.getPesos();
        double[] pesosFilho1 = new double[237];


        for (int i = 0; i < 237; i++) {
            // Calculando a média geométrica para cada gene
            pesosFilho1[i] = Math.sqrt(cromossomo1[i] * cromossomo2[i]);

        }

        filhos.setPesos(pesosFilho1);

        filhos.geraAptidao(dificuldade);
        return filhos;
    }


    public static Individuo selecaoTorneio(Populacao populacao,int geracao) {
        Random r = new Random();
        Populacao populacaoIntermediaria = new Populacao(2, false,dificuldade);
        int limite;
        int indice;
        if(geracao > getNumeroMaximoGeracoes()/2){
            limite = populacao.getTamPopulacao();
            indice = r.nextInt(limite);
        }else{
            limite = r.nextDouble() < 0.3 ? populacao.getTamPopulacao() : populacao.getTamPopulacao() / 4;
            indice = r.nextInt(limite);
        }
        populacaoIntermediaria.setIndividuo(populacao.getIndivduo(indice));

        populacaoIntermediaria.setIndividuo(populacao.getIndivduo(indice));

        populacaoIntermediaria.ordenaPopulacao();

        int pos;

        r = new Random();
        if (populacaoIntermediaria.getIndivduo(0).getAptidao() >= populacaoIntermediaria.getIndivduo(1).getAptidao()) {
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
            cromossomo[i] += r.nextGaussian() * 100;
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

    public static void setDificuldade(int dificuldade) {
        Algoritimo.dificuldade = dificuldade;
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
