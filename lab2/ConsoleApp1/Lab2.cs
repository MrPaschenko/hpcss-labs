/*
 * Програмне забезпечення високопродуктивних компʼютерних систем
 * Лабораторна робота №2
 * Варіант 23
 *
 * Завдання X = sort(d * B + Z * (MM * MX)) * min(B)
 * ПВВ2 – B, MX
 * ПВВ4 – MM, X, Z, d
 *
 * Пащенко Дмитро ІП-04
 * Дата 25.04.2023
 */

namespace ConsoleApp1;

public class Lab2
{
    private static void Main(string[] args)
    {
        Thread.CurrentThread.Name = "Lab2";

        var data = new Data();

        var thread1 = new Thread(() => T1.Run(data)) { Name = "T1" };
        var thread2 = new Thread(() => T2.Run(data)) { Name = "T2" };
        var thread3 = new Thread(() => T3.Run(data)) { Name = "T3" };
        var thread4 = new Thread(() => T4.Run(data)) { Name = "T4" };

        var startTime = DateTime.Now;

        thread1.Start();
        thread2.Start();
        thread3.Start();
        thread4.Start();

        thread1.Join();
        thread2.Join();
        thread3.Join();
        thread4.Join();

        var endTime = DateTime.Now;

        Console.WriteLine("That took " + (endTime - startTime).TotalMilliseconds + " milliseconds");
    }
}
