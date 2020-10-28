public class MinHashTime {
    public void timer(String folder, int numPermutations){

        long startTime = System.nanoTime();
        MinHashSimilarities minHashSimilarities = new MinHashSimilarities(folder, numPermutations);
        long stopTime = System.nanoTime();
        System.out.println("Time taken to initialize MinHashSimilarities: "+ ((stopTime - startTime)/60_000_000_000.0)+" minutes");

        System.out.println();

        String[] docs = minHashSimilarities.getMinHash().allDocs();
        int mlen = minHashSimilarities.getMinHash().minHashMatrix().length;

        long startTimeApproxJac = System.nanoTime();
        double [][] jaccardApprox = new double[docs.length][docs.length];
        for(int i = 0; i<docs.length-1; i++) {
            for(int j=i+1; j<minHashSimilarities.getMinHash().allDocs().length; j++){

                jaccardApprox[i][j]= minHashSimilarities.approximateJaccard(i, j, mlen);
                jaccardApprox[j][i] = jaccardApprox[i][j];


            }
            System.out.println("Approx jaccard done for doc: "+i);
        }
        System.out.printf("Approximate Jaccard values matrix :");
        System.out.println();
        print2D(jaccardApprox);

        long stopTimeApproxJac = System.nanoTime();
        System.out.println();
        System.out.println("Time taken for approx Jaccard Similarity calculation: "+ ((stopTimeApproxJac - startTimeApproxJac)/60_000_000_000.0)+ " minutes");

        double [][] jaccardExact = new double[docs.length][docs.length];
        long startTimeExactJac = System.nanoTime();
        for(int i = 0; i<docs.length-1; i++) {
            for (int j = i + 1; j < minHashSimilarities.getMinHash().allDocs().length; j++) {
                jaccardExact[i][j] = minHashSimilarities.exactJaccard(i, j);
                jaccardExact[j][i] = jaccardExact[i][j];

            }
            System.out.println("Exact jaccard done for doc: "+i);
        }
        System.out.println("Exact Jaccard values matrix :");
        System.out.println();
        print2D(jaccardExact);
        long stopTimeExactJac = System.nanoTime();
        System.out.println();
        System.out.println("Time taken for exact Jaccard Similarity calculation: "+ ((stopTimeExactJac - startTimeExactJac)/60_000_000_000.0)+ " minutes");

    }

    public static void print2D(double mat[][])
    {
        // Loop through all rows
        for (int i = 0; i < mat.length; i++) {

            // Loop through all elements of current row
            for (int j = 0; j < mat[i].length; j++)
                System.out.print(mat[i][j] + " ");

            System.out.println();
        }
    }


    public static void main(String[] args) {
        MinHashTime minHashTime = new MinHashTime();
        minHashTime.timer("/Users/harshavk/Desktop/gitrepos/Docs/space", 600);

    }
}
