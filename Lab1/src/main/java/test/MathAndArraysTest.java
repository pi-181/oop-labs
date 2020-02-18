package test;

import java.util.Arrays;
import java.util.Scanner;

public class MathAndArraysTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array length: ");
        int size = scanner.nextInt();
        scanner.close();

        double[] arr = new double[size];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Math.random() * 100;

        Arrays.sort(arr);

        System.out.println("Array: ");
        for (double i : arr)
            System.out.printf("%6.2f", i);

        System.out.println();
    }
}
