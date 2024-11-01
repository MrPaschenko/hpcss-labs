public class Data {
    public static final int N = 2000;
    public static final int P = 4;
    public static final int H = N / P;

    public static ResourceMonitor resourceMonitor = new ResourceMonitor();
    public static SynchronisationMonitor synchronisationMonitor = new SynchronisationMonitor();

    public static int[][] multiplyMatrix(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication.");
        }

        int[][] result = new int[rowsA][colsB];

        for(int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                result[i][j] = 0;
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return result;
    }

    public static int[][] getSubMatrix(int[][] matrix, int id) {
        int[][] result = new int[Data.H][Data.N];

        for (int i = 0; i < Data.H; i++) {
            System.arraycopy(matrix[id * Data.H + i], 0, result[i], 0, Data.N);
        }

        return result;
    }
}
