import java.util.Arrays;

public class T2 extends Thread {
    private int x2 = 0;

    public void run() {
        try {
            System.out.println("T2 is started");

            //Input MD
            for (int i = 0; i < Data.N; i++) {
                Arrays.fill(Data.MD[i], 1);
            }

            //Input d
            Data.d = 1;

            //Signal T1, T3, T4 about input MD, d
            Data.Sem2.release(3);

            //Wait for input MC, E in T1
            Data.Sem1.acquire(1);

            //Wait for input B, p in T3
            Data.Sem3.acquire(1);

            //Wait for input R, Z in T4
            Data.Sem4.acquire(1);

            //Calculation1 Sh = R * MCh
            Data.calculation1(Data.H, Data.H * 2);

            //Calculation2 x2 = BH * ZH
            for (int i = 0; i < Data.H; i++) {
                x2 += Data.B[i] * Data.Z[i];
            }

            //Calculation3 x = x + x2
            Data.x.addAndGet(x2);

            //Signal T1, T3, T4 about end of calculation x
            Data.Sem6.release(3);

            //Wait for end of calculation x in T1
            Data.Sem5.acquire(1);

            //Wait for end of calculation x in T3
            Data.Sem7.acquire(1);

            //Wait for end of calculation x in T4
            Data.Sem8.acquire(1);

            //Copy p2 = p
            int p2;
            synchronized (Data.Y) {
                p2 = Data.p;
            }

            //Copy x2 = x
            Data.Sem0.acquire();
            x2 = Data.x.get();
            Data.Sem0.release();

            //Copy d2 = d
            int d2;
            synchronized (Data.W) {
                d2 = Data.d;
            }

            //Calculation4 Ah = S * MDh * p2 + x2 * Eh * d2
            Data.calculation4(p2, x2, d2, 0, Data.H);

            //Signal T3 about end of calculation Ah
            Data.Sem10.release(1);

            System.out.println("T2 is finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
