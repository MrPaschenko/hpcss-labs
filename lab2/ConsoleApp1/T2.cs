namespace ConsoleApp1;

public class T2
{
    public static void Run(Data data)
    {
        Console.WriteLine("T2 is started");

        // Input B
        data.B = Enumerable.Repeat(1, data.N).ToArray();

        // Input MX
        data.MX = new int[data.N, data.N];
        for (var i = 0; i < data.N; i++)
        for (var j = 0; j < data.N; j++)
            data.MX[i, j] = 1;

        // Signal T1, T3, T4 about input B, MX
        data.Sem1.Release(3);

        // Wait for input d, Z, MM in T4
        data.Sem2.WaitOne();

        data.Mutex1.WaitOne();

        // Copy d2 = d
        var d2 = data.d;
        data.Mutex1.ReleaseMutex();

        // Calculation1 Ah = sort(d * Bh + Z * (MM * MXh))
        data.calculation1(d2, data.H, data.H * 2);

        // Signal T1 about end of calculation Ah
        data.Sem3.Release();

        // Calculation4 ai = min(Bh)
        var a2 = data.calculation4(data.H, data.H * 2);

        // Calculation5 a = min(a, ai)
        lock(data.aLock)
        {
            data.a = Math.Min(data.a, a2);
        }

        // Signal T1, T3, T4 about end of calculation a
        data.Event2.Set();

        // Wait for calculation a, A in T1
        data.Event1.WaitOne();

        // Wait for calculation a in T3
        data.Event3.WaitOne();

        // Wait for calculation a in T4
        data.Event4.WaitOne();

        data.Mutex2.WaitOne();
        // Copy a2 = a
        a2 = data.a;
        data.Mutex2.ReleaseMutex();

        // Calculation6 Xh = Ah * a
        data.calculation6(a2, data.H, data.H * 2);

        // Signal T4 about end of calculation Xh
        data.Barrier.SignalAndWait();

        Console.WriteLine("T2 is finished");
    }
}
