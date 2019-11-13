package proj2;

import java.util.ArrayList;


/**
 * Runs condensed k-nearest-neighbor
 */
public class CKNNModel extends KNNModel{
    /**
     * Runs condensed KNN
     * @param raw_data The data (un-condensed)
     */
    public CKNNModel(DataPoint[] raw_data) {
        super(raw_data);
        System.out.println("Pre-condense size: " + this.data.length);
        data = condense(raw_data);
        System.out.println("Post-condense size: " + this.data.length);
    }

    /**
     * Condenses the dataset
     * @param data Data to be condensed
     * @return the condensed dataset
     */
    private DataPoint[] condense(DataPoint[] data){
        ArrayList<DataPoint> newData = new ArrayList<>();
        data = Helper.scramble(data);
        newData.add(data[0]); // inserts the first item

        int size = newData.size();
        int delta = 2;

        while (Math.abs(newData.size() - size) < delta){ //until it doesn't change that much
            for (DataPoint x : data) {
                Double[] dists = Helper.getDistancesToPoints(x, newData, this.similarityMatrices); //Gets dist to each point in new set

                int indx = Helper.findIndexOfMinimum(dists);
                if(!newData.get(indx).getClassMembership().equals(x.getClassMembership())){ //Checks if the closest one is different from this one
                    newData.add(x);
                }
            }
        }
        return newData.toArray(new DataPoint[newData.size()]);//Condensed dataset

    }
}
