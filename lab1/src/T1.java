import java.util.Arrays;

public class T1 extends Thread {
    private int x1 = 0;

    public void run() {
        try {
            System.out.println("T1 is started");

            //Input MC
            for (int i = 0; i < Data.N; i++) {
                Arrays.fill(Data.MC[i], 1);
            }

            //Input E
            Arrays.fill(Data.E, 1);

            //Signal T2, T3, T4 about input MC, E
            Data.Sem1.release(3);

            //Wait for input MD, d in T2
            Data.Sem2.acquire(1);

            //Wait for input B, p in T3
            Data.Sem3.acquire(1);

            //Wait for input R, Z in T4
            Data.Sem4.acquire(1);

            //Calculation1 Sh = R * MCh
            Data.calculation1(0, Data.H);

            //Calculation2 x1 = BH * ZH
            for (int i = 0; i < Data.H; i++) {
                x1 += Data.B[i] * Data.Z[i];
            }

            //Calculation3 x = x + x1
            Data.x.addAndGet(x1);

            //Signal T2, T3, T4 about end of calculation x
            Data.Sem5.release(3);

            //Wait for end of calculation x in T2
            Data.Sem6.acquire(1);

            //Wait for end of calculation x in T3
            Data.Sem7.acquire(1);

            //Wait for end of calculation x in T4
            Data.Sem8.acquire(1);

            //Copy p1 = p
            int p1;
            synchronized (Data.Y) {
                p1 = Data.p;
            }

            //Copy x1 = x
            Data.Sem0.acquire();
            x1 = Data.x.get();
            Data.Sem0.release();

            //Copy d1 = d
            int d1;
            synchronized (Data.W) {
                d1 = Data.d;
            }

            //Calculation4 Ah = S * MDh * p1 + x1 * Eh * d1
            Data.calculation4(p1, x1, d1, 0, Data.H);

            //Signal T3 about end of calculation Ah
            Data.Sem9.release(1);

            System.out.println("T1 is finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
