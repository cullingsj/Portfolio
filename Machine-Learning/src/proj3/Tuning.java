package proj3;


import proj3.ActivationFunctions.ActivationFunction;
import proj3.recycled.DataPoint;
import proj3.recycled.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Elliott Pryor on 11/3/2019.
 * @project Machine_Learning
 */
public class Tuning {

    public static String retMe = "";

    /**
     * Tunes a Feed Forward Network
     * This infers the number of inputs and outputs from the dataset
     * @param nodeSearchParams The first parameter is the maximum number of layers, second is the lower bound of nodes,
     *                         The third is the upper bound of nodes, the fourth is the increment between
     * @param learningRates The learning rates to search
     * @param trainData Data to train the model
     * @param testData Data to test the model
     * @param function The activation function used for all the nodes
     * @return The tuned model
     */
    public static MultiLayerFF tuneFFNetwork(int[] nodeSearchParams, double[] learningRates,
                                        DataPoint[] trainData, DataPoint[] testData, ActivationFunction function){
        int inputs = trainData[0].getNumberOfFeatures();
        int outputs = trainData[0].getTarget().length;

        int[][][] options = buildLayers(nodeSearchParams[0], nodeSearchParams[1], nodeSearchParams[2], nodeSearchParams[3]);
        double best = Double.NEGATIVE_INFINITY;
        MultiLayerFF tunedModel = null;

        for (int layer = 0; layer < nodeSearchParams[0]; layer++) {
            for (double rate: learningRates) {
                for (int i = 0; i < options[layer].length; i++) {
                    MultiLayerFF model = new MultiLayerFF(inputs, options[layer][i], outputs, function, rate);

                    model.train(trainData, .01);

                    double val = 0;
                    for (DataPoint d : testData){
                        if(d.getTarget().length == 1){ //Is regression, so use squared error
                            val -= Math.pow((d.getTarget()[0] - model.predict(d)[0]), 2); // Subtract because we want to maximize negative error
                        } else{ //Is classification so use accuracy
                            if(Utils.argMax(model.predict(d)) == Utils.argMax(d.getTarget())){
                                val += 1;
                            }
                        }
                    }


                    if(val > best){ //Set the new model to this one because it is better
                        System.out.printf("Layers: %d, Learning rate: %f, Layer Plan: %s \n", layer, rate, Arrays.toString(options[layer][i]));
                        System.out.println("Acc: " + val/testData.length);

                        retMe = "" + layer + "," + Arrays.toString(options[layer][i]) + ",";

                        tunedModel = model;
                        best = val;
                    }

                }
            }
        }
        return tunedModel;
    }


    /**
     * Utitlity function that builds an array of all possible hidden node + layer configurations
     * @param layers
     * @param lower
     * @param upper
     * @param inc
     * @return
     */
    private static int[][][] buildLayers(int layers, int lower, int upper, int inc){
        int[] base = new int[(upper - lower)/inc];
        for (int i = 0; i < base.length; i++) {
            base[i] = lower + i * inc;
        }

        int[][][] allOptions = new int[layers + 1][][]; //First index is layer number, second is a counter, the last array stores the configuration
        allOptions[0] = new int[][] {{}}; //No hidden layer
        allOptions[1] = new int[base.length][1]; //1 hidden layer
        for (int i = 0; i < base.length; i++) {
            allOptions[1][i][0] = base[i];
        }

        for (int i = 2; i < layers; i++) {
            ArrayList<int[]> layer = new ArrayList<>();
            int[][] prev = allOptions[i - 1];

            for (int j = 0; j < prev.length; j++) {
                for (int k = 0; k < base.length; k++) {
                    layer.add(append(base[k], prev[j]));
                }
            }
            allOptions[i] = flatten(layer);
        }
        return allOptions;
    }

    private static int[][] flatten(ArrayList<int[]> l){
        int[][] retMe = new int[l.size()][];
        for (int i = 0; i < retMe.length; i++) {
            retMe[i] = l.get(i);
        }
        return retMe;
    }

    private static int[] append(int val, int[] arr){
        int[] retMe = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            retMe[i] = arr[i];
        }
        retMe[retMe.length - 1] = val;
        return retMe;
    }


}
