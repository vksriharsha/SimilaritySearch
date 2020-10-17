public class MinHash {

    private String folder;
    private int numPermutations;

    //constructor
    public MinHash(String folder, int numPermutations){
        this.folder = folder;
        this.numPermutations = numPermutations;
    }

    //Returns all the names of files in the document collection
    public String[] allDocs(){

        return null;
    }

    //Returns the MinHash Matrix of the collection
    public int[][] minHashMatrix(){

        return null;
    }

    //Return the term document matrix of the collection
    public int[][] termDocumentMatrix(){

        return null;
    }

    //Returns the size of union of all documents (after preprocessing)
    public int numTerms(){

        return 0;
    }


    //Returns the number of permutations used to construct the MinHash matrix
    public int numPermutations(){

        return 0;
    }

}
