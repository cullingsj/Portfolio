package proj3.recycled;

/**
 * Stores the features of a datpoint
 */
public class Feature {
    FeatureType featureType;
    double[] hot;

    private double continuousPayload; //If the feature is continuous
    private String categoricalPayload; //For categorical features
    private String label;

    /**
     * Creates a new continuous feature
     * @param payload The value of the feature
     * @param name the label
     */
    public Feature(double payload, String name){
        continuousPayload = payload;
        featureType = FeatureType.CONTINUOUS;
        label = name;
    }

    /**
     * Creates a new categorical feature
     * @param payload The value of the feature
     * @param name the label
     */
    public Feature(String payload, String name){
        categoricalPayload = payload;
        featureType = FeatureType.CATEGROICAL;
        label = name;
    }

    /**
     * Gets the value of continuous features
     * @return
     */
    public double getContinuousPayload() {
        return this.continuousPayload;
    }

    /**
     * Gets the identifier for this feature
     * @return
     */
    public double[] getCategoricalOnehot() { return this.hot; }

    public String getLabel() { return this.label; }

    /**
     * Gets the value of categorical features
     * @return
     */
    public String getCategoricalPayload() {
        return this.categoricalPayload;
    }

    /**
     *
     * @return the type of feature
     */
    public FeatureType getFeatureType() {
        return this.featureType;
    }

    public boolean isCategorical() {
        return this.featureType == FeatureType.CATEGROICAL;
    }

    public boolean isContinuous() {
        return this.featureType == FeatureType.CONTINUOUS;
    }

    public String toString(){
        if(featureType == FeatureType.CATEGROICAL)
            return label + "(C): " + categoricalPayload;
        else
            return label + "(N): " + continuousPayload;

    }

    public void setContinuousPayload(double payload){
        this.continuousPayload = payload;
    }


    @Override
    public boolean equals(Object obj) {
        Feature other = (Feature) obj;
        if (other.featureType != this.featureType){
            return false;
        }
        if (other.label != this.label){
            return false;
        }
        if (this.featureType == FeatureType.CATEGROICAL) {
            return this.categoricalPayload.equals(other.categoricalPayload);
        }
        if (this.featureType == FeatureType.CONTINUOUS) {
            return this.continuousPayload == other.continuousPayload;
        }
        return false;
    }
}
