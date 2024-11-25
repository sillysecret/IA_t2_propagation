package org.example.dominio;

import java.util.Arrays;
import java.util.Comparator;

// Define uma população
public class Populacao {

    private Individuo[] individuos;
    private int tamPopulacao;

    public Populacao(int tamPop, boolean individuosAleatorios) {
        tamPopulacao = tamPop;
        individuos = new Individuo[tamPop];

        for (int i = 0; i < individuos.length; i++) {
            if (individuosAleatorios) {
                individuos[i] = new Individuo(true); // Inicializa com indivíduos aleatórios
            } else {
                individuos[i] = null; // Espaços vazios na população
            }
        }
    }

    public void setIndividuo(Individuo individuo, int posicao) {
        if (posicao >= 0 && posicao < individuos.length) {
            individuos[posicao] = individuo;
        }
    }

    public void setIndividuo(Individuo individuo) {
        for (int i = 0; i < individuos.length; i++) {
            if (individuos[i] == null) {
                individuos[i] = individuo;
                return;
            }
        }
    }

    public void ordenaPopulacao() {
        Arrays.sort(individuos, Comparator.nullsLast(Comparator.comparingDouble(Individuo::getAptidao).reversed()));
    }

    public int getNumIndividuos() {
        return (int) Arrays.stream(individuos).filter(ind -> ind != null).count();
    }

    public double getMediaAptidao() {
        int numIndividuos = getNumIndividuos();
        if (numIndividuos == 0) {
            return 0.0; // Evita divisão por zero
        }
        return getTotalAptidao() / numIndividuos;
    }

    public double getTotalAptidao() {
        return Arrays.stream(individuos)
                .filter(ind -> ind != null)
                .mapToDouble(Individuo::getAptidao)
                .sum();
    }

    public int getTamPopulacao() {
        return tamPopulacao;
    }

    public Individuo getIndivduo(int pos) {
        if (pos >= 0 && pos < individuos.length) {
            return individuos[pos];
        }
        return null;
    }

    public Individuo[] getIndividuos() {
        return individuos;
    }
}
