package proj3.recycled;

import java.util.*;

/**
 * Filled with helpful functions
 */
public class Utils {

    /**
     * Gets the distance between a and b.
     * Uses euclidean distance for continuous features
     * Uses similarity matricies for categorical features
     * @param a the first datapoint
     * @param b the second datpoint
     * @return double distance
     */
    public static Double getDistance(DataPoint a, DataPoint b) {

        double totalDistance = 0;

        // iterate through features of point a - assume they are the same as point b

        for (int i = 0; i < a.getNumberOfFeatures(); i++) {
            totalDistance += Math.pow(a.getFeatureAt(i).getContinuousPayload() - b.getFeatureAt(i).getContinuousPayload(), 2);
        }

        //System.out.println(totalDistance);

//        for (Feature feat: a.getFeatures()) {
//            totalDistance += Math.pow(feat.getContinuousPayload() - b.getFeatureByLabel(feat.getLabel()).getContinuousPayload(), 2);
//        }
        return totalDistance;
    }

    /**
     * Overloads the getDistancesToPoints(Datapoint, DataPoint[], List)
     * @param a
     * @param points
     * @return
     */
    public static Double[] getDistancesToPoints(DataPoint a, List<DataPoint> points) {
        DataPoint[] pointsArr = points.toArray(new DataPoint[points.size()]);
        return getDistancesToPoints(a, pointsArr);
    }

    /**
     * Gets the distance to every point in points
     * @param points the points to compare with
     * @param a the first datapoint
     * @return double[] of distances
     */
    public static Double[] getDistancesToPoints(DataPoint a, DataPoint[] points) {
        List<Double> ans = new ArrayList<>();
        for (DataPoint dp: points) {
            ans.add(getDistance(a, dp));
        }
        Double[] arr = new Double[ans.size()];
        return ans.toArray(arr);
    }

    /**
     * Finds the argmin(array)
     * @param array
     * @return index of minimum value
     */
    public static int findIndexOfMinimum(Double[] array) {
        int currentMinIndex = 0;
        for (int i=0; i<array.length; i++) {
            if (array[i] < array[currentMinIndex]) {
                currentMinIndex = i;
            }
        }
        return currentMinIndex;
    }

    /**
     * Finds the indicies of the k-lowest values
     * @param k
     * @param array
     * @return
     */
    public static int[] findIndexOfMinimumK(int k, Double[] array) {
        Integer[] indxs = new Integer[k];
        for (int i = 0; i < k; i++) { // just in case the first element is the smallest
            indxs[i] = -1;
        }

        for (int j = 0; j < k; j++) {
            int currentMinIndex = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] < array[currentMinIndex] && !contains(i, indxs)) {
                    currentMinIndex = i;
                }
            }
            indxs[j] = currentMinIndex;
        }

        int[] retMe = new int[indxs.length];
        for (int i = 0; i < indxs.length; i++) {
            retMe[i] = indxs[i];
        }
        return retMe;
    }

    /**
     * Check if an array contains a specific element
     * @param i
     * @param arr
     * @param <T> Type
     * @return true if the array contains the element, false otherwise
     */
    public static <T> boolean contains(T i, T[] arr){
        for(T x : arr) {
            if (x.equals(i))
                return true;
        }
        return false;
    }


    /**
     * Finds the 'average' of given datapoints.
     * Uses arithmetic mean for continuous features.
     * Uses the categorical value that has the highest frequency for categorical features
     * @param points
     * @return
     */
    public static DataPoint findAverageOfPoints(DataPoint[] points) {
        Feature[] avgFeatures = new Feature[points[0].getNumberOfFeatures()];

        for (int j = 0; j < points[0].getNumberOfFeatures(); j++){
            Feature feat = points[0].getFeatureAt(j);
            if(feat.featureType == FeatureType.CATEGROICAL){
                Map<Feature, Integer> votes = new HashMap<>();

                for (int i = 0; i < points.length; i++) { //Votes the K-nearest neighbors
                    if(votes.get(points[i].getFeatureAt(j)) != null) {
                        votes.put(points[i].getFeatureAt(j), //The string key
                                votes.get(points[i].getFeatureAt(j)) + 1); //increments value already stored
                    } else
                        votes.put(points[i].getFeatureAt(j), 1);
                }

                Feature maxKey = null;
                for(Map.Entry<Feature, Integer> entry : votes.entrySet()){ //Goes through each of the class values within k-neighbors and finds max # of votes
                    if (maxKey == null || entry.getValue() > votes.get(maxKey))
                        maxKey = entry.getKey();
                }
                avgFeatures[j] = maxKey;

            } else {
                double sum = 0;
                for(DataPoint d : points){
                    sum += d.getFeatureAt(j).getContinuousPayload();
                }

                sum /= points.length;

                avgFeatures[j] = new Feature(sum, points[0].getFeatureAt(j).getLabel());
            }
        }

        return new DataPoint(avgFeatures);
    }

    /**
     * Randomly mixes the array
     * @param array
     * @param <T>
     * @return the same array in a random order.
     */
    public static <T> T[] scramble(T[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            T temp = array[i];
            int randomSpot = rand.nextInt(array.length);
            array[i] = array[randomSpot];
            array[randomSpot] = temp;
        }
        return array;
    }

    /**
     * Calculates the most common class label of the data
     * @param data
     * @return the most common class label in the data
     */
    public static String vote(DataPoint[] data){
        Map<String, Integer> votes = new HashMap<>();

//        for (int i = 0; i < data.length; i++) { //Votes the K-nearest neighbors
//            if(votes.get(data[i].getClassMembership()) != null) {
//                votes.put(data[i].getClassMembership(), //The string key
//                        votes.get(data[i].getClassMembership()) + 1); //increments value already stored
//            } else
//                votes.put(data[i].getClassMembership(), 1);
//        }
//
//
//        String maxKey = null;
//        for(Map.Entry<String, Integer> entry : votes.entrySet()){ //Goes through each of the class values within k-neighbors and finds max # of votes
//            if (maxKey == null || entry.getValue() > votes.get(maxKey))
//                maxKey = entry.getKey();
//
//        }

        return ""; //Return key with most votes
    }

    /**
     * Converts a DataPoint list to an array
     * @param list
     * @return
     */
    public static DataPoint[] listToArr(List<DataPoint> list){
        DataPoint[] retme = new DataPoint[list.size()];
        for (int i = 0; i < list.size(); i++) {
            retme[i] = list.get(i);
        }
        return retme;
    }

    public static int argMax(double[] a){
        int index = 0;
        double max = a[index];
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= max){
                max = a[i];
                index = i;
            }
        }

        return index;
    }

}