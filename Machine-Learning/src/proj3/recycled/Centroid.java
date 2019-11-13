package proj3.recycled;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pryor on 10/7/2019.
 */
public class Centroid extends DataPoint {
    private List<DataPoint> dataPoints; // Stores the datapoints that belong to this centroid

    /**
     * Creates a centroid 'located' at the features given as a parameter
     * @param features The features identify this object's location in the hyperspace
     */
    public Centroid(Feature[] features) {
        super(features);
        dataPoints = new ArrayList<>();
        this.target = new double[]{};
    }

    /**
     * Creates a centroid 'located' at a given datapoint
     * @param dataPoint the location at where to put the centroid.
     */
    public Centroid(DataPoint dataPoint) {
        this(dataPoint.features);
    }

    /**
     * Gets the points that belong to the centroid
     * @return The datapoints that 'belong' to this centroid
     */
    public DataPoint[] getPoints(){
        return Utils.listToArr(dataPoints);
    }

    /**
     * Adds a point to the centroid
     * @param d The point to be added
     */
    public void addPoint(DataPoint d){
        dataPoints.add(d);
    }

    /**
     * Moves the centroid to the average of its points
     */
    public void moveToAvg() {
        if(dataPoints.size() > 0) { //Checks if any points belong to the centroid
            DataPoint average = Utils.findAverageOfPoints(this.getPoints());
            features = average.getFeatures();
            moveToPoint(average);
        }
    }


    /**
     * Moves the centroid to the given point
     * @param point point at which to place the centroid
     */
    public void moveToPoint(DataPoint point){
        features = point.getFeatures();
    }


    public double getDeviation(){
        double deviation = 0;

        for (int i = 0; i < dataPoints.size(); i++) {
            deviation += Utils.getDistance(this, dataPoints.get(i));
        }
        if(dataPoints.size() > 0){
            double val = Math.sqrt(deviation/dataPoints.size());
            return (val >= 0.3) ? val : 0.3;
        } else{
            return 0.3;
        }
    }

    /**
     * Checks to see whether a point 'belongs' to the centroid
     * @param point the point being queried
     * @return True if the point is associated with the centroid
     */
    public boolean contains(DataPoint point){
        return dataPoints.contains(point);
    }

    /**
     * Removes all associations with datapoints.
     * Ie. This centroid no longer has any points that 'belong' to it
     */
    public void clearPoints(){
        dataPoints = new ArrayList<>();
    }

}
