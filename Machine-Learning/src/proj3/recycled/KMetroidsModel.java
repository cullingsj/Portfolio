package proj3.recycled;

/**
 * Runs K-Metroids model
 */
public class KMetroidsModel extends ClusteringModel {
    int k;

    public KMetroidsModel(DataPoint[] data) {
        super(data);
    }


    /**
     * Runs iteration of the model
     * This checks each datapoint to find the location of the centroid that minimizes distortion
     */
    private void runIteration() {
        this.clearCentroids();

        // Calculates the initial cost for comparison purposes
        double prevCost = cost(centroids.length);
        group();
        // Loops through each centroid comparing the cost of a swap with a non medoid to the currently stored cost,
        // prevCost
        for (int j = 0; j < k; j++) {
            DataPoint[] points = centroids[j].getPoints();
            for (int i = 0; i < points.length; i++) {

                // if not a centroid compute the following
                if (!Utils.contains(points[i], centroids)) {

                    // store previous centroid
                    DataPoint temp = centroids[j];

                    // swap data point with medoid
                    centroids[j].moveToPoint(points[i]);

                    // calculate cost and compare against cost with previous centroid
                    double calcCost = cost(centroids.length);
                    if (prevCost < calcCost) {

                        // if the cost worsens it swaps back
                        centroids[j].moveToPoint(temp);
                    } else {
                        prevCost = calcCost;
                    }
                }
            }
        }

    }

    /**
     * Puts points in centroids closest to them
     */
    private void group() {
        Double[][] dissimilarity = new Double[k][data.length];
        double min;
        int currCentroid;

        for (int j = 0; j < k; j++) {
            dissimilarity[j] = Utils.getDistancesToPoints(centroids[j], data);
        }

        for (int i = 0; i < data.length; i++) {
            min = dissimilarity[0][i];
            currCentroid = 0;

            for (int m = 0; m < k; m++) {
                if (min > dissimilarity[m][i]) {
                    min = dissimilarity[m][i];
                    currCentroid = m;
                }
            }
            centroids[currCentroid].addPoint(data[i]);

        }
    }

    /**
     * calculates total cost of the current medoids and the data set
     * @param k
     * @return
     */
    private double cost(int k) {
        Double[][] dissimilarity = new Double[k][data.length];
        double cost = 0;
        double minimum;

        for (int j = 0; j < k; j++) {
            dissimilarity[j] = Utils.getDistancesToPoints(centroids[j], data);
        }

        for (int i = 0; i < data.length; i++) {
            minimum = dissimilarity[0][i];
            for (int m = 1; m < k; m++) {
                if (minimum > dissimilarity[m][i]) {
                    minimum = dissimilarity[m][i];
                }
            }
            cost += minimum;
        }
        return cost;

    }


    @Override
    public void cluster(int k) {
        this.k = k;
        centroids = new Centroid[k];

        // randomly grabs k medoids by scrambling the data set and grabbing the first k values
        DataPoint[] randArray = Utils.scramble(data);
        for (int i = 0; i < k; i++) {
            centroids[i] = new Centroid(randArray[i]);
        }
        double currCost = cost(k);
        double prevCost = currCost + 1;

        while (prevCost > currCost) {
            runIteration();
            prevCost = currCost;
            currCost = cost(k);
        }

//        System.out.println("Display K-Metroids Centroids");
//        for (Centroid c : centroids){
//
//            System.out.println("Centroid: " + c.toString());
//            if(c.getPoints().length == 0){
//                System.out.print("no points");
//            }
//            else {
//                for (DataPoint d : c.getPoints()) {
//                    System.out.println("Point: " + d.toString());
//                }
//            }
//            System.out.println();
//        }

    }
}

