import java.util.Arrays;

public class T2 extends Thread{
    public void run() {
        System.out.println("T2 is started");

        // Wait for input MB in T1, MC in T3, Z, d, MM in T4
        Data.synchronisationMonitor.waitSignalInput();

        // Calculation1 a2 = min(Zh)
        int a2 = Data.resourceMonitor.min_Z(0);

        // Calculation2 a = min(a, a2)
        Data.resourceMonitor.set_a(Data.resourceMonitor.calculation2(a2));

        // Signal T1, T3, T4 about end of calculation a
        Data.synchronisationMonitor.signalMin();

        // Wait for end of calculation a in T1, T3, T4
        Data.synchronisationMonitor.waitSignalMin();

        // Copy d2 = d
        int d2 = Data.resourceMonitor.copy_d();

        // Copy a2 = a
        a2 = Data.resourceMonitor.copy_a();

        // Calculation3 MRh = MB * (MC * MMh) * d2 + a3 * MC
        Data.resourceMonitor.calculation3(1, d2, a2);

        // Wait for end of calculation MR in T1, T3, T4
        Data.synchronisationMonitor.waitSignalOutput();

        // Output MR
        System.out.println(Arrays.deepToString(Data.resourceMonitor.get_MR()));

        System.out.println("T2 is finished");
    }
}
