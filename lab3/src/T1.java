import java.util.Arrays;

public class T1 extends Thread{
    public void run() {
        System.out.println("T1 is started");

        // Input MB
        int[][] mb = new int[Data.N][Data.N];

        for (int i = 0; i < Data.N; i++) {
            Arrays.fill(mb[i], 1);
        }

        Data.resourceMonitor.set_MB(mb);

        // Signal T2, T3, T4 about input MB
        Data.synchronisationMonitor.signalInput();

        // Wait for input MC in T3, Z, d, MM in T4
        Data.synchronisationMonitor.waitSignalInput();

        // Calculation1 a1 = min(Zh)
        int a1 = Data.resourceMonitor.min_Z(0);

        // Calculation2 a = min(a, a1)
        Data.resourceMonitor.set_a(Data.resourceMonitor.calculation2(a1));

        // Signal T2, T3, T4 about end of calculation a
        Data.synchronisationMonitor.signalMin();

        // Wait for end of calculation a in T2, T3, T4
        Data.synchronisationMonitor.waitSignalMin();

        // Copy d1 = d
        int d1 = Data.resourceMonitor.copy_d();

        // Copy a1 = a
        a1 = Data.resourceMonitor.copy_a();

        // Calculation3 MRh = MB * (MC * MMh) * d1 + a1 * MC
        Data.resourceMonitor.calculation3(0, d1, a1);

        // Signal T2 about end of calculation MR
        Data.synchronisationMonitor.signalOutput();

        System.out.println("T1 is finished");
    }
}
