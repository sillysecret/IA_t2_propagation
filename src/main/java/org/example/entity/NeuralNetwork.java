package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
    public List<Layer> layers;

    public NeuralNetwork(double[] args) {
        int index = 0;
        List<Layer> layers = new ArrayList<>();


        List<Neuron> hiddenNeurons = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            double[] weights = new double[9];
            double bias = args[index++];
            for (int j = 0; j < 9; j++) {
                weights[j] = args[index++];
            }
            hiddenNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(hiddenNeurons));


        List<Neuron> outputNeurons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double[] weights = new double[12];
            double bias = args[index++];
            for (int j = 0; j < 12; j++) {
                weights[j] = args[index++];
            }
            outputNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(outputNeurons));

        this.layers = layers;
    }


    public int forward(double[] inputs) {
        double[] outputs = inputs;
        for (Layer layer : layers) {
            outputs = layer.forward(outputs);
        }

        var biggestOneIndex = 0;
        for (int i = 0; i < 9; i++) {
            if (outputs[i] > outputs[biggestOneIndex]) {
                biggestOneIndex = i;
            }
        }

        return biggestOneIndex;
    }
}
