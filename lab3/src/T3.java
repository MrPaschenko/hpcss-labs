import java.util.Arrays;

public class T3 extends Thread{
    public void run() {
        System.out.println("T3 is started");

        // Input MC
        int[][] mc = new int[Data.N][Data.N];

        for (int i = 0; i < Data.N; i++) {
            Arrays.fill(mc[i], 1);
        }

        Data.resourceMonitor.set_MC(mc);

        // Signal T1, T2, T4 about input MC
        Data.synchronisationMonitor.signalInput();

        // Wait for input MB in T1, Z, d, MM in T4
        Data.synchronisationMonitor.waitSignalInput();

        // Calculation1 a3 = min(Zh)
        int a3 = Data.resourceMonitor.min_Z(2);

        // Calculation2 a = min(a, a3)
        Data.resourceMonitor.set_a(Data.resourceMonitor.calculation2(a3));

        // Signal T1, T2, T4 about end of calculation a
        Data.synchronisationMonitor.signalMin();

        // Wait for end of calculation a in T1, T2, T4
        Data.synchronisationMonitor.waitSignalMin();

        // Copy d3 = d
        int d3 = Data.resourceMonitor.copy_d();

        // Copy a3 = a
        a3 = Data.resourceMonitor.copy_a();

        // Calculation3 MRh = MB * (MC * MMh) * d3 + a4 * MC
        Data.resourceMonitor.calculation3(2, d3, a3);

        // Signal T2 about end of calculation MR
        Data.synchronisationMonitor.signalOutput();

        System.out.println("T3 is finished");
    }
}
