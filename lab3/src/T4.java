import java.util.Arrays;

public class T4 extends Thread{
    public void run() {
        System.out.println("T4 is started");

        // Input Z
        int[] z = new int[Data.N];
        Arrays.fill(z, 1);

        Data.resourceMonitor.set_Z(z);

        // Input d
        Data.resourceMonitor.set_d(1);

        // Input MM
        int[][] mm = new int[Data.N][Data.N];

        for (int i = 0; i < Data.N; i++) {
            Arrays.fill(mm[i], 1);
        }

        Data.resourceMonitor.set_MM(mm);

        // Signal T1, T2, T3 about input Z, d, MM
        Data.synchronisationMonitor.signalInput();

        // Wait for input MB in T1, MC in T3
        Data.synchronisationMonitor.waitSignalInput();

        // Calculation1 a4 = min(Zh)
        int a4 = Data.resourceMonitor.min_Z(3);

        // Calculation2 a = min(a, a4)
        Data.resourceMonitor.set_a(Data.resourceMonitor.calculation2(a4));

        // Signal T1, T2, T3 about end of calculation a
        Data.synchronisationMonitor.signalMin();

        // Wait for end of calculation a in T1, T2, T3
        Data.synchronisationMonitor.waitSignalMin();

        // Copy d4 = d
        int d4 = Data.resourceMonitor.copy_d();

        // Copy a4 = a
        a4 = Data.resourceMonitor.copy_a();

        // Calculation3 MRh = MB * (MC * MMh) * d4 + a5 * MC
        Data.resourceMonitor.calculation3(3, d4, a4);

        // Signal T2 about end of calculation MR
        Data.synchronisationMonitor.signalOutput();

        System.out.println("T4 is finished");
    }
}
