namespace ConsoleApp1;

public class T3
{
    public static void Run(Data data)
    {
        Console.WriteLine("T3 is started");

        // Wait for input B, MX in T2
        data.Sem1.WaitOne();

        // Wait for input d, Z, MM in T4
        data.Sem2.WaitOne();

        data.Mutex1.WaitOne();
        // Copy d3 = d
        var d3 = data.d;
        data.Mutex1.ReleaseMutex();

        // Calculation1 Ah = sort(d * Bh + Z * (MM * MXh))
        data.calculation1(d3, data.H * 2, data.H * 3);

        // Wait for calculation Ah in T4
        data.Sem5.WaitOne();

        // Calculation2 A2h = sort(Ah, Ah)
        data.calculation2(data.H * 2, data.H * 4);

        // Signal T1 about end of calculation A2h
        data.Sem4.Release();

        // Calculation4 ai = min(Bh)
        var a3 = data.calculation4(data.H * 2, data.H * 3);

        // Calculation5 a = min(a, ai)
        lock(data.aLock)
        {
            data.a = Math.Min(data.a, a3);
        }

        // Signal T1, T2, T4 about end of calculation a
        data.Event3.Set();

        // Wait for calculation a, A in T1
        data.Event1.WaitOne();

        // Wait for calculation a in T2
        data.Event2.WaitOne();

        // Wait for calculation a in T4
        data.Event4.WaitOne();

        data.Mutex2.WaitOne();
        // Copy a3 = a
        a3 = data.a;
        data.Mutex2.ReleaseMutex();

        // Calculation6 Xh = Ah * a
        data.calculation6(a3, data.H * 2, data.H * 3);

        // Signal T4 about end of calculation Xh
        data.Barrier.SignalAndWait();

        Console.WriteLine("T3 is finished");
    }
}
