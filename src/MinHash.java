import java.io.File;
import java.util.*;

public class MinHash {

    private String folder;
    private int numPermutations;
    private int[][] termDocumentMatrix;
    private int[][] modifiedTermDocumentMatrix;
    private int[][] minHashMatrix;
    private int numTerms;
    private int modifiedNumTerms;
    private int a[];
    private int b[];
    private int[][] permutations;

    private static final int INF = 9999999;

    //constructor
    public MinHash(String folder, int numPermutations){
        this.folder = folder;
        this.numPermutations = numPermutations;

        preProcess();

        System.out.println("Ended Preprocessing");
        a = new int[this.numPermutations];
        b = new int[this.numPermutations];
        permutations = new int[this.modifiedNumTerms][this.numPermutations];

        for(int i=0; i<this.numPermutations; i++){
            a[i] = PrimeNumberGenerator.getRandomPrime(modifiedNumTerms());
            b[i] = PrimeNumberGenerator.getRandomPrime(modifiedNumTerms());

            for(int j=0; j<modifiedNumTerms(); j++){
                permutations[j][i] = (a[i]*j+b[i])%modifiedNumTerms();
            }

        }

        System.out.println("Ended permutations generation");
        //print2D(permutations);

//        for(int i=0; i<numPermutations(); i++) {
//            int numcollisions = 0;
//            for (int j = 0; j < numTerms(); j++) {
//                for (int k = 0; k < numTerms(); k++) {
//                    if (j != k && permutations[j][1] == permutations[k][1]) {
//                        numcollisions++;
//                    }
//                }
//
//            }
//
//            System.out.println("Num collisions in list "+i+" : " + numcollisions);
//
//        }

    }

    //Returns all the names of files in the document collection
    public String[] allDocs(){
        String[] docsInFolder = null;

        File docsFolder = new File(folder);
        docsInFolder = docsFolder.list();

        return docsInFolder;
    }


    public void preProcess(){

            String[] docs = allDocs();
            LinkedHashMap<String,Integer[]> modifiedTermDocHash = new LinkedHashMap<String,Integer[]>();
            LinkedHashMap<String,Integer[]> termDocHash = new LinkedHashMap<String,Integer[]>();

            int fileNum = 0;
            try {
                for (String doc : docs) {
                    System.out.println("Processing : "+doc);
                    File f = new File(folder + File.separator + doc);
                    if (f.isFile()) {
                        Scanner scanner = new Scanner(f);

                        while(scanner.hasNext()){

                            String nextword = scanner.next();
                            String nextwordlcs = nextword.toLowerCase();

                            if(nextwordlcs.equals(".")||nextwordlcs.equals(",")||nextwordlcs.equals(":")||
                                    nextwordlcs.equals(";")||nextwordlcs.equals("'")||nextwordlcs.length()<=2||nextwordlcs.equals("the")){


                            }
                            else{
                                nextwordlcs = nextwordlcs.replace(".","").replace(",","").replace(":","")
                                        .replace(";","").replace("'","");


                                boolean notNovel = true;
                                int addendum = 1000;
                                String oldword = nextwordlcs;

                                if(termDocHash.get(nextwordlcs) == null){
                                    Integer[] freq = new Integer[docs.length];
                                    for (int i = 0; i < docs.length; i++) {
                                        freq[i] = 0;
                                    }
                                    freq[fileNum]++;
                                    termDocHash.put(nextwordlcs, freq);
                                }
                                else{
                                    Integer[] freq = termDocHash.get(nextwordlcs);
                                    freq[fileNum]++;
                                    termDocHash.put(nextwordlcs, freq);
                                }
                                while(notNovel) {
                                    if (modifiedTermDocHash.get(nextwordlcs) == null || modifiedTermDocHash.get(nextwordlcs)[fileNum]==0) {
                                        Integer[] freq = null;
                                        if(modifiedTermDocHash.get(nextwordlcs) == null) {
                                            freq = new Integer[docs.length];
                                            for (int i = 0; i < docs.length; i++) {
                                                freq[i] = 0;
                                            }
                                        }
                                        else{
                                            freq = modifiedTermDocHash.get(nextwordlcs);
                                        }
                                        freq[fileNum]++;
                                        modifiedTermDocHash.put(nextwordlcs, freq);
                                        notNovel = false;
                                    } else {
                                        nextwordlcs = oldword+addendum+"";
                                        addendum++;

                                    }
                                }
                            }

                        }
                        fileNum++;
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            modifiedTermDocumentMatrix = new int[modifiedTermDocHash.size()][fileNum];
            termDocumentMatrix = new int[termDocHash.size()][fileNum];

            //System.out.println("Resulting hashmap is : "+ termDocHash.size());

            modifiedNumTerms = modifiedTermDocHash.size();
            numTerms = termDocHash.size();

            int j=0;
            for (Map.Entry<String,Integer[]> entry : modifiedTermDocHash.entrySet()){
                //System.out.println(j+ " : "+ entry.getKey());
                Integer[] wordfreq = entry.getValue();
                int[] wordfreqint = new int[wordfreq.length];

                for(int k=0; k<wordfreq.length; k++){
                    wordfreqint[k] = (int)wordfreq[k];
                }
                modifiedTermDocumentMatrix[j] = wordfreqint;

                j++;

            }

            int t=0;

            for (Map.Entry<String,Integer[]> entry : termDocHash.entrySet()){
            //System.out.println(j+ " : "+ entry.getKey());
            Integer[] wordfreq = entry.getValue();
            int[] wordfreqint = new int[wordfreq.length];

            for(int k=0; k<wordfreq.length; k++){
                wordfreqint[k] = (int)wordfreq[k];
            }
            termDocumentMatrix[t] = wordfreqint;

            t++;

        }




    }

    //Returns the MinHash Matrix of the collection
    public int[][] minHashMatrix(){

        minHashMatrix = new int[numPermutations()][allDocs().length];

        for(int i=0 ; i<numPermutations(); i++){
//            for(int j=0; j< allDocs().length; j++){
//                minHashMatrix[i][j] = INF;
//            }
            Arrays.fill(minHashMatrix[i],INF);
        }

        System.out.println("initialized minHash");

//        for(int i=0; i<numPermutations(); i++){
//            for(int j=0; j<modifiedNumTerms(); j++){
//                for(int k=0; k< allDocs().length; k++){
//                    if(modifiedTermDocumentMatrix[j][k] == 1){
//
//                        if(minHashMatrix[i][k] > permutations[j][i]){
//                            minHashMatrix[i][k] = permutations[j][i];
//                        }
//                    }
//                }
//            }
//            System.out.println("Permutation : "+i + " is done.");
//        }


        for(int i=0; i<numPermutations(); i++){
            for(int j=0; j< allDocs().length; j++){
//                int [] termCol = MatrixOperations.getColumn(modifiedTermDocumentMatrix,j);
//                int [] permCol = MatrixOperations.getColumn(permutations,i);
                int [] res = MatrixOperations.elementWiseMultiplication(modifiedTermDocumentMatrix,j, permutations, i);
                int minRes = MatrixOperations.minimumValueInVector(res);
                minHashMatrix[i][j] = minRes;
            }
            System.out.println("Done for permutation : "+i);
        }


        System.out.println("Done Calculating minHash");


        return minHashMatrix;
    }


    //Return the term document matrix of the collection
    public int[][] termDocumentMatrix(){

        if(termDocumentMatrix != null)
        return termDocumentMatrix;
        else {
            preProcess();
            return termDocumentMatrix;
        }

    }

    //Returns the size of union of all documents (after preprocessing)
    public int numTerms(){
        return this.numTerms;
    }

    public int modifiedNumTerms(){
        return this.modifiedNumTerms;
    }


    //Returns the number of permutations used to construct the MinHash matrix
    public int numPermutations(){

        return this.numPermutations;
    }


    public static void print2D(int mat[][])
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
        MinHash mh = new MinHash("/Users/harshavk/Desktop/gitrepos/Docs/space",10);
//        for(String s : mh.allDocs())
//        System.out.println(s);

        int[][] output = mh.modifiedTermDocumentMatrix;
        int[][] mhm = mh.minHashMatrix();
        System.out.println("Printing matrix");
        print2D(output);
//        System.out.println();
//        System.out.println("***********************************");
//        System.out.println();
//        print2D(mh.modifiedTermDocumentMatrix);




    }

}


class PrimeNumberGenerator{

    public static int getRandomPrime(int bound){
        Random rand = new Random();
        int num = 0;

        num = rand.nextInt(bound)+1;

        while(!isPrime(num)){
            num = rand.nextInt(bound)+1;
        }
        return num;
    }

    public static boolean isPrime(int num){
        if (num <= 3 || num % 2 == 0)
            return num == 2 || num == 3;
        int divisor = 3;
        while ((divisor <= Math.sqrt(num)) && (num % divisor != 0))
            divisor += 2;
        return num % divisor != 0;
    }

    public static int getClosestPrime (int num){

        while (!isPrime(num)){
            num++;
        }
        return num;

    }

}