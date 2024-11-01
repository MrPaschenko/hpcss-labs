namespace ConsoleApp1;

public class T1
{
    public static void Run(Data data)
    {
        Console.WriteLine("T1 is started");

        // Wait for input B, MX in T2
        data.Sem1.WaitOne();

        // Wait for input d, Z, MM in T4
        data.Sem2.WaitOne();

        data.Mutex1.WaitOne();
        // Copy d1 = d
        var d1 = data.d;
        data.Mutex1.ReleaseMutex();

        // Calculation1 Ah = sort(d * Bh + Z * (MM * MXh))
        data.calculation1(d1, 0, data.H);

        // Wait for calculation Ah in T2
        data.Sem3.WaitOne();

        // Calculation2 A2h = sort(Ah, Ah)
        data.calculation2(0, data.H * 2);

        // Wait for calculation A2h in T3
        data.Sem4.WaitOne();

        // Calculation3 A = sort(A2h, A2h)
        Array.Sort(data.A);

        // Calculation4 ai = min(Bh)
        var a1 = data.calculation4(0, data.H);

        // Calculation5 a = min(a, ai)
        lock(data.aLock)
        {
            data.a = Math.Min(data.a, a1);
        }

        // Signal T2, T3, T4 about end of calculation a, A
        data.Event1.Set();

        // Wait for calculation a in T2
        data.Event2.WaitOne();

        // Wait for calculation a in T3
        data.Event3.WaitOne();

        // Wait for calculation a in T4
        data.Event4.WaitOne();

        data.Mutex2.WaitOne();
        // Copy a1 = a
        a1 = data.a;
        data.Mutex2.ReleaseMutex();

        // Calculation6 Xh = Ah * a
        data.calculation6(a1, 0, data.H);

        // Signal T4 about end of calculation Xh
        data.Barrier.SignalAndWait();

        Console.WriteLine("T1 is finished");
    }
}
