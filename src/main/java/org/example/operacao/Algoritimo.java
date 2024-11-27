package org.example.operacao;

import org.example.dominio.Individuo;
import org.example.dominio.Populacao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
                dificuldade = 2;

            if (geracao == (int)(getNumeroMaximoGeracoes() * 0.75))
                dificuldade = 4;

            if(geracao % 500 == 0){
                //armazena os melhores pesos em um arquivo para serem usados posteriormente
                double[] melhor = populacao.getIndivduo(0).getPesos();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\vizzo\\Downloads\\IA_trab\\propagation\\src\\main\\java\\org\\example\\operacao\\melhores_pesos.txt", true))) {
                    writer.write("Geração " + geracao + ":");
                    writer.newLine();
                    writer.write("Aptidaão do Melhor: " + populacao.getIndivduo(0).getAptidao());
                    writer.newLine();
                    writer.write(Arrays.toString(melhor));
                    writer.newLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(geracao % 2000 == 0){
                for (int i = 0; i < populacao.getTamPopulacao(); i++) {
                    double[] melhor = populacao.getIndivduo(i).getPesos();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\vizzo\\Downloads\\IA_trab\\propagation\\src\\main\\java\\org\\example\\operacao\\melhores_pesos.txt", true))) {
                        writer.write("Geração " + geracao + ":");
                        writer.newLine();
                        writer.write("Aptidaão individuo "+i+": " + populacao.getIndivduo(i).getAptidao());
                        writer.newLine();
                        writer.write(Arrays.toString(melhor));
                        writer.newLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            System.out.println("Geração " + geracao + ":");
            System.out.println("Melhor: " + populacao.getIndivduo(0).getAptidao() + " (" + populacao.getIndivduo(0).getAptidao() + ")");
            System.out.println("Média: " + populacao.getMediaAptidao());
            System.out.println("Pior: " + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + " (" + populacao.getIndivduo(populacao.getNumIndividuos() - 1).getAptidao() + ")");
            for (int i = 0; i < populacao.getTamPopulacao(); i++) {
                System.out.print(populacao.getIndivduo(i).getAptidao() + " ");
            }
            System.out.println();
            System.out.println("-------------------------------------");

//            if (populacao.getMediaAptidao() == 0) {
//                System.out.println("Melhor indivíduo: " + Arrays.toString(populacao.getIndivduo(0).getPesos()));
//                break;
//            }


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
                mutacao(filho, geracao, getNumeroMaximoGeracoes());
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
        double[] pesosFilho1 = new double[180];

        for (int i = 0; i < 180; i++) {
            pesosFilho1[i] = (cromossomo1[i] + cromossomo2[i]) / 2 + r.nextGaussian() * 0.1;
        }

        filhos.setPesos(pesosFilho1);

        filhos.geraAptidao(dificuldade);
        return filhos;
    }

    public static Individuo selecaoTorneio(Populacao populacao, int geracao) {
        Random random = new Random();
        int tamanhoPopulacao = populacao.getTamPopulacao();
        int limite;

        // Definir limite de seleção baseado na geração
        if (geracao < getNumeroMaximoGeracoes() * 0.25) {
            limite = tamanhoPopulacao;
        } else if (geracao < getNumeroMaximoGeracoes() * 0.75) {
            limite = random.nextDouble() < 0.3 ? tamanhoPopulacao : tamanhoPopulacao / 2;
        } else {
            limite = random.nextDouble() < 0.3 ? tamanhoPopulacao : tamanhoPopulacao / 4;
        }

        // Selecionar dois indivíduos distintos
        int indice1 = random.nextInt(limite);
        int indice2;
        do {
            indice2 = random.nextInt(limite);
        } while (indice2 == indice1);

        Individuo individuo1 = populacao.getIndivduo(indice1);
        Individuo individuo2 = populacao.getIndivduo(indice2);

        // Retornar o indivíduo com maior aptidão
        return (individuo1.getAptidao() >= individuo2.getAptidao()) ? individuo1 : individuo2;
    }

    public static Individuo mutacao(Individuo individuo, int geracaoAtual, int numeroMaximoGeracoes) {
        Random r = new Random();
        double[] cromossomo = individuo.getPesos();


        double intensidadeMutacao = 1000 - (double) geracaoAtual / numeroMaximoGeracoes;

        for (int i = 0; i < cromossomo.length; i++) {
            if (r.nextDouble() < getTaxaDeMutacao()) {
                double perturbacao = r.nextGaussian() * intensidadeMutacao * cromossomo[i];
                cromossomo[i] += perturbacao;
            }
        }

        individuo.setPesos(cromossomo);
        individuo.geraAptidao(dificuldade);
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
