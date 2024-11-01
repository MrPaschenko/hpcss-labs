public class ResourceMonitor {
    private int[][] MB;
    private int[][] MC;
    private int[] Z;
    private int d;
    private static int[][] MM;
    private int a;
    private final int[][] MR = new int[Data.N][Data.N];

    public synchronized void set_MB(int[][] matrix) {
        MB = matrix;
    }

    public synchronized void set_MC(int[][] matrix) {
        MC = matrix;
    }

    public synchronized void set_Z(int[] vector) {
        Z = vector;
    }

    public synchronized void set_d(int value) {
        d = value;
    }

    public synchronized void set_MM(int[][] matrix) {
        MM = matrix;
    }

    public synchronized void set_a(int value) {
        a = value;
    }

    public synchronized int copy_d() {
        return d;
    }

    public synchronized int copy_a() {
        return a;
    }

    public synchronized int[][] get_MR() {
        return MR;
    }

    public int min_Z(int startId) {
        int min = Z[startId * Data.H];

        for (int i = startId * Data.H + 1; i < (startId + 1) * Data.H; i++) {
            if (Z[i] < min) {
                min = Z[i];
            }
        }

        return min;
    }

    public int calculation2(int ai) {
        return Math.min(ai, a);
    }

    // Calculation3 MRh = MB * (MC * MMh) * d + a * MC
    public void calculation3(int startId, int di, int ai) {

        // MC * MMh
        int[][] result1 = Data.multiplyMatrix(Data.getSubMatrix(MM, startId), MC);

        // MB * (MC * MMh)
        int[][] result2 = Data.multiplyMatrix(result1, MB);

        // (MB * (MC * MMh)) * d
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < Data.N; j++) {
                result2[i][j] *= di;
            }
        }

        // a * MC
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                MC[i][j] *= ai;
            }
        }

        // MRh = (MB * (MC * MMh)) * d + a * MC
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < Data.N; j++) {
                MR[startId * Data.H + i][j] = result2[i][j] + MC[i][j];
            }
        }
    }
}
