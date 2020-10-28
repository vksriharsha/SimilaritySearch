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

//            if (Math.abs(s - threshold) < INF){
//                INF = Math.abs(s - threshold);
//                System.out.println("Value of s = "+s);
//                bandValue = i;
//            }

            if(s<threshold && Math.abs(s - threshold) < INF){
                INF = Math.abs(s - threshold);
                System.out.println("Value of s = "+s);
                bandValue = i;
            }
        }

        System.out.println("Band Value chosen : "+bandValue);
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
        NearDuplicates nearDuplicates = new NearDuplicates("/Users/harshavk/Desktop/gitrepos/Docs/F17PA2_Small", 600, 0.9);

        //MinHashSimilarities minHashSimilarities = new MinHashSimilarities("/Users/harshavk/Desktop/gitrepos/Docs/F17PA2", 1);

        System.out.println();
        System.out.println();
        System.out.println("Experiment Set 1 :    Permuatations = 600,  s = 0.9");
        System.out.println("___________________________________________________");
        System.out.println();

        ArrayList<String> exp1 = nearDuplicates.nearDuplciateDetector("space-29.txt");
        System.out.println("Near duplicates of space-29.txt : "+ exp1);
        System.out.println();
        System.out.println();

        ArrayList<String> exp2 = nearDuplicates.nearDuplciateDetector("baseball37.txt");
        System.out.println("Near duplicates of baseball37.txt : "+ exp2);
        System.out.println();
        System.out.println();


        ArrayList<String> exp3 = nearDuplicates.nearDuplciateDetector("hockey28.txt");
        System.out.println("Near duplicates of hockey28.txt : "+ exp3);
        System.out.println();
        System.out.println();
    }

}
