import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MinHashSimilarities {
    private int[][] termDocumentMatrix;
    private int[][] minHashMatrix;
    private MinHash minHash;

    //Constructor
    public MinHashSimilarities(String folder, int numPermutations){
        minHash = new MinHash(folder, numPermutations);
        this.termDocumentMatrix = minHash.termDocumentMatrix();
        this.minHashMatrix = minHash.minHashMatrix();
    }

    public static void main(String[] args) {

        MinHashSimilarities mhs = new MinHashSimilarities("/Users/harshavk/Desktop/gitrepos/Docs/space", 400);

        for(int i = 2; i<1000; i++) {
            double result = mhs.approximateJaccard("space-1.txt", "space-"+i+".txt");
            System.out.println("Estimate Jac "+i+ " : "+ result);
        }

        for(int i = 2; i<1000 ;i++) {
            double exactJac = mhs.exactJaccard("space-1.txt","space-"+i+".txt");
            System.out.println("Exact Jac "+i+ " : "+ exactJac);
        }

    }

    //Methods

    // Returns the exact Jaccard Similarity of the file1 and file2
    public double exactJaccard(String file1, String file2) {

        String[] allDocs = minHash.allDocs();

        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < allDocs.length; i ++){
            if (allDocs[i].equals(file1)){
                index1 = i;
            }
            if (allDocs[i].equals(file2)){
                index2 = i;
            }
        }

        int numTerms = minHash.numTerms();
        int intersection = 0;
        int union = 0;

        for (int i = 0; i < numTerms; i++){
            intersection += Math.min(termDocumentMatrix[i][index1], termDocumentMatrix[i][index2]);
            union += Math.max(termDocumentMatrix[i][index1], termDocumentMatrix[i][index2]);
        }
        double result = (double) intersection/union;

        return result;
    }

    // Estimates and returns the Jaccard similarity of documents file1 and file2 by comparing the MinHash signatures of file1 and file2
    public double approximateJaccard(String file1, String file2) {

        int count = 0;

        int[] file1Sig = minHasSig(file1);
        int[] file2Sig = minHasSig(file2);

        int length = file1Sig.length;

        for (int i = 0; i < length; i++){
            if (file1Sig[i] == file2Sig[i]){
                count ++;
            }
        }

        double result = (double) count/length;
        return result;
    }

    // Returns the MinHash the minhash signature of the document named fileName
    public int[] minHasSig(String fileName){

        String[] allDocs = minHash.allDocs();
        ArrayList<String> list1 = new ArrayList<>();
        LinkedList<String> list2 = new LinkedList<>();


//        list1.size();

        int index = 0;
        for (int i = 0; i < allDocs.length; i ++){
            if (allDocs[i].equals(fileName)){
                index = i;
            }
        }
//        String[] file = allDocs();

        int[] result = MatrixOperations.getColumn(minHashMatrix, index);
        return result;
    }

    public MinHash getMinHash() {
        return minHash;
    }
}
