package proj2;

/**
 * Runs K-Means
 */
public class KMeansModel extends ClusteringModel {


    /**
     * Constructor
     * @param data data to be clustered
     */
    public KMeansModel(DataPoint[] data) {
        super(data);
    }

    @Override
    public void cluster(int k) {
        // initlaize random centroids
        // TODO : be smarter about where we initalize points
        centroids = new Centroid[k];
        DataPoint[] randArray = Helper.scramble(data);

        for (int i = 0; i < k; i++) {
            centroids[i] = new Centroid(randArray[i]); //Puts the centroid at random point
        }



        // TODO : find when to stop in a better way
        // run 100 iterations to train
        for (int i=0; i < 100; i++) {
            this.runIteration();
        }

        System.out.println("Display K-Means centroids: ");
        for (Centroid c : centroids){

            System.out.println("Centroid: " + c.toString());
            if(c.getPoints().length == 0){
                System.out.print("no points");
            }
            else {
                for (DataPoint d : c.getPoints()) {
                    System.out.println("Point: " + d.toString());
                }
            }
            System.out.println();
        }

    }

    /**
     * Runs an iteration of the algorithm
     * This assigns points to centroids and moves the centroids to their averages
     */
    private void runIteration() {
        // Step 1: Evaluate Group Membership
        this.clearCentroids();// Empties out old datapoints for new iteration

        for (DataPoint point : data) {
            // find distances from all current centroids to this point
            Double[] distances = Helper.getDistancesToPoints(point, this.centroids, this.similarityMatrices);
            // find the centroid this point is closest to
            Centroid closestCentroid = this.centroids[Helper.findIndexOfMinimum(distances)];

            // set this point's group to be the centroid's group
            closestCentroid.addPoint(point);
        }

        // Step 2: Compute New Centroids
        this.moveCentroids();
        this.assignClasses();//So classes on centroid stays up-to-date

    }
}
