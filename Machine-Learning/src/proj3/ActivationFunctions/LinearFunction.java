package proj3.ActivationFunctions;

import proj3.Node;
import proj3.recycled.DataPoint;

/**
 * Created by pryor on 11/6/2019.
 */
public class LinearFunction extends ActivationFunction{
    @Override
    public double value(double in, Node n, DataPoint dataPoint) {
        return in;
    }

    @Override
    public double derivative(double in, Node n, DataPoint dataPoint) {
        return in;
    }
}
