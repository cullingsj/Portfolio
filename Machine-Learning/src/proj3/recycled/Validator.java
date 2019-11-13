package proj3.recycled;

import proj3.Network;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pryor on 10/7/2019.
 */
public class Validator {

    /**
     * Tests the data on accuracy
     * @param model the model to be tested
     * @param testData the data to be tested
     * @return The accuracy of the model
     */
    public static double accuracy(Network model, DataPoint[] testData){

        //counters
        int correctClassification = 0;

        for(int i = 0; i < testData.length; i++) {

            if(Utils.argMax(model.predict(testData[i])) == Utils.argMax(testData[i].getTarget())) //if we get it right add to the total correct
                correctClassification++;

        }
        return (double) correctClassification/(double) testData.length;
    }


    public static double squaredError(Network model, DataPoint[] testData){
        double error = 0;

        for(int i = 0; i < testData.length; i++) {
            error += Math.pow((model.predict(testData[i])[0] - testData[i].getTarget()[0]), 2);
        }

        return error/testData.length;
    }



}
