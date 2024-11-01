/* 1.15      2.24     3.4
 * F1: d = MAX((A + B + C) * (MA * ME))     (скаляр)
 * F2: MG = SORT(MF - MH * MK)              (матриця)
 * F3: O = SORT(P) * SORT(MR * MS)          (вектор)
 */

public class lab0 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        T2 t2 = new T2();
        T3 t3 = new T3();

        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}