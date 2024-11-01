import java.util.Arrays;

public class T3 extends Thread {
    private int x3 = 0;

    public void run() {
        try {
            System.out.println("T3 is started");

            //Input B
            Arrays.fill(Data.B, 1);

            //Input p
            Data.p = 1;

            //Signal T1, T2, T4 about input B, p
            Data.Sem3.release(3);

            //Wait for input MC, E in T1
            Data.Sem1.acquire(1);

            //Wait for input MD, d in T2
            Data.Sem2.acquire(1);

            //Wait for input R, Z in T4
            Data.Sem4.acquire(1);

            //Calculation1 Sh = R * MCh
            Data.calculation1(Data.H * 2, Data.H * 3);

            //Calculation2 x3 = BH * ZH
            for (int i = 0; i < Data.H; i++) {
                x3 += Data.B[i] * Data.Z[i];
            }

            //Calculation3 x = x + x3
            Data.x.addAndGet(x3);

            //Signal T1, T2, T4 about end of calculation x
            Data.Sem7.release(3);

            //Wait for end of calculation x in T1
            Data.Sem5.acquire(1);

            //Wait for end of calculation x in T2
            Data.Sem6.acquire(1);

            //Wait for end of calculation x in T4
            Data.Sem8.acquire(1);

            //Copy p3 = p
            int p3;
            synchronized (Data.Y) {
                p3 = Data.p;
            }

            //Copy x3 = x
            Data.Sem0.acquire();
            x3 = Data.x.get();
            Data.Sem0.release();

            //Copy d3 = d
            int d3;
            synchronized (Data.W) {
                d3 = Data.d;
            }

            //Calculation4 Ah = S * MDh * p3 + x3 * Eh * d3
            Data.calculation4(p3, x3, d3, 0, Data.H);

            //Wait for end of calculation Ah in T1
            Data.Sem9.acquire(1);

            //Wait for end of calculation Ah in T2
            Data.Sem10.acquire(1);

            //Wait for end of calculation Ah in T4
            Data.Sem11.acquire(1);

            //Output Ah
            System.out.println(Arrays.toString(Data.A));

            System.out.println("T3 is finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
