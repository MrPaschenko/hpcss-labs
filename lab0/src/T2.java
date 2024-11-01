import java.util.Arrays;

// MG = SORT(MF - MH * MK)

public class T2 extends Thread {
    static int N;
    static double[][] MF;
    static double[][] MH;
    static double[][] MK;
    static double[][] MG;
    static String inputType;

    public void run() {
        // start
        System.out.println("T2 is started");

        // input
        second_input();

        // calculation
        double[][] mult = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int n = 0; n < N; n++) {
                    mult[i][j] += MH[i][n] * MK[n][j];
                }
            }
        }

        MG = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MG[i][j] = MF[i][j] - mult[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            Arrays.sort(MG[i]);
        }

        // output
        System.out.println("\nResult:");
        for (double[] doubles : MG) {
            System.out.println(Arrays.toString(doubles));
        }

        // finish
        System.out.println("T2 is finished");
    }

    public static void second_input() {
        N = Data.inputN(2);
        inputType = Data.inputInputType(N);

        MF = new double[N][N];
        MH = new double[N][N];
        MK = new double[N][N];

        if (inputType.equals("Keyboard")) {
            MF = Data.inputMatrix(N, "MF");
            for (double[] doubles : MF) {
                System.out.println(Arrays.toString(doubles));
            }

            MH = Data.inputMatrix(N, "MH");
            for (double[] doubles : MH) {
                System.out.println(Arrays.toString(doubles));
            }

            MK = Data.inputMatrix(N, "MK");
            for (double[] doubles : MK) {
                System.out.println(Arrays.toString(doubles));
            }
        }

        if (inputType.equals("Random")) {
            MF = Data.generateMatrix(N);

            MH = Data.generateMatrix(N);

            MK = Data.generateMatrix(N);
        }

        if (inputType.equals("Same")) {
            System.out.print("Enter values for all function 2 elements: ");
            double value = Data.inputValue();

            for (int i = 0; i < N; i++) {
                Arrays.fill(MF[i], value);

                Arrays.fill(MH[i], value);

                Arrays.fill(MK[i], value);
            }
        }


        if (inputType.equals("File")) {
            Data.loadFile("MF");
            MF = Data.inputMatrixFromFile(N);
            for (double[] doubles : MF) {
                System.out.println(Arrays.toString(doubles));
            }

            Data.loadFile("MH");
            MH = Data.inputMatrixFromFile(N);
            for (double[] doubles : MH) {
                System.out.println(Arrays.toString(doubles));
            }

            Data.loadFile("MK");
            MK = Data.inputMatrixFromFile(N);
            for (double[] doubles : MK) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }
}
