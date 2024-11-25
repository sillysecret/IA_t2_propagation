package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    public double[] weights;
    public double bias;

    public Neuron(double[] weights, double bias) {
        this.weights = weights;
        this.bias = bias;
    }

    // Função de ativação (ReLU)
    public double activate(double input) {
        return Math.max(0,input); // vai pegar no intervalo de [1,+infinito) pra ver qual o neuronio deve ser ativado
    }

    // Calcula a saída do neurônio dado as entradas
    public double forward(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        sum += bias; // Inclui o bias
        return activate(sum); // Passa pela função de ativação
    }
}