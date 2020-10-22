public class MinHashAccuracy {


    //method
    // For every pair of files in the document collection, compute exact Jaccard Similarity and
    // approximate Jaccard similarity
    public int accuracy(String folder, int numPermutations, double error){
        MinHashSimilarities minHashSim = new MinHashSimilarities(folder, numPermutations);
        MinHash minHash = new MinHash(folder, numPermutations);
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
}

