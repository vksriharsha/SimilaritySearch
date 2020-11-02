import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
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


    // Returns the exact Jaccard Similarity of the file1 and file2
    public double exactJaccard(String file1, String file2) {

        HashMap<String,Integer> allDocsHashMap = minHash.allDocsHashMap();

        int index1 = 0;
        int index2 = 0;

        index1 = (int)allDocsHashMap.get(file1);
        index2 = (int)allDocsHashMap.get(file2);

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

        int file1Sig = minHasSigIndex(file1);
        int file2Sig = minHasSigIndex(file2);

        for (int i = 0; i < minHashMatrix.length; i++){
            if (minHashMatrix[i][file1Sig] == minHashMatrix[i][file2Sig]){
                count ++;
            }
        }

        double result = (double) count/minHashMatrix.length;
        return result;
    }


    // Returns the MinHash the minhash signature of the document named fileName
    public int[] minHasSig(String fileName){

        String[] allDocs = minHash.allDocs();
        ArrayList<String> list1 = new ArrayList<>();
        LinkedList<String> list2 = new LinkedList<>();

        int index = 0;
        for (int i = 0; i < allDocs.length; i ++){
            if (allDocs[i].equals(fileName)){
                index = i;
            }
        }

     int[] result = MatrixOperations.getColumn(minHashMatrix, index);
        return result;
    }


    public int minHasSigIndex(String fileName){

        HashMap<String,Integer> allDocsHashMap = minHash.allDocsHashMap();
        int index = 0;

        index = allDocsHashMap.get(fileName);

        return index;
    }

    public MinHash getMinHash() {
        return minHash;
    }



    public static void main(String[] args) {

        MinHashSimilarities mhs = new MinHashSimilarities("/Users/harshavk/Desktop/Docs/space2", 400);

        String[] docs = mhs.getMinHash().allDocs();
        for(int i = 2; i<20; i++) {
            double result = mhs.approximateJaccard(docs[0], docs[i]);
            System.out.println("Estimate Jac "+i+ " : "+ result);
        }

        for(int i = 2; i<20 ;i++) {
            double exactJac = mhs.exactJaccard(docs[0],docs[i]);
            System.out.println("Exact Jac "+i+ " : "+ exactJac);
        }

    }



}
