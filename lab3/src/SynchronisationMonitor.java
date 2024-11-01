public class SynchronisationMonitor {
    private int F1 = 0;
    private int F2 = 0;
    private int F3 = 0;

    public synchronized void signalInput() {
        F1++;

        if (F1 >= 3) {
            notifyAll();
        }
    }

    public synchronized void waitSignalInput() {
        try {
            if (F1 < 3) {
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void signalMin() {
        F2++;

        if (F2 >= 4) {
            notifyAll();
        }
    }

    public synchronized void waitSignalMin() {
        try {
            if (F2 < 4) {
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void signalOutput() {
        F3++;

        if (F3 >= 3) {
            notifyAll();
        }
    }

    public synchronized void waitSignalOutput() {
        try {
            if (F3 < 3) {
                wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
