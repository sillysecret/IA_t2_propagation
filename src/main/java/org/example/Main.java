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
                    args[index++] = String.valueOf((Math.random() * 2) - 1);
                }else{
                    //pesos
                    args[index++] = String.valueOf((Math.random() * 2) - 1);
                }
            }
            //valores da de saida
            for (int i = 0; i < 90; i++){
                if(i%10 == 0){
                    // Bias
                    args[index++] = String.valueOf((Math.random() * 2) - 1);
                }else{
                    //pesos
                    args[index++] = String.valueOf((Math.random() * 2) - 1);
                }
            }
            // Valores de entrada

            for (int i = 0; i < 9; i++) args[index++] = String.valueOf((Math.random() * 2) - 1);
        }

        if (args.length != 189) {
            System.out.println("forneça exatamente 189 parâmetros: 81 pesos e 9 biases para a camada oculta, 81 pesos e 9 biases para a camada de saída, e 9 valores de entrada.");
            return;
        }
        //troquei para double 
        //NeuralNetwork network = new NeuralNetwork(args);
        int index = args.length - 9;
        // Entrada de teste com 9 valores
        double[] inputs = new double[9];
        for (int i = 0; i < 9; i++) {
            inputs[i] = Double.parseDouble(args[index++]);
        }

        // Calcula a saída
        double[] outputs = network.forward(inputs);

        // Exibe a saída
        int biggestOneIndex = 0;
        System.out.println("Saída da rede:");
        for (int i = 0; i < 9;i++) {
            if((outputs[i])>(outputs[biggestOneIndex]))
                biggestOneIndex = i;

            System.out.println(outputs[i]);
        }

        System.out.println( "chosed neuron: "+ biggestOneIndex + " | value: " + outputs[biggestOneIndex]);
    }
}