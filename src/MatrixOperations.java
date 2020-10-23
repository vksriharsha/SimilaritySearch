public class MatrixOperations {

    public static int[] elementWiseMultiplication(int[] a1, int[] a2){
        int [] output = new int[a1.length];
        for(int i=0; i<a1.length ; i++) {

            if(a1[i] == 0){
                output[i] = -1;
            }
            else {
                output[i] = a1[i] * a2[i];
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

        if(min == 999999){
            for(int i=0; i<a.length; i++){
                //System.out.print(a[i]+", ");
            }
            //System.out.println();
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
