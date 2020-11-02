public class MinHashAccuracy {


    //method
    // For every pair of files in the document collection, compute exact Jaccard Similarity and
    // approximate Jaccard similarity
    public int accuracy(String folder, int numPermutations, double error){
        MinHashSimilarities minHashSim = new MinHashSimilarities(folder, numPermutations);
        MinHash minHash = minHashSim.getMinHash();
        String[] allDocs = minHash.allDocs();
        double exactJS = 0.0;
        double approximateJS = 0.0;
        int numPairs = 0;


        for (int i = 0; i < allDocs.length; i++){
            String document1 = allDocs[i];
            for( int j = i + 1; j < allDocs.length; j++){
                String document2 = allDocs[j];
                exactJS = minHashSim.exactJaccard(document1, document2);
                approximateJS = minHashSim.approximateJaccard(document1, document2);
                if (Math.abs(exactJS - approximateJS) > error){
                    numPairs ++;
                }
            }
        }

    return numPairs;
    }

    public static void main(String[] args) {

        MinHashAccuracy minHashAccuracy = new MinHashAccuracy();
        String dir = "/Users/harshavk/Desktop/gitrepos/Docs/space";


        int accuracy = minHashAccuracy.accuracy(dir,400, 0.04);
        System.out.println("Experiment 1 : Permutations = 400, error = 0.04");
        System.out.println("Num documents miscalculated: "+accuracy);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy2 = minHashAccuracy.accuracy(dir,400, 0.07);
        System.out.println("Experiment 2 : Permutations = 400, error = 0.07");
        System.out.println("Num documents miscalculated: "+accuracy2);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy2*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy3 = minHashAccuracy.accuracy(dir,400, 0.09);
        System.out.println("Experiment 3 : Permutations = 400, error = 0.09");
        System.out.println("Num documents miscalculated: "+accuracy3);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy3*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy4 = minHashAccuracy.accuracy(dir,600, 0.04);
        System.out.println("Experiment 4 : Permutations = 600, error = 0.04");
        System.out.println("Num documents miscalculated: "+accuracy4);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy4*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy5 = minHashAccuracy.accuracy(dir,600, 0.07);
        System.out.println("Experiment 5 : Permutations = 600, error = 0.07");
        System.out.println("Num documents miscalculated: "+accuracy5);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy5*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy6 = minHashAccuracy.accuracy(dir,600, 0.09);
        System.out.println("Experiment 6 : Permutations = 600, error = 0.09");
        System.out.println("Num documents miscalculated: "+accuracy6);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy6*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        int accuracy7 = minHashAccuracy.accuracy(dir,800, 0.04);
        System.out.println("Experiment 7 : Permutations = 800, error = 0.04");
        System.out.println("Num documents miscalculated: "+accuracy7);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy7*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy8 = minHashAccuracy.accuracy(dir,800, 0.07);
        System.out.println("Experiment 8 : Permutations = 800, error = 0.07");
        System.out.println("Num documents miscalculated: "+accuracy8);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy8*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        int accuracy9 = minHashAccuracy.accuracy(dir,800, 0.09);
        System.out.println("Experiment 9 : Permutations = 800, error = 0.09");
        System.out.println("Num documents miscalculated: "+accuracy9);
        System.out.println("Percentage of docs miscalculated: "+ ((accuracy9*100)/500500));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}

