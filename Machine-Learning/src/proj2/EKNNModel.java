package proj2;


import java.util.ArrayList;

/**
 * Runs Edited KNN
 */
public class EKNNModel extends KNNModel{
    int k;

    /**
     * Runs edited KNN on a dataset
     * @param k The K value to use
     * @param raw_data The data (un-edited)
     */
    public EKNNModel(int k, DataPoint[] raw_data) {
        super(raw_data);
        this.k = k;
        System.out.println("Pre-edit size: " + data.length);
        edit();
        System.out.println("Post edit size: " + data.length);
    }

    /**
     * Edits the dataset
     * Removes datapoints that are incorrectly classified
     */
    private void edit(){
        ArrayList<DataPoint> newData = new ArrayList<>();
        int prevSize = newData.size();
        int delta = 2;

        while (Math.abs(prevSize - newData.size()) < delta) {//Do this until it doesn't change that much
            prevSize = newData.size();
            for (DataPoint x : data) {
                if (this.predict(k, x).equalsIgnoreCase(x.getClassMembership())) { //If it gets it wrong, remove it from dataset
                    newData.add(x);
                }
            }
            data = Helper.listToArr(newData); //Safe cast
        }
        data = Helper.listToArr(newData); //Safe cast
    }



}