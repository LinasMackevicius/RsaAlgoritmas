package org.example;

import org.apache.commons.math3.primes.Primes;

import java.util.Scanner;

public class GetInput {
    public String returnMyString() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print(">\t");

        String myString = myObj.nextLine();  // Read user input

        return myString;
    }

    public int returnMyInt() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print(">\t");

        int myInt = Integer.parseInt(myObj.nextLine());  // Read user input

        if(Primes.isPrime(myInt)) {
            return myInt;
        }
        else
            return 1;
    }
}
