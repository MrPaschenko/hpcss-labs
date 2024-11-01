/*
 * Програмне забезпечення високопродуктивних компʼютерних систем
 * Лабораторна робота №3
 * Варіант 10
 *
 * Завдання MR = MB * (MC * MM) * d + min(Z) * MC
 * ПВВ1 – MB
 * ПВВ2 – MR
 * ПВВ3 – MC
 * ПВВ4 – Z, d, MM
 *
 * Пащенко Дмитро ІП-04
 * Дата 04.05.2023
 */

public class lab3 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        T1 T1 = new T1();
        T2 T2 = new T2();
        T3 T3 = new T3();
        T4 T4 = new T4();

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("That took " + duration + " milliseconds");
    }
}