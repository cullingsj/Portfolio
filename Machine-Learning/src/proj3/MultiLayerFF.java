package proj3;

import proj3.ActivationFunctions.ActivationFunction;
import proj3.ActivationFunctions.LinearFunction;
import proj3.ActivationFunctions.SoftMax;
import proj3.recycled.DataPoint;

/**
 * Created by pryor on 11/6/2019.
 */
public class MultiLayerFF extends Network {
    public MultiLayerFF(int inputs, int[] hiddenLayers, int outputs, ActivationFunction function, double learningRate) {
        super(inputs, hiddenLayers, outputs, function, (outputs == 1) ? new LinearFunction() : new SoftMax(), //If regression use linear activation for output
                learningRate);

        //Adds a bias node to every layer except output
        for (int i = 0; i < net.length - 1; i++) {
            addBiasNode(i);
        }
    }

    @Override
    public void train(DataPoint[] data){
        for (DataPoint dp : data) {
            Node[] inputNodes = net[0];

            for(Node[] layer : net){
                for (Node node : layer) {
                    node.reset();
                }
            }

            for (Node node : inputNodes) {
                node.backprop(this.learningRate, dp);
            }
        }
    }
}
