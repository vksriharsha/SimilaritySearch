import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LSH {

    private int[][] minHashMatrix;
    private String[] docNames;
    private int bands;
    private int k;
    private int r;
    private int remainder;
    private int a[];
    private int b[];
    private ArrayList[][] hashTables;
    private int lengthHashTable;
//    private ArrayList<<ArrayList>String>;

    //Constructor
    public LSH(int[][] minHashMatrix, String[] docNames, int bands){
        this.minHashMatrix = minHashMatrix;
        this.docNames = docNames;
        this.bands = bands;
        this.k = minHashMatrix.length;
        this.r = this.k/this.bands;
        this.remainder = this.k%this.bands;
        this.lengthHashTable = PrimeNumberGenerator.getClosestPrime(docNames.length);

        hashTables = new ArrayList[lengthHashTable][this.bands];

        this.a = new int[bands];
        this.b = new int[bands];

        for( int i = 0; i < bands; i ++){
            a[i] = PrimeNumberGenerator.getRandomPrime(docNames.length);
            b[i] = PrimeNumberGenerator.getRandomPrime(docNames.length);
        }

//        hashTables = new ArrayList<String>[][];

        for(int i = 0; i < docNames.length; i++){
            for (int j = 0; j < bands; j ++){
                String stringBand = "";
                for(int x = r*j; x < r*(j+1); x++){
                    if(j==bands-1){
                        if(x<k){
                            stringBand += minHashMatrix[x][i];
                        }
                        else{
                            break;
                        }
                    }
                    stringBand += minHashMatrix[x][i];
                }
                storeInHashTable(stringBand, j, docNames[i]);
            }
        }
    }

    //Methods

    public void storeInHashTable(String stringBand, int hashTableID, String docName){
        int hashValue = hashFunction(stringBand, hashTableID);
        if(hashTables[hashValue][hashTableID] == null){
            hashTables[hashValue][hashTableID] = new ArrayList();
        }

        hashTables[hashValue][hashTableID].add(docName);
    }

    public int hashFunction(String s, int hashID){
        int x = s.hashCode();
        int value = (a[hashID]*x + b[hashID]) % lengthHashTable;
        if(value < 0){
            value = -1*value;
        }
        return value;
    }


    // Takes name of a document docName as parameter and returns an array list of names of the near duplicate documents.
    // Return type is ArrayList of Strings.
    public ArrayList<String> nearDuplicatesOf(String docName){
        HashSet<String> nearDuplicate = new HashSet<>();

        int index = 0;
        for (int i = 0; i < docNames.length; i ++){
            if (docNames[i].equals(docName)){
                index = i;
            }
        }
//        String[] file = allDocs();


        int[] MinHashSigofdocName = MatrixOperations.getColumn(minHashMatrix, index);


        for (int j = 0; j < bands; j ++){
            String stringBand = "";
            for(int x = r*j; x < r*(j+1); x++){
                if(j==bands-1){
                    if(x<k){
                        stringBand += minHashMatrix[x][index];
                    }
                    else{
                        break;
                    }
                }
                stringBand += minHashMatrix[x][index];
            }
            int hashValue = hashFunction(stringBand, j);
            for (int i =0; i < hashTables[hashValue][j].size(); i ++){
                nearDuplicate.add(hashTables[hashValue][j].get(i).toString());
            }

        }
        ArrayList<String> nearDuplicateArraylist = new ArrayList<>();

        for(String s : nearDuplicate){
            nearDuplicateArraylist.add(s);
        }

        return nearDuplicateArraylist;
    }
}
