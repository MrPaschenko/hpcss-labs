import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {
    public static final int N = 2000;
    public static final int P = 4;
    public static final int H = N / P;

    public static int[][] MC = new int[N][N];
    public static int[] E = new int[N];

    public static int[][] MD = new int[N][N];

    public static int[] A = new int[N];
    public static int[] B = new int[N];

    public static int[] R = new int[N];
    public static int[] Z = new int[N];

    public static int[] S = new int[N];
    public static int p = 0;
    public static AtomicInteger x = new AtomicInteger();
    public static int d = 0;

    public static final Object W = new Object();
    public static final Object Y = new Object();

    public static Semaphore Sem0 = new Semaphore(1, true);
    public static Semaphore Sem1 = new Semaphore(0, true);
    public static Semaphore Sem2 = new Semaphore(0, true);
    public static Semaphore Sem3 = new Semaphore(0, true);
    public static Semaphore Sem4 = new Semaphore(0, true);
    public static Semaphore Sem5 = new Semaphore(0, true);
    public static Semaphore Sem6 = new Semaphore(0, true);
    public static Semaphore Sem7 = new Semaphore(0, true);
    public static Semaphore Sem8 = new Semaphore(0, true);
    public static Semaphore Sem9 = new Semaphore(0, true);
    public static Semaphore Sem10 = new Semaphore(0, true);
    public static Semaphore Sem11 = new Semaphore(0, true);

    static int[] multiplyVectorBySubMatrix(int[] vec, int[][] ma, int start, int end) {
        int[] res = new int[N];
        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                res[i] += vec[j] * ma[j][i];
            }
        }
        return res;
    }

    //Calculation1 Sh = R * MCh
    static void calculation1(int start, int end){
        int[] result = new int[N];

        for (int i = start; i < end; i++) {
            for (int j = 0; j < N; j++) {
                result[i] += R[j] * MC[j][i];
            }
        }

        System.arraycopy(result, start, S, start, H);
    }

    //Calculation4 Ah = S * MDh * pi + xi * Eh * di
    public static void calculation4(int pi, int xi, int di, int start, int end) {
        System.arraycopy(
                addSubVectors(
                        multiplySubVectorByScalar(multiplyVectorBySubMatrix(S, MD, start, end), pi, start, end),
                        multiplySubVectorByScalar(E, xi * di, start, end),
                        start,
                        end),
                start, A, start, end - start);
    }

    static int[] addSubVectors(int[] X, int[] Y, int start, int end) {
        int[] result = new int[X.length];

        for (int i = start; i < end; i++) {
            result[i] = X[i] + Y[i];
        }

        return result;
    }

    static int[] multiplySubVectorByScalar(int[] X, int x, int start, int end) {
        int[] result = new int[X.length];

        for (int i = start; i < end; i++) {
            result[i] = X[i] * x;
        }

        return result;
    }
}
