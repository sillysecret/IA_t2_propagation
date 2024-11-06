package org.example.entity;

import org.example.entity.Neuron;

import java.util.List;

public class Layer {
    public List<Neuron> neurons;

    public Layer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    // Calcula a saída da camada dada as entradas
    public double[] forward(double[] inputs) {
        double[] outputs = new double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).forward(inputs);
        }
        return outputs;
    }
}