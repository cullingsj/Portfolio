package proj2;


import proj3.Driver;
import proj3.Onehot;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an object to store the data
 */
public class DataPoint{
    // form of { label: payload }
    protected Feature[] features;
    protected String classMembership;

    // default constructor
    public DataPoint(Feature[] features) {
        this.features = features;
    }

    // constructor with class membership
    public DataPoint(Feature[] features, String classMembership) {
        this(features);
        this.classMembership = classMembership;
    }

    /**
     * @return the class of this point
     */
    public String getClassMembership() {
        return this.classMembership;
    }

    /**
     *
     * @return The number of features that this point contains
     */
    public int getNumberOfFeatures() {
        return this.features.length;
    }

    /**
     * Gets the feature value based on the label
     * @param label String identifier of the feature
     * @return the feature value
     */
    public Feature getFeatureByLabel(String label) {
        for (Feature feat: this.features) {
            if (feat.getLabel() == label) {
                return feat;
            }
        }
        //If there is no feature with that label
        return null;
    }

    /**
     * Gets the ith feature
     * @param i index of the feature
     * @return the feature
     */
    public Feature getFeatureAt(int i){
        return features[i];
    }

    /**
     *
     * @return All of the features of this point
     */
    public Feature[] getFeatures() {
        return features;
    }

    /**
     * Assigns a new class to this point
     * @param newClassMembership the new class
     */
    public void setClassMembership(String newClassMembership) {
        this.classMembership = newClassMembership;
    }

    public String toString(){
        List<String> featureStrings = new ArrayList<>();
        for (Feature feat: features) {
            featureStrings.add(feat.toString());
        }
        return "Features: {" + String.join(", ",featureStrings) + "} | Class: " + classMembership;
    }

    @Override
    public boolean equals(Object obj) {
        DataPoint other  = (DataPoint) obj;
        for (Feature feat: features) {
            if (!feat.equals(other.getFeatureByLabel(feat.getLabel()))) {
                return false;
            }
        }
        return  true;
    }
}
