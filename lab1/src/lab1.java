/*
 * Програмне забезпечення високопродуктивних компʼютерних систем
 * Лабораторна робота №1
 * Варіант 6
 *
 * Завдання A = (R * MC) * MD * p + (B * Z) * E * d
 * ПВВ1 – MC, E
 * ПВВ2 – MD, d
 * ПВВ3 – A, B, p
 * ПВВ4 – R, Z
 *
 * Пащенко Дмитро ІП-04
 * Дата 25.03.2023
 */

public class lab1 {
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