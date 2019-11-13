package proj2;

import java.util.ArrayList;

/**
 * Created by pryor on 10/7/2019.
 */
public class Validator {

    /**
     * Tests the data on accuracy
     * @param model the model to be tested
     * @param k the k-value
     * @param testData the data to be tested
     * @return The accuracy of the model
     */
    public static double accuracy(KNNModel model, int k, DataPoint[] testData){

        //counters
        int correctClassification = 0;

        for(int i = 0; i < testData.length; i++) {
            if(testData[i].getClassMembership() == null || model == null || testData[i] == null)
                System.out.println("Huston we have a problem");

            if(model.predict(k, testData[i]).equals(testData[i].getClassMembership())) //if we get it right add to the total correct
                correctClassification++;

        }
        return (double) correctClassification/(double) testData.length;
    }

    /**
     * Calculates the precision of a model
     * @param model the model to be tested
     * @param k the k-value
     * @param testData the data to be tested
     * @return the precision of the model
     */
    public static double precision(KNNModel model, int k, DataPoint[] testData) {

        int[][] classifications = getConfusionMatrix(model, k, testData);
        double precision = 0;

        for (int perspec = 0; perspec < classifications.length; perspec++) {//iterates through the perspective
            int TP = 0;  //True positive
            int FP = 0;  //False positive
            for (int compare = 0; compare < classifications.length; compare++) {
                if(compare != perspec){
                    FP += classifications[compare][perspec]; // Checks side to side for false positives
                } else{
                    TP += classifications[perspec][compare]; // Checks for the correct classifications
                }
            }

            if(TP + FP != 0) { // Make sure we have values to avoid NaN
                precision += (double) TP / ((double) (TP + FP)); //Sums the precision
            }

        }
        precision /= Driver.classes.size();

        return precision;
    }

    /**
     * Calculates the recall of a model
     * @param model the model to be tested
     * @param k the k-value
     * @param testData the data to be tested
     * @return the recall
     */
    public static double recall(KNNModel model, int k, DataPoint[] testData) {
        int[][] classifications = getConfusionMatrix(model, k, testData);
        double recall = 0;

        for (int perspec = 0; perspec < classifications.length; perspec++) {//iterates through the perspective
            int TP = 0;  //True positive
            int FN = 0;  //False negative
            for (int compare = 0; compare < classifications.length; compare++) {
                if(compare != perspec){
                    FN += classifications[perspec][compare]; // Checks up and down for false negatives
                } else{
                    TP += classifications[perspec][compare]; // Checks for the correct classifications
                }
            }

            if(TP + FN != 0) { // Make sure we have values to avoid NaN
                recall += (double) TP / ((double) (TP + FN)); //Sums the recall
            }

        }
        recall /= Driver.classes.size();

        return recall;
    }

    /**
     * This calculates the F1 score of the testing data
     * @param testData the data to be tested
     * @return the F1 score
     */
    public static double fScore(KNNModel model, int k, DataPoint[] testData){

        double precision = precision(model, k, testData);
        double recall = recall(model, k, testData);

        return 2 * (precision * recall) / (precision + recall);
    }

    /**
     * Calculates the confusion matrix of a model
     * @param model the model to be tested
     * @param k the k-value
     * @param testData the data to be tested
     * @return
     */
    private static int[][] getConfusionMatrix(KNNModel model, int k, DataPoint[] testData){
        ArrayList<String> classes = Driver.classes;

        int[][] classifications = new int[classes.size()][classes.size()];

        for(int i = 0; i < testData.length; i++) {
            classifications[classes.indexOf(testData[i].getClassMembership())]
                    [classes.indexOf(model.predict(k, testData[i]))]++;//add the value to the matrix at the spot where we classified it.
        }

        return classifications;
    }



}
