import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Data {
    private static String[] textArray;

    public static int inputN(Integer n) {
        Scanner scanner = new Scanner(System.in);
        String value;

        System.out.print("Enter N, matrix and vectors size for function " + n + ": ");
        while (true) {
            value = scanner.next();
            if (isInt(value) && Integer.parseInt(value) > 0) break;
            else System.out.print("Incorrect value! Try one more time: ");
        }

        return Integer.parseInt(value);
    }

    public static String inputInputType(int N) {
        Scanner scanner = new Scanner(System.in);
        String value;

        if (N <= 4) {
            return "Keyboard";
        }

        System.out.print("N = " + N + " > 4. Choose input method: \n" +
                "'random' - generate random values;\n" +
                "'file' - input from file;\n" +
                "'same' - set all elements the same value.\n" +
                "Input method: ");

        while (true) {
            value = scanner.next();
            switch (value) {
                case "random" -> {
                    return "Random";
                }
                case "file" -> {
                    return "File";
                }
                case "same" -> {
                    return "Same";
                }
                default -> System.out.print("Incorrect value! Try one more time: ");
            }
        }
    }

    public static double inputValue() {
        Scanner scanner = new Scanner(System.in);
        String value;

        while (true) {
            value = scanner.next();
            if (isDouble(value)) break;
            else System.out.print("Incorrect value! Try one more time: ");
        }

        return Double.parseDouble(value);
    }

    private static boolean isInt(String value) {
        try {
            Integer.parseInt(value);

            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);

            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static double[] inputVector(int N, String name) {
        double[] A = new double[N];

        for (int i = 0; i < N; i++) {
            System.out.print("Enter value " + name + "[" + i + "]: ");
            A[i] = inputValue();
        }

        return A;
    }

    public static double[][] inputMatrix(int N, String name) {
        double[][] MA = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print("Enter value " + name + "[" + i + "][" + j + "]: ");
                MA[i][j] = inputValue();
            }
        }

        return MA;
    }

    public static double[] generateVector(int N) {
        double[] A = new double[N];

        for (int i = 0; i < N; i++) {
            A[i] = Math.random() * 20 - 10;
        }

        return A;
    }

    public static double[][] generateMatrix(int N) {
        double[][] MA = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MA[i][j] = Math.random() * 20 - 10;
            }
        }

        return MA;
    }

    public static void loadFile(String name) {
        Scanner scanner = new Scanner(System.in);
        String value;

        System.out.print("Enter file path with data for " + name + ": ");

        while (true) {
            value = scanner.next();
            Path path = Path.of(value);
            if (Files.exists(path)) {
                try {
                    String text;
                    text = new String(Files.readAllBytes(path));
                    text = text.replaceAll("\\s", "");
                    textArray = text.split(";");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            } else System.out.print("Incorrect path! Try one more time: ");
        }
    }

    public static double[] inputVectorFromFile(int N) {
        double[] A = new double[N];
        for (int i = 0; i < N; i++) {
            if (textArray.length > i && isDouble(textArray[i])) {
                A[i] = Double.parseDouble(textArray[i]);
            } else {
                A[i] = 0;
            }
        }
        return A;
    }

    public static double[][] inputMatrixFromFile(int N) {
        double[][] MA = new double[N][N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (textArray.length > index && isDouble(textArray[index])) {
                    MA[i][j] = Double.parseDouble(textArray[index]);
                } else {
                    MA[i][j] = 0;
                }
                index++;
            }
        }
        return MA;
    }
}
