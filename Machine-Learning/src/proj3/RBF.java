package proj3;


import proj3.ActivationFunctions.GaussianFunction;
import proj3.ActivationFunctions.LinearFunction;
import proj3.ActivationFunctions.LogisticFunction;
import proj3.ActivationFunctions.SoftMax;
import proj3.recycled.Centroid;
import proj3.recycled.ClusteringModel;
import proj3.recycled.DataPoint;

/**
 * @author Elliott Pryor on 10/21/2019.
 * @project Machine_Learning
 */
public class RBF extends Network {

    public RBF(ClusteringModel model, double learningRate){
        this(model.getCentroids(), calculateSigmas(model), model.getData()[0].getNumberOfFeatures(),
                model.getData()[0].getTarget().length, learningRate);
    }

    public RBF(DataPoint[] centers, double[] sigmas, int inputs, int outputs, double learningRate){
        super(inputs, new int[]{sigmas.length}, outputs, new GaussianFunction(),
                (outputs == 1) ? new LinearFunction() : new SoftMax(), learningRate);

        //Sets it so that there are no weights on input layer
        Node[] inputLayer = net[0];
        for(Node n : inputLayer){
            for (int i = 0; i < n.weights.length; i++) {
                n.weights[i] = 1.0;
            }
        }

        //Sets the gaussians in hidden layer.
        Node[] hiddenLayer = net[1];
        for (int i = 0; i < hiddenLayer.length; i++){
            hiddenLayer[i].setFunction(new GaussianFunction(centers[i], sigmas[i]));
        }
        addBiasNode(1); // Adds a bias node to the hidden layer

    }

    private static double[] calculateSigmas(ClusteringModel model){
        Centroid[] centroids = model.getCentroids();
        double[] sigmas = new double[centroids.length];
        for (int i = 0; i < centroids.length; i++) {
            sigmas[i] = centroids[i].getDeviation();
            if(sigmas[i] == 0)
                System.out.println("PROBLEM");
        }
        return sigmas;
    }

    @Override
    public void train(DataPoint[] data){
        for (DataPoint dp : data) {
            Node[] inputNodes = net[1]; //Start backprop at hidden layer so not to train input nodes

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
