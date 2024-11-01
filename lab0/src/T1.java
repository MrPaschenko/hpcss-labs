import java.util.Arrays;

// d = MAX((A + B + C) * (MA * ME))

public class T1 extends Thread {
    static int N;
    static double[] A;
    static double[] B;
    static double[] C;
    static double[][] MA;
    static double[][] ME;
    static double d;
    static String inputType;

    public void run() {
        // start
        System.out.println("T1 is started");

        // input
        first_input();

        // calculation
        double[] sum = new double[N];
        for (int i = 0; i < N; i++) {
            sum[i] = A[i] + B[i] + C[i];
        }

        double[][] mult = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int n = 0; n < N; n++) {
                    mult[i][j] += MA[i][n] * ME[n][j];
                }
            }
        }

        double[] mult2 = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mult2[i] += sum[j] * mult[i][j];
            }
        }

        Arrays.sort(mult2);
        d = mult2[N - 1];

        // output
        System.out.println("\nResult: " + d);

        // finish
        System.out.println("T1 is finished");
    }

    public static void first_input() {
        N = Data.inputN(1);
        inputType = Data.inputInputType(N);

        A = new double[N];
        B = new double[N];
        C = new double[N];
        MA = new double[N][N];
        ME = new double[N][N];

        if (inputType.equals("Keyboard")) {
            A = Data.inputVector(N, "A");
            System.out.println(Arrays.toString(A));

            B = Data.inputVector(N, "B");
            System.out.println(Arrays.toString(B));

            C = Data.inputVector(N, "C");
            System.out.println(Arrays.toString(C));

            MA = Data.inputMatrix(N, "MA");
            for (double[] doubles : MA) {
                System.out.println(Arrays.toString(doubles));
            }

            ME = Data.inputMatrix(N, "ME");
            for (double[] doubles : ME) {
                System.out.println(Arrays.toString(doubles));
            }
        }

        if (inputType.equals("Random")) {
            A = Data.generateVector(N);

            B = Data.generateVector(N);

            C = Data.generateVector(N);

            MA = Data.generateMatrix(N);

            ME = Data.generateMatrix(N);
        }

        if (inputType.equals("Same")) {
            System.out.print("Enter values for all function 1 elements: ");
            double value = Data.inputValue();

            Arrays.fill(A, value);

            Arrays.fill(B, value);

            Arrays.fill(C, value);

            for (int i = 0; i < N; i++) {
                Arrays.fill(MA[i], value);

                Arrays.fill(ME[i], value);
            }
        }

        if (inputType.equals("File")) {
            Data.loadFile("A");
            A = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(A));

            Data.loadFile("B");
            B = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(B));

            Data.loadFile("C");
            C = Data.inputVectorFromFile(N);
            System.out.println(Arrays.toString(C));

            Data.loadFile("MA");
            MA = Data.inputMatrixFromFile(N);
            for (double[] doubles : MA) {
                System.out.println(Arrays.toString(doubles));
            }

            Data.loadFile("ME");
            ME = Data.inputMatrixFromFile(N);
            for (double[] doubles : ME) {
                System.out.println(Arrays.toString(doubles));
            }
        }
    }
}
