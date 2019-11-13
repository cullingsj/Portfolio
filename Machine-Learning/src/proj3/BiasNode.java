package proj3;

import proj3.ActivationFunctions.LinearFunction;
import proj3.recycled.DataPoint;

/**
 * Created by pryor on 11/6/2019.
 */
public class BiasNode extends Node {
    public BiasNode(Network network) {
        super(new LinearFunction(), network);
    }

    @Override
    public double genOutput(DataPoint d) {
        return 1;
    }

}
