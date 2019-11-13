package proj3.ActivationFunctions;

import proj3.Node;
import proj3.recycled.DataPoint;
import proj3.recycled.Utils;

/**
 * Created by pryor on 11/5/2019.
 */
public class GaussianFunction extends ActivationFunction {

    DataPoint center;
    double sigma;

    public GaussianFunction(){} //Just a placeholder should always be replaced later

    public GaussianFunction(DataPoint center, double sigma){
        this.center = center;
        this.sigma = sigma;
    }

    @Override
    public double value(double in, Node n, DataPoint d) {
        if(center == null)
            System.out.println("Null Center");
        return Math.exp(-(Math.pow(Utils.getDistance(center, d), 2) / (2 * Math.pow(sigma, 2))));
    }

    @Override
    public double derivative(double in, Node n, DataPoint dataPoint) {
        return value(in, n, dataPoint);
    }
}
