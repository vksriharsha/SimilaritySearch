public class MinHashSimilarities {
    private int[][] termDocumentMatrix;
    private int[][] minHashMatrix;

    //Constructor
    public MinHashSimilarities(String folder, int numPermutations){
        MinHash minHash = new MinHash(folder, numPermutations);
        this.termDocumentMatrix = minHash.termDocumentMatrix();
        this.minHashMatrix = minHash.minHashMatrix();
    }

    public double exactJaccard(String file1, String file2){

        return 0.0;
    }

    public double approximateJaccard(String file1, String file2){

        return 0.0;
    }

    public int[] minHasSig(String fileName){

        return null;
    }
}
