package proj3.recycled;


/**
 * Creates a framework for clustering models
 */
public abstract class ClusteringModel {
    protected DataPoint[] data;
    protected Centroid[] centroids;

    /**
     * Initializes the clustering model with some data to be clustered
     * @param data the dataset
     */
    public ClusteringModel(DataPoint[] data){
        this.data = data;
    }

    /**
     * Creates k clusters on data
     * @param k the number of clusters
     */
    public abstract void cluster(int k);

    /**
     * Gets the distortion value of the clusters.
     * Note, must have called cluster() before getDistortion
     * @return the distortion of the clusters
     * @throws Exception If the number of features is not the same between points being compared
     */
    public double getDistrortion() throws Exception {
        double ans = 0.0;

        // cycle through all points
        for (DataPoint point : this.data) {
            // get the group average for the group this point belongs
            DataPoint average = getCentroid(point);

            // check to make sure we're comparing points of the same dimensionality
            if (average.getNumberOfFeatures() != point.getNumberOfFeatures()) {
                throw new Exception("Trying to compare 2 vectors of different dimensions. Probable error in dataset.");
            }

            ans +=  Math.pow(Utils.getDistance(point, average), 2);

        }

        return ans;
    }

    /**
     * Moves all the centroids to their averages
     */
    public void moveCentroids(){
        for(Centroid centroid : centroids){
            centroid.moveToAvg();
        }
    }

    /**
     * Returns the centroid to which the point belongs
     * @param p point being queried
     * @return the centroid to which the point belongs
     */
    private Centroid getCentroid(DataPoint p){
        for (Centroid centroid : centroids){
            if (centroid.contains(p)) {
                return centroid;
            }
        }

        //Error catching in case the point does not belong to any centroid
        Double[] dists = Utils.getDistancesToPoints(p, centroids);
        Centroid c = centroids[Utils.findIndexOfMinimum(dists)];
        c.addPoint(p);
        return c;
    }

    /**
     * Gets the centroids
     * @return the centroids
     */
    public Centroid[] getCentroids(){
        return this.centroids;
    }

    /**
     * Gets the data being clustered
     * @return the data
     */
    public DataPoint[] getData() {
        return this.data;
    }

    /**
     * Sets the centroids to a new Centroid[]
     * @param newPoints the new centroids
     */
    protected void setCentroids(Centroid[] newPoints) {
        this.centroids = newPoints;
    }


    /**
     * Sets the class membership of the centroid based on the majority class of its points.
     */
    public void assignClasses(){
        for (Centroid centroid : centroids) {
            //if(centroid.getPoints().length > 0)
                //centroid.setClassMembership(Utils.vote(centroid.getPoints()));
        }
    }

    /**
     * Empties the centroids of all their points.
     * Ie. Removes all point-centroid associations.
     */
    public void clearCentroids(){
        for(Centroid centroid : centroids){
            centroid.clearPoints();
        }
    }

}
