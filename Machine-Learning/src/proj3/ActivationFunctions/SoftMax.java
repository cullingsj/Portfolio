package proj3.ActivationFunctions;

import proj3.Network;
import proj3.Node;
import proj3.recycled.DataPoint;

/**
 * Created by pryor on 11/7/2019.
 */
public class SoftMax extends ActivationFunction {
    @Override
    public double value(double in, Node n, DataPoint d) {
        double numerator = Math.exp(dot(n, d));
        double denom = 0;

        Node[][] net = n.network.net;

        for (Node x : net[net.length - 1]) {
            denom += Math.exp(dot(x, d));
        }

        return numerator / denom;
    }

    @Override
    public double derivative(double in, Node n, DataPoint d) {
        return in * (1 - in); // Shouldn't be used but just in case use Logistic derivative
    }
}
