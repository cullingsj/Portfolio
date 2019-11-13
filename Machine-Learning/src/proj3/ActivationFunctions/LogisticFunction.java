package proj3.ActivationFunctions;

import proj3.Node;
import proj3.recycled.DataPoint;

/**
 * Created by pryor on 11/5/2019.
 */
public class LogisticFunction extends ActivationFunction {
    @Override
    public double value(double in, Node n, DataPoint d) {
        return (1 / (1 + Math.exp(-in)));
    }

    @Override
    public double derivative(double in, Node n, DataPoint dataPoint) {
        return in * (1 - in);
    }
}
