package proj2;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

/**
 * @author Elliott Pryor on 10/4/2019.
 * @project Machine_Learning
 */
public class Driver {
    public static ArrayList<String> classes;

    private static int k = 7;

    public static void main(String[] args) {
        String[] allFiles = {
                "abalone",
                "car",
                "segmentation",
                "forestfires",
                "machine",
                "wine"
        };



        String[] classificationFiles = {
                "abalone",
                "car",
                "segmentation"
        };
        String[] regressionFiles = {"forestfires", "machine", "wine"};

        String[] CATEGORICAL_FEAURES = {
                "sex",
                "buying",
                "maint",
                "doors",
                "persons",
                "safety",
                "lug_boot",
                "month",
                "day"
        };

        String[] demoFiles = {"car"};


        for (String path : demoFiles) { // Reads the files
            System.out.println();
            System.out.println();
            System.out.println("Processing: " + path);

            String file = readEntireFile("Data/Assignment2/" + path + "_preprocessed.data");//reads in the data

            String[] lines = file.split("\n");
            DataPoint[] data = new DataPoint[lines.length]; // First line is feature labels

            String[] featureLabels = lines[0].split(",");

            classes = new ArrayList<>();

            //Generates the datapoints
            for (int i = 1; i < lines.length; i++) {
                data[i] = genDatapoint(lines[i], featureLabels, CATEGORICAL_FEAURES);
                if (!classes.contains(data[i].getClassMembership()))
                    classes.add(data[i].getClassMembership());
            }


            DataPoint[][] folds = fold(data);// Splits the data into 10 roughly equal folds

            if (Helper.contains(path, classificationFiles)) { //Run Classification algs
                //Header labels for the *.csv file that metrics are written to
                String knnOutput = "train Fold, test Fold, KNN-ACC, KNN-PREC, KNN-RECALL, KNN-FSCORE, CKNN-ACC, CKNN-PREC, CKNN-RECALL, CKNN-FSCORE, EKNN-ACC, EKNN-PREC, EKNN-RECALL, EKNN-FSCORE\n";
                String clusterOutput = "train Fold, test Fold, KMEANS-DISTORTION, KMEANS-ACC, KMEANS-PREC, KMEANS-RECALL, KMEANS-FSCORE, KMETROIDS-DISTORTION, KMETROIDS-ACC, KMETROIDS-PREC, KMETROIDS-RECALL, KMETROIDS-FSCORE\n";

                //Creates a new file for this run
                File knnFile = createNewFile("Data/Assignment2/outputs/classification/knn-k" + k + "-" + path);
                File clusterFile = createNewFile("Data/Assignment2/outputs/classification/clustering-k" + k + "-" + path);

                //Writes the header
                appendToFile(knnOutput, knnFile);
                appendToFile(clusterOutput, clusterFile);

                for (int trainFold = 0; trainFold < 1; trainFold++) {
                    System.out.println("Train Fold: " + trainFold);
                    DataPoint[] trainingData = folds[trainFold];
                    DataPoint[] testData;//initializes it, but will be overwritten in For loop

                    Scanner scanner = new Scanner(System.in);
                    String x;

                    //initializes the models
                    KNNModel knn = new KNNModel(trainingData);
                    KNNModel cknn = new CKNNModel(trainingData);
                    KNNModel eknn = new EKNNModel(k, trainingData);

                    int clusters = eknn.data.length;

                    x = scanner.nextLine();


                    for (int fold = 1; fold < 2; fold++) {
                        //RUN THE MODELS
                        System.out.println("Test Fold: " + fold);//To track progress while running

                        testData = folds[fold];
                        knnOutput = trainFold + "," + fold + ","; //fold info
                        clusterOutput = trainFold + "," + fold + ",";

                        //Formats the KNN outputs for CSV
                        System.out.println("Run KNN");
                        knnOutput = knnOutput + runKNNClassification(knn, testData) + ",";//calculated
                        x = scanner.nextLine();
                        System.out.println("Run CKNN");
                        knnOutput = knnOutput + runKNNClassification(cknn, testData) + ",";//calculated
                        x = scanner.nextLine();
                        System.out.println("Run EKNN");
                        knnOutput = knnOutput + runKNNClassification(eknn, testData) + "\n";//calculated

                        x = scanner.nextLine();// waits so we can look at the knn data

                        KMeansModel kmeans = new KMeansModel(trainingData);
                        KMetroidsModel kmetroids = new KMetroidsModel(trainingData);

                        kmeans.cluster(clusters);

                        x = scanner.nextLine();

                        kmetroids.cluster(clusters);

                        x = scanner.nextLine();

                        //Formats the clustering outputs for CSV
                        System.out.println("Run K-Means");
                        clusterOutput = clusterOutput + runClusteringClassification(kmeans, testData) + ",";
                        System.out.println("Run K-Metroids");
                        clusterOutput = clusterOutput + runClusteringClassification(kmetroids, testData) + "\n";

                        //Writes the data
                        appendToFile(knnOutput, knnFile);
                        appendToFile(clusterOutput, clusterFile);

                    }
                }

                System.out.println("Done Writing");

            } else { //Regression model
                //Header for CSV
                String clusterOutput = "train Fold, KMEANS-DISTORTION, KMETROIDS-DISTORTION\n";
                File newFile = createNewFile("Data/Assignment2/outputs/regression/clusteringRegression-" + path);

                appendToFile(clusterOutput, newFile);

                for (int trainFold = 0; trainFold < folds.length; trainFold++) {

                    System.out.println("Fold: " + trainFold);

                    DataPoint[] trainData = folds[trainFold];//initializes it, but will be overwritten in For loop


                    KMeansModel kmeans = new KMeansModel(trainData);
                    KMetroidsModel kmetroids = new KMetroidsModel(trainData);

                    kmeans.cluster(trainData.length / 4);
                    kmetroids.cluster(trainData.length / 4);


                    //RUN THE MODELS
                    clusterOutput = trainFold + ","; //fold info
                    clusterOutput = runClusteringRegression(kmeans, clusterOutput) + ",";
                    clusterOutput = runClusteringRegression(kmetroids, clusterOutput) + "\n";

                    appendToFile(clusterOutput, newFile);


                }
                System.out.println("Done Writing");

            }


            System.out.println(); // To separate files on the output console
            System.out.println();
            System.out.println();
            System.out.println();
        }


    }

    /**
     * Runs the validation on KNN models. Returns a string with the data formatted for CSV file
     * @param model
     * @param k
     * @param testData
     * @return
     */
    private static String printKNNMetrics(KNNModel model, int k, DataPoint[] testData) {
        double acc = Validator.accuracy(model, k, testData);
        double prec = Validator.precision(model, k, testData);
        double recall = Validator.recall(model, k, testData);
        double fScore = Validator.fScore(model, k, testData);

        //Stores the metrics for each thing

        String output = acc + "," + prec + "," + recall + "," + fScore;
        System.out.println("Output: ACCURACY, PRECISION, RECALL, F-SCORE");
        System.out.println("Output: " + output);

        return output;
    }

    /**
     * Creates a datapoint based on a string input
     *
     * @param featureString String defining the datapoint
     * @return Datapoint generated
     */
    private static DataPoint genDatapoint(String featureString, String[] featureLabels, String[] categoricalFeatures) {
        String[] splice = featureString.split(",");

        Feature[] features = new Feature[splice.length - 1];// Assume last value is class


        for (int i = 0; i < features.length; i++) {
            if (Arrays.asList(categoricalFeatures).contains(featureLabels[i])) {
                features[i] = new Feature(splice[i], featureLabels[i]);
            } else {
                try {
                    features[i] = new Feature(Double.parseDouble(splice[i]), featureLabels[i]);
                } catch (NumberFormatException e) {
                    features[i] = new Feature(splice[i], featureLabels[i]);
                }
            }
        }

        DataPoint d = new DataPoint(features, splice[splice.length - 1]);

        return d;

    }

    /**
     * Runs the validation on KNN models. Returns a string with the data formatted for CSV file
     * @param model
     * @param testData
     * @return
     */
    private static String runKNNClassification(KNNModel model, DataPoint[] testData) {
        String output = printKNNMetrics(model, k, testData);
        return output;
    }


    /**
     * Runs the validation on Clustering models for classification. Returns a string with the data formatted for CSV file
     * @param model
     * @param testData
     * @return
     */
    private static String runClusteringClassification(ClusteringModel model, DataPoint[] testData) {
        double distortion = 0;

        try {
            distortion = model.getDistrortion();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.assignClasses();


        KNNModel knnModel = new KNNModel(model.getCentroids());
        knnModel.similarityMatrices = model.similarityMatrices;

        String output = distortion + "," + printKNNMetrics(knnModel, k, testData);

        return output;
    }

    /**
     * Runs the clustering models and returns a string formatted for CSV
     * @param model
     * @param output
     * @return
     */
    private static String runClusteringRegression(ClusteringModel model, String output) {
        try {
            double distrortion = model.getDistrortion();

            output = output + distrortion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }


    private static String readEntireFile(String filePath) { // Reads the file
        File file = new File(filePath);
        String retString = "";
        if (file.exists()) {
            try {
                Scanner scan = new Scanner(file);
                scan.useDelimiter("\\Z");
                if (scan.hasNext()) {
                    retString = scan.next();
                }
                scan.close();
            } catch (FileNotFoundException ignored) {
                return "File not found for path: " + file;
            }
        } else {
            System.out.println("File doesn't exist");
        }

        return retString;
    }

    /**
     * Adds the string to the end of a file
     *
     * @param line string to be added
     * @param file the file to be added to
     */
    public static void appendToFile(String line, File file) { // adds on to file
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.append(line);
            writer.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Creates a file if there doesn't exist one already. Then returns the file at the filepath
     *
     * @param filePath file path
     * @return the file (either old or newly created)
     */
    public static File createNewFile(String filePath) { // makes a file
        String newPath = filePath;
        File file = new File(newPath + ".csv");
        int i = 2;

        while (file.exists()) {
            newPath = filePath + "-" + i;
            file = new File(newPath + ".csv");
            i += 1;
        }

        try {
            file.createNewFile();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }


        return file;
    }

    /**
     * Folds the data into 10 fairly equal folds
     *
     * @param points The data to be folded
     * @return a folded list
     */
    private static DataPoint[][] fold(DataPoint[] points) {
        points = Helper.scramble(points);//scrambles the data

        DataPoint[][] data = new DataPoint[10][points.length];
        int[] counters = new int[10];//so the elements go into the array in order (ie. all the null values are at the end)
        Random rand = new Random();

        for (int i = 0; i < 10; i++) { //ensures all folds have at least one DataPoint
            data[i][counters[i]++] = points[i];
        }

        for (int i = 10; i < points.length; i++) {
            int random = rand.nextInt(10);
            data[random][counters[random]++] = points[i]; //places the points into the folds in order so as to avoid null values
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = trim(data[i]);// gets rid of trailing null values
        }

        return data;
    }

    /**
     * Removes the null values from end of array
     *
     * @param points The array
     * @return The values in the same order as the appear in points without trailing null values
     */
    private static DataPoint[] trim(DataPoint[] points) {
        int i = 0;
        DataPoint point = points[i++];

        while (point != null) {
            point = points[i++];
        }


        return (DataPoint[]) Arrays.copyOf(points, i - 1);
    }
}
