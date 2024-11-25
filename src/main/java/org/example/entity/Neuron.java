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


    public double activate(double input) {
        return Math.max(0,input);
    }


    public double forward(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        sum += bias;
        return activate(sum);
    }
}