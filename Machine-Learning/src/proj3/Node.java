package proj3;

import proj3.ActivationFunctions.ActivationFunction;
import proj3.recycled.DataPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.DoubleToLongFunction;
import java.util.zip.DeflaterOutputStream;

/**
 * @author Elliott Pryor on 10/21/2019.
 * @project Machine_Learning
 */
public class Node {
    private Node[] downstreamNodes;
    public Node[] upstreamNodes;
    public Double[] weights;
    private ActivationFunction function;
    public Network network;

    private boolean ranBackprop = false;
    private double prevChange[];
    private double alpha = 0.5;
    private double delta[];
    double sum = 0;

    public Node(ActivationFunction func, Network network){
        downstreamNodes = new Node[0];
        weights = new Double[0];
        upstreamNodes = new Node[0];

        function = func;
        this.network = network;
    }

    /**
     * Adds connection from this node to downstream node
     */
    public void addConnection(Node n, double w){
        ArrayList<Node> tempDownstream = new ArrayList<>();
        ArrayList<Double>  tempWeights = new ArrayList<>();

        tempDownstream.addAll(Arrays.asList(downstreamNodes));
        tempWeights.addAll(Arrays.asList(weights));


        //Adds the new node and weight
        tempDownstream.add(n);
        tempWeights.add(w);

        //Tell new node that this node is upstream
        n.addUpstreamNode(this);

        downstreamNodes = tempDownstream.toArray(new Node[tempDownstream.size()]);
        weights = tempWeights.toArray(new Double[tempWeights.size()]);

    }

    /**
     * Connects a downstream node to its upstream node
     * @param n
     */
    private void addUpstreamNode(Node n) {
        ArrayList<Node> tempUpstream = new ArrayList<>();
        tempUpstream.addAll(Arrays.asList(upstreamNodes));
        tempUpstream.add(n);
        this.upstreamNodes = tempUpstream.toArray(new Node[tempUpstream.size()]);
    }

    /**
     * Adds connections from this node to all other nodes
     * @param nodes Nodes to be connected to
     * @param weights weights of each connection
     */
    public void addConnections(Node[] nodes, double[] weights){
        for (int i = 0; i < nodes.length; i++) {
            addConnection(nodes[i], weights[i]);
        }
    }


    /**
     * Runs backpropogation to train weights. This is a recursive function to train all the weights in the network.
     * @param learningRate Learning rate
     * @param d datapoint being trained on
     * @return the change in weight of this node
     */
    public double backprop(double learningRate,
                           DataPoint d) {
        if (this.isOutputNode()) {
            // base case - this is an output node
            double nodeOutput = this.genOutput(d);

            double target;

            if (d.getTarget().length == 1) {
                target = d.getTarget()[0];
            } else {
                // find the classification node's output
                int index = network.getOutputIndex(this);
                target = d.getTarget()[index];
            }


            double changeToWeights = (target - nodeOutput) * function.derivative(nodeOutput, this,  d);

            ranBackprop = true;




            return changeToWeights;
        }
        else {
            // recursive case - this is a hidden node
            if(!ranBackprop) { //We already called backprop from this node
                for (int i = 0; i < downstreamNodes.length; i++) {
                    double backpropResult = downstreamNodes[i].backprop(learningRate, d);
                    sum += backpropResult * weights[i];

                    // change weights
                    double change = (learningRate * backpropResult * this.genOutput(d)) + alpha * prevChange[i];
                    weights[i] = weights[i] + change;

                    delta[i] = change - prevChange[i];
                    prevChange[i] = change;

                }
                ranBackprop = true; //For memorization so we don't call this many times
            }


            // oj(1-oj)(SUM())
            return function.derivative(this.genOutput(d), this, d) * sum;
        }
    }

    private Double remOutput = null; //For memorization

    /**
     * First implementation for base node (ie takes values directly and not as a combination of previous nodes
     * @return the linear combination put through a sigmoid function -> sig(W.x)
     */
    public double genOutput(DataPoint d) {
        if(remOutput != null) //Memorizes previous value so new value doesn't have to be computed recursively through network again
            return remOutput;

        if(this.isInputNode()){ //Return the value from the datapoint
            int index = network.getInputIndex(this);

            return d.getFeatureAt(index).getContinuousPayload();
        }

        double sum = 0;
        for(int i=0; i < upstreamNodes.length; i++) {
            //Gets the weight corresponding to this node from the upstream node
            double upOut = upstreamNodes[i].genOutput(d);
            sum += upstreamNodes[i].weights[upstreamNodes[i].getIndex(this)] * upstreamNodes[i].genOutput(d);
        }

        remOutput = function.value(sum, this, d);

        return remOutput;
    }

    /**
     * Makes node forget memorized values
     */
    public void reset(){
        remOutput = null;
        ranBackprop = false;

        prevChange = new double[weights.length];
        delta = new double[weights.length];

        for (int i = 0; i < weights.length; i++) {
            prevChange[i] = 0;
            delta[i] = 0;
        }
    }


    public boolean isInputNode() {
        return this.upstreamNodes.length == 0;
    }

    public boolean isOutputNode() {
        return this.downstreamNodes.length == 0;
    }

    public int getIndex(Node n){
        for (int i = 0; i < downstreamNodes.length; i++) {
            if(downstreamNodes[i].equals(n)){
                return i;
            }
        }
        return -1;
    }

    public void setFunction(ActivationFunction function){
        this.function = function;
    }

    public void setWeights(Double[] weights){
        this.weights = weights;
    }

    public double[] getDelta(){
        return delta;
    }

}
