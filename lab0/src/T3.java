import java.util.Arrays;

// O = SORT(P) * SORT(MR * MS)

public class T3 extends Thread {
    static int N;
    static double[] P;
    static double[][] MR;
    static double[][] MS;
    static double[] O;
    static String inputType;

    public void run() {
        // start
        System.out.println("T3 is started");

        // input
        third_input();

        // calculation
        double[][] mult = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int n = 0; n < N; n++) {
                    mult[i][j] += MR[i][n] * MS[n][j];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            Arrays.sort(mult[i]);
        }

        Arrays.sort(P);

        O = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                O[i] += P[j] * mult[i][j];
            }
        }

        // output
        System.out.println("\nResult:");
        System.out.println(Arrays.toString(O));

        // finish
        System.out.println("T3 is finished");
    }

    public static void third_input() {
        N = Data.inputN(3);
        inputType = Data.inputInputType(N);

        P = new double[N];
        MR = new double[N][N];
        MS = new double[N][N];

        if (inputType.equals("Keyboard")) {
            P = Data.inputVector(N, "P");
            System.out.println(Arrays.toString(P));

            MR = Data.inputMatrix(N, "MR");
            for (double[] doubles : MR) {
                System.out.println(Arrays.toString(doubles));
            }

            MS = Data.inputMatrix(N, "MS");
            for (double[] doubles : MS) {
                System.out.println(Arrays.toString(doubles));
            }

        }

        if (inputType.equals("Random")) {
            P = Data.generateVector(N);

            MR = Data.generateMatrix(N);

            MS = Data.generateMatrix(N);
        }

        if (inputType.equals("Same")) {
            System.out.println("Enter values for all function 3 elements: ");
            double value = Data.inputValue();

            Arrays.fill(P, value);

            for (int i = 0; i < N; i++) {
                Arrays.fill(MR[i], value);

                Arrays.fill(MS[i], value);
            }
        }

        if (inputType.equals("File")) {
            Data.loadFile("P");
            P = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(P));

            Data.loadFile("MR");
            MR = Data.inputMatrixFromFile(N);
            for (double[] doubles : MR) {
                System.out.println(Arrays.toString(doubles));
            }

            Data.loadFile("MS");
            MS = Data.inputMatrixFromFile(N);
            for (double[] doubles : MS) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }
}
