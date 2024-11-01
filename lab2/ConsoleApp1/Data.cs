namespace ConsoleApp1;

public class Data
{
    public int N = 2000;
    public int P = 4;
    public int H;

    public int[] B;
    public int[,] MX;

    public int d;
    public int[] Z;
    public int[,] MM;

    public int a;
    public int[] A;
    
    public Mutex Mutex1 = new();
    public Mutex Mutex2 = new();
    public Semaphore Sem1 = new(0, 3);
    public Semaphore Sem2 = new(0, 3);
    public Semaphore Sem3 = new(0, 1);
    public Semaphore Sem4 = new(0, 1);
    public Semaphore Sem5 = new(0, 1);
    public EventWaitHandle Event1 = new(false, EventResetMode.ManualReset);
    public EventWaitHandle Event2 = new(false, EventResetMode.ManualReset);
    public EventWaitHandle Event3 = new(false, EventResetMode.ManualReset);
    public EventWaitHandle Event4 = new(false, EventResetMode.ManualReset);
    public Barrier Barrier = new(4);
    public readonly object aLock = new();

    // Result
    public int[] X; 

    public Data()
    {
        H = N / P;
        a = 1;
        A = new int[N];
        X = new int[N];
    }

    // Calculation1 Ah = sort(d * Bh + Z * (MM * MXh))
    // 1) d * Bh
    // 2) Z * (MM * MXh)
    // 3) d * Bh + Z * (MM * MXh)
    // 4) sort(d * Bh + Z * (MM * MXh))
    public void calculation1(int d, int start, int end)
    {
        var result = new int[end - start];
        
        var index = 0;
        for (var i = start; i < end; i++)
        {
            // d * Bh
            result[index] = B[i] * d; 
            index++;
        }

        // Z * (MM * MXh)
        var tempArray = new int[end - start]; 
        index = 0;
        for (var i = start; i < end; i++)
        {
            var m = 0;
            for (var j = 0; j < N; j++)
            {
                var s = 0;
                for (var k = 0; k < N; k++) s += MX[i, k] * MM[k, j];
                m += s * Z[j];
            }

            tempArray[index] = m;
            index++;
        }

        // d * Bh + Z * (MM * MXh)
        for (var i = 0; i < result.Length; i++)
        {
            result[i] += tempArray[i];
        }
        
        // sort(d * Bh + Z * (MM * MXh))
        Array.Sort(result); 
        index = 0;
        for (var i = start; i < end; i++)
        {
            A[i] = result[index];
            index++;
        }
    }

    // Calculation2 A2h = sort(Ah, Ah)
    public void calculation2(int start, int end)
    {
        var array = new int[end - start];
        
        Array.ConstrainedCopy(A, start, array, 0, end - start);
        Array.Sort(array);
        var index = 0;
        for (var i = start; i < end; i++)
        {
            A[i] = array[index];
            index++;
        }
    }

    // Calculation4 ai = min(Bh)
    public int calculation4(int start, int end)
    {
        var min = B[start];
        
        for (var i = start + 1; i < end; i++)
        {
            if (B[i] < min)
            {
                min = B[i];
            }
        }
        
        return min;
    }

    // Calculation6 Xh = Ah * a
    public void calculation6(int a, int start, int end)
    {
        for (var i = start; i < end; i++)
        {
            X[i] = A[i] * a;
        }
    }
}
