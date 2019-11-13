package proj2;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs KNN model
 */
public class KNNModel {
    protected DataPoint[] data;

    protected List<SimilarityMatrix> similarityMatrices;

    public KNNModel(DataPoint[] data) {
        this.similarityMatrices = new ArrayList<>();
	    this.data = data;
        for (Feature feat: this.data[0].getFeatures()) {
            if (feat.getFeatureType() == FeatureType.CATEGROICAL) {
                similarityMatrices.add(new SimilarityMatrix(this.data, feat.getLabel()));
            }
        }
    }

    /**
     * Predicts the class of a new datapoint.
     * @param k The number of neighbors to compare to
     * @param newObservation The datapoint to predict
     * @return The predicted class value of the datapoint
     */
    public String predict(int k, DataPoint newObservation) {

        Double[] distances = Helper.getDistancesToPoints(newObservation, data, this.similarityMatrices);

        int[] indxs = Helper.findIndexOfMinimumK(k, distances);
        DataPoint[] kNearest = new DataPoint[k];



        for (int i = 0; i < kNearest.length; i++) {
            kNearest[i] = data[indxs[i]];
        }

        System.out.println("The K-Nearest Datapoints are:");
        for (DataPoint d : kNearest){
            System.out.println(d.toString());
        }

        return Helper.vote(kNearest);
    }
}
