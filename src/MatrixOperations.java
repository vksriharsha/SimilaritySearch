import java.util.Arrays;

public class MatrixOperations {

    public static int[] elementWiseMultiplication(int[][] a1, int a1index, int[][] a2, int a2index){
        int [] output = new int[a1.length];
        Arrays.fill(output,-1);

        for(int i=0; i<a1.length ; i++) {

            if(a1[i][a1index] != 0){
                output[i] = a1[i][a1index] * a2[i][a2index];
            }

        }
        return output;
    }


    public static int minimumValueInVector(int [] a){
        int min = 999999;
        for(int i=0; i<a.length; i++){
            if(a[i] < min && a[i] >= 0){
                min = a[i];
            }
        }

        return min;
    }

    public static int[] getColumn(int[][] array, int index){
        int[] column = new int[array.length]; // Here I assume a rectangular 2D array!
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }


}
