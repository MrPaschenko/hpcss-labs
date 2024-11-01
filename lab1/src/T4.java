import java.util.Arrays;

public class T4 extends Thread {
    private int x4 = 0;

    public void run() {
        try {
            System.out.println("T4 is started");

            //Input R
            Arrays.fill(Data.R, 1);

            //Input Z
            Arrays.fill(Data.Z, 1);

            //Signal T1, T2, T3 about input MC, E
            Data.Sem4.release(3);

            //Wait for input MC, E in T1
            Data.Sem1.acquire(1);

            //Wait for input MD, d in T2
            Data.Sem2.acquire(1);

            //Wait for input B, p in T3
            Data.Sem3.acquire(1);

            //Calculation1 Sh = R * MCh
            Data.calculation1(Data.H * 3, Data.H * 4);

            //Calculation2 x4 = BH * ZH
            for (int i = 0; i < Data.H; i++) {
                x4 += Data.B[i] * Data.Z[i];
            }

            //Calculation3 x = x + x4
            Data.x.addAndGet(x4);

            //Signal T1, T2, T3 about end of calculation x
            Data.Sem8.release(3);

            //Wait for end of calculation x in T1
            Data.Sem5.acquire(1);

            //Wait for end of calculation x in T2
            Data.Sem6.acquire(1);

            //Wait for end of calculation x in T3
            Data.Sem7.acquire(1);

            //Copy p4 = p
            int p4;
            synchronized (Data.Y) {
                p4 = Data.p;
            }

            //Copy x4 = x
            Data.Sem0.acquire();
            x4 = Data.x.get();
            Data.Sem0.release();

            //Copy d4 = d
            int d4;
            synchronized (Data.W) {
                d4 = Data.d;
            }

            //Calculation4 Ah = S * MDh * p4 + x4 * Eh * d4
            Data.calculation4(p4, x4, d4, 0, Data.H);

            //Signal T3 about end of calculation Ah
            Data.Sem11.release();

            System.out.println("T4 is finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
