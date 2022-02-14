package org.example.lesson1;

import java.util.Scanner;

public class Fibonacci {

    public static int fibonacciRecursion(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1 || n == 2){
            return 1;
        }
        return fibonacciRecursion(n-2) + fibonacciRecursion(n-1);
    }
    public static void main(String[] args) {
        int maxNumber = new Scanner(System.in).nextInt();
        System.out.print("Fibonacci Series of "+maxNumber+" numbers: ");
        for(int i = 0; i < maxNumber; i++){
            System.out.print(fibonacciRecursion(i) +" ");
        }
    }
}
