package org.example;

import org.example.entity.Layer;
import org.example.entity.NeuralNetwork;
import org.example.entity.Neuron;

import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[189];
            int index = 0;

            // Pesos camada oculta
            for (int i = 0; i < 90; i++){
                if(i%10 == 0){
                    // Bias
                    args[index++] = "0.1";
                }else{
                    //pesos
                    args[index++] = "0.2";
                }
            }
            //valores da de saida
            for (int i = 0; i < 90; i++){
                if(i%10 == 0){
                    // Bias
                    args[index++] = "0.1";
                }else{
                    //pesos
                    args[index++] = "0.2";
                }
            }
            // Valores de entrada
            for (int i = 0; i < 9; i++) args[index++] = "1.0";
        }

        if (args.length != 189) {
            System.out.println("Por favor, forneça exatamente 189 parâmetros: 81 pesos e 9 biases para a camada oculta, 81 pesos e 9 biases para a camada de saída, e 9 valores de entrada.");
            return;
        }

        int index = 0;
        List<Layer> layers = new ArrayList<>();

        // Cria a camada oculta com 9 neurônios, cada um com 9 pesos e 1 bias
        List<Neuron> hiddenNeurons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double[] weights = new double[9];
            double bias = Double.parseDouble(args[index++]);
            for (int j = 0; j < 9; j++) {
                weights[j] = Double.parseDouble(args[index++]);
            }
            hiddenNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(hiddenNeurons));

        // Cria a camada de saída com 9 neurônios, cada um com 9 pesos e 1 bias
        List<Neuron> outputNeurons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            double[] weights = new double[9];
            double bias = Double.parseDouble(args[index++]);
            for (int j = 0; j < 9; j++) {
                weights[j] = Double.parseDouble(args[index++]);
            }
            outputNeurons.add(new Neuron(weights, bias));
        }
        layers.add(new Layer(outputNeurons));

        NeuralNetwork network = new NeuralNetwork(layers);

        // Entrada de teste com 9 valores
        double[] inputs = new double[9];
        for (int i = 0; i < 9; i++) {
            inputs[i] = Double.parseDouble(args[index++]);
        }

        // Calcula a saída
        double[] outputs = network.forward(inputs);

        // Exibe a saída
        System.out.println("Saída da rede:");
        for (double output : outputs) {
            System.out.println(output);
        }
    }
}