package proj3.ActivationFunctions;


import proj3.Node;
import proj3.recycled.DataPoint;

/**
 * @author Elliott Pryor on 10/21/2019.
 * @project Machine_Learning
 */
public abstract class ActivationFunction {

    public ActivationFunction() {
    }

    /**
     * Calculates the activation of a neuron
     * @param in
     * @param n
     * @param dataPoint
     * @return
     */
    public abstract double value(double in, Node n, DataPoint dataPoint);

    /**
     * Calculates the derivative of the neuron activation
     * @param in
     * @param n
     * @param dataPoint
     * @return
     */
    public abstract double derivative(double in, Node n, DataPoint dataPoint);

    public double dot(Node n, DataPoint d){
        double sum = 0;
        for(int i=0; i < n.upstreamNodes.length; i++) {
            //Gets the weight corresponding to this node from the upstream node
            sum += n.upstreamNodes[i].weights[n.upstreamNodes[i].getIndex(n)] * n.upstreamNodes[i].genOutput(d);
        }
        return sum;
    }


}
