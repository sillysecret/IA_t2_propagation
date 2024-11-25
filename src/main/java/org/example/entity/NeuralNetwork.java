package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    public List<Layer> layers;

    public NeuralNetwork(double[] args) {
        int index = 0;
        List<Layer> layers = new ArrayList<>();

        // Cria a camada oculta com 9 neurônios, cada um com 9 pesos e 1 bias
        List<Neuron> hiddenNeurons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double[] weights = new double[9];
            double bias = args[index++];
            for (int j = 0; j < 9; j++) {
                weights[j] = args[index++];
            }
            hiddenNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(hiddenNeurons));

        // Cria a camada de saída com 9 neurônios, cada um com 9 pesos e 1 bias
        List<Neuron> outputNeurons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double[] weights = new double[9];
            double bias = args[index++];
            for (int j = 0; j < 9; j++) {
                weights[j] = args[index++];
            }
            outputNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(outputNeurons));

        this.layers = layers;
    }

    // Propaga as entradas pela rede
    public int forward(double[] inputs) {
        double[] outputs = inputs;
        //pega a primeira camada e passa os inputs
        for (Layer layer : layers) {
            outputs = layer.forward(outputs);
        }

        //pega o index do maior valor
        var biggestOneIndex = 0;
        for (int i = 0; i < 9; i++) {
            if (outputs[i] > outputs[biggestOneIndex]) {
                biggestOneIndex = i;
            }
        }

        return biggestOneIndex;
    }
}