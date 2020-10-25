import java.util.ArrayList;

public class NearDuplicates {

//    MinHash mh = new MinHash("C://",4);

    private String folder;
    private int numPermutations;
    private double threshold;
    private LSH lsh;
    private MinHash minHash;
    private String[] docNames;
    private int bands;

    //Constructor
    public NearDuplicates(String folder, int numPermutations, double threshold){
        this.folder = folder;
        this.numPermutations = numPermutations;
        this.threshold = threshold;
        this.minHash = new MinHash(folder, numPermutations);
        this.docNames = minHash.allDocs();

        this.bands = calculateBandsValue(numPermutations, threshold);

        this.lsh = new LSH(minHash.minHashMatrix(), docNames, this.bands);
    }

    //Methods

    public int calculateBandsValue (int numPermutations, double threshold){

        double INF = 9999999.9;
        int bandValue = 0;
        int r = 0;
        double s;


        for (int i = 1; i < numPermutations ; i ++){
            r = numPermutations/i;
            s = Math.pow(1.0 / i, 1.0 / r);

            if (Math.abs(s - threshold) < INF){
                INF = Math.abs(s - threshold);
                bandValue = i;
            }
        }
        return bandValue;
    }

    // Takes the name of a document as parameter, which is a string.
    // Returns a list of documents that are at least s-similar to docName, by calling
    // the method nearDuplicatesOf from LSH.
    public ArrayList<String> nearDuplciateDetector(String docName){
//        ArrayList<String> nearDuplicate = ;

        return lsh.nearDuplicatesOf(docName);
    }

    public static void main(String[] args) {
//        NearDuplicates nearDuplicates = new NearDuplicates("G:\\test", 400, 0.95);
//
//        ArrayList<String> a = nearDuplicates.nearDuplciateDetector("space-2.txt");
//        System.out.println(a);

        MinHashSimilarities minHashSimilarities = new MinHashSimilarities("G:\\test", 400);

        System.out.printf(minHashSimilarities.exactJaccard("space-2.txt", "space-2 - Copy.txt")+ "");

    }

}
