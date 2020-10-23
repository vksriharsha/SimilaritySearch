public class MinHashTime {
    public void timer(String folder, int numPermutations){

        long startTime = System.nanoTime();
        MinHashSimilarities minHashSimilarities = new MinHashSimilarities(folder, numPermutations);
        long stopTime = System.nanoTime();
        System.out.println("Time taken to initialize MinHashSimilarities: "+ (stopTime - startTime)+" ns");

        System.out.println();

        String[] docs = minHashSimilarities.getMinHash().allDocs();

        long startTimeApproxJac = System.nanoTime();
        double [][] jaccardApprox = new double[docs.length][docs.length];
        for(int i = 0; i<docs.length-1; i++) {
            for(int j=i+1; j<minHashSimilarities.getMinHash().allDocs().length; j++){

                jaccardApprox[i][j]= minHashSimilarities.approximateJaccard(docs[i], docs[j]);
                jaccardApprox[j][i] = jaccardApprox[i][j];

            }
        }
        System.out.printf("Approximate Jaccard values matrix :");
        System.out.println();
        print2D(jaccardApprox);

        long stopTimeApproxJac = System.nanoTime();
        System.out.println();
        System.out.println("Time taken for approx Jaccard Similarity calculation: "+ (stopTimeApproxJac - startTimeApproxJac)+ " ns");

        double [][] jaccardExact = new double[docs.length][docs.length];
        long startTimeExactJac = System.nanoTime();
        for(int i = 0; i<docs.length-1; i++) {
            for (int j = i + 1; j < minHashSimilarities.getMinHash().allDocs().length; j++) {
                jaccardExact[i][j] = minHashSimilarities.exactJaccard(docs[i], docs[j]);
                jaccardExact[j][i] = jaccardExact[i][j];

            }
        }
        System.out.println("Exact Jaccard values matrix :");
        System.out.println();
        print2D(jaccardExact);
        long stopTimeExactJac = System.nanoTime();
        System.out.println();
        System.out.println("Time taken for exact Jaccard Similarity calculation: "+ (stopTimeExactJac - startTimeExactJac)+ " ns");

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
        minHashTime.timer("/Users/harshavk/Desktop/gitrepos/Docs/space", 20);

    }
}
