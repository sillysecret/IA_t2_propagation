package org.example.entity;

import java.util.List;

public class NeuralNetwork {
    public List<Layer> layers;

    public NeuralNetwork(List<Layer> layers) {
        this.layers = layers;
    }

    // Propaga as entradas pela rede
    public double[] forward(double[] inputs) {
        double[] outputs = inputs;
        //pega a primeira camada e passa os inputs
        for (Layer layer : layers) {
            outputs = layer.forward(outputs);
        }
        return outputs;
    }
}