package proj3.recycled;


import java.util.ArrayList;

/**
 * @author Elliott Pryor on 11/4/2019.
 * @project Machine_Learning
 */
public class Condense {

    /**
     * Condenses the dataset
     * @param data Data to be condensed
     * @return the condensed dataset
     */
    public static Centroid[] condense(proj3.recycled.DataPoint[] data){
        ArrayList<DataPoint> newData = new ArrayList<>();
        data = proj2.Helper.scramble(data);
        newData.add(data[0]); // inserts the first item

        int size = newData.size();
        int delta = 1;

        while (Math.abs(newData.size() - size) < delta){ //until it doesn't change that much
            for (proj3.recycled.DataPoint x : data) {
                Double[] dists = Utils.getDistancesToPoints(x, newData); //Gets dist to each point in new set

                int indx = Utils.findIndexOfMinimum(dists);
                if(!categoricalEquals(newData.get(indx).getTarget(), x.getTarget())){ //Checks if the closest one is different from this one
                    newData.add(x);
                }
            }
        }

        Centroid[] retMe = convertToCentroid(newData);
        for (DataPoint point : data) {
            // find distances from all current centroids to this point
            Double[] distances = Utils.getDistancesToPoints(point, retMe);
            // find the centroid this point is closest to
            Centroid closestCentroid = retMe[Utils.findIndexOfMinimum(distances)];

            // set this point's group to be the centroid's group
            closestCentroid.addPoint(point);
        }

        return retMe;//Condensed dataset
    }

    private static Centroid[] convertToCentroid(ArrayList<DataPoint> data){
        Centroid[] retMe = new Centroid[data.size()];
        for (int i = 0; i < data.size(); i++) {
            retMe[i] = new Centroid(data.get(i));
        }
        return retMe;
    }

    private static boolean categoricalEquals(double[] a, double[] b){
        if(a.length == b.length){
            for (int i = 0; i < a.length; i++){
                if(a[i] != b[i])
                    return false;
            }
            return true;
        } else
            return false;
    }

}
