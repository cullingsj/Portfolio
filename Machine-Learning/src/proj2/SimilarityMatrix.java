package proj2;

import java.util.HashMap;
import java.util.Map;

public class SimilarityMatrix {
    private Map<String, Map<String, Double>> distances;
    private String featLabel;

    /**
     * Returns the similarity (or distance) between two categorical variables
     * @param payload1 the first payload to compare
     * @param payload2 the second payload to compare
     * @return the similarity (or distance) between the two categorical variables
     */
    public double getDistance(String payload1, String payload2) {
        return this.distances.get(payload1).get(payload2);
    }

    /**
     * Gets the label for the feature this matrix corresponds to
     * @return label for the feature this matrix corresponds to
     */
    public String getFeatLabel() {
        return this.featLabel;
    }

    /**
     * Creates a similarity matrix  by calculating the similarity (distance) matrix for one feature, identified by its label
     * @param data the data to be used to calculate the matrix
     * @param featLabel the label for the feature to calculate the matrix of (uses object's data)
     */
    public SimilarityMatrix(DataPoint[] data, String featLabel) {
        // maps payload to its occurrence
        Map<String, Integer> numberOfEntriesPerPayload = new HashMap<String, Integer>();
        // maps class to (map payload to its occurrence)
        Map<String, Map<String, Integer>> numberOfEntriesPerClassPerPayload = new HashMap<String, Map<String, Integer>>();
        // maps a payload x to (map to payload y to it's "distance" from payload x)
        Map<String, Map<String, Double>> distances = new HashMap<String, Map<String, Double>>();

        for (DataPoint dp : data) {
            String payload = dp.getFeatureByLabel(featLabel).getCategoricalPayload();
            String classMembership = dp.getClassMembership();

            // first, add number of occurrences per payload
            if (numberOfEntriesPerPayload.containsKey(payload)) {
                numberOfEntriesPerPayload.put(payload, numberOfEntriesPerPayload.get(payload) + 1);
            } else {
                numberOfEntriesPerPayload.put(payload, 1);
            }

            // second, add number of occurrences per payload per class
            if (!numberOfEntriesPerClassPerPayload.containsKey(classMembership)) {
                // contains class
                numberOfEntriesPerClassPerPayload.put(classMembership, new HashMap<String, Integer>());
            }

            Map<String, Integer> mapForClass = numberOfEntriesPerClassPerPayload.get(classMembership);

            if (mapForClass.containsKey(payload)) {
                // contains payload
                mapForClass.put(payload, mapForClass.get(payload) + 1);
            } else {
                // doesn't contain payload
                mapForClass.put(payload, 1);
            }

            // counting done
        }

        // calculate similarities

        for (String payload1: numberOfEntriesPerPayload.keySet()) {
            for (String payload2: numberOfEntriesPerPayload.keySet()) {
                if (!distances.containsKey(payload1)) {
                    // initialize new map
                    distances.put(payload1, new HashMap<String, Double>());
                }


                double prob = 0.0;

                for (String classMembership: numberOfEntriesPerClassPerPayload.keySet()) {

                    double payload1OccurrenceInClass;
                    double payload2OccurrenceInClass;

                    double payload1Occurrence = (double) numberOfEntriesPerPayload.get(payload1);
                    double payload2Occurrence = (double) numberOfEntriesPerPayload.get(payload2);
                    try {
                        payload1OccurrenceInClass = (double) numberOfEntriesPerClassPerPayload.get(classMembership).get(payload1);
                    } catch (NullPointerException e) {
                        payload1OccurrenceInClass = 0.0;
                    }

                    try{
                        payload2OccurrenceInClass = (double) numberOfEntriesPerClassPerPayload.get(classMembership).get(payload2);
                    } catch (NullPointerException e) {
                        payload2OccurrenceInClass = 0.0;
                    }



                    prob += Math.abs(
                            (payload1OccurrenceInClass / payload1Occurrence)
                            -
                            (payload2OccurrenceInClass / payload2Occurrence)
                    );
                }

                distances.get(payload1).put(payload2, prob);
            }
        }

        this.distances = distances;
        this.featLabel = featLabel;
    }

    @Override
    public String toString() {
        String ans = "Sim Matrix for " + featLabel + "\n";
        for (Map.Entry<String, Map<String, Double>> level1 : distances.entrySet()) {
            for (Map.Entry<String, Double> level2 : level1.getValue().entrySet()) {
                String payload1 = level1.getKey();
                String payload2 = level2.getKey();
                Double distance = level2.getValue();

               ans = ans + "\t" + payload1 + " to " + payload2 + ": " + distance.toString() + "\n";
            }
        }
        return ans;
    }
}
