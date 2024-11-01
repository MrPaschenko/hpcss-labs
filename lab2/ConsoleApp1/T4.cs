namespace ConsoleApp1;

public class T4
{
    public static void Run(Data data)
    {
        Console.WriteLine("T4 is started");

        // Input d
        data.d = 1;

        // Input Z
        data.Z = Enumerable.Repeat(1, data.N).ToArray();

        // Input MM
        data.MM = new int[data.N, data.N];
        for (var i = 0; i < data.N; i++)
        for (var j = 0; j < data.N; j++)
            data.MM[i, j] = 1;

        // Signal T1, T2, T3 about input d, Z, MM 
        data.Sem2.Release(3);

        // Wait for input B, MX in T2
        data.Sem1.WaitOne();

        data.Mutex1.WaitOne();
        // Copy d4 = d
        var d4 = data.d;
        data.Mutex1.ReleaseMutex();

        // Calculation1 Ah = sort(d * Bh + Z * (MM * MXh))
        data.calculation1(d4, data.H * 3, data.H * 4);

        // Signal T3 about end of calculation Ah
        data.Sem5.Release();

        // Calculation4 ai = min(Bh)
        var a4 = data.calculation4(data.H * 3, data.H * 4);

        // Calculation5 a = min(a, ai)
        lock(data.aLock)
        {
            data.a = Math.Min(data.a, a4);
        }

        // Signal T1, T2, T3 about end of calculation a
        data.Event4.Set();

        // Wait for calculation a, A in T1
        data.Event1.WaitOne();

        // Wait for calculation a in T2
        data.Event2.WaitOne();

        // Wait for calculation a in T3
        data.Event3.WaitOne();

        data.Mutex2.WaitOne();
        // Copy a4 = a
        a4 = data.a;
        data.Mutex2.ReleaseMutex();

        // Calculation6 Xh = Ah * a
        data.calculation6(a4, data.H * 3, data.H * 4);

        // Wait for calculation Xh in T1, T2, T3
        data.Barrier.SignalAndWait();

        // Output X
        Console.WriteLine(string.Join(", ", data.X));

        Console.WriteLine("T4 is finished");
    }
}
