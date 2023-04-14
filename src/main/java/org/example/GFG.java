package org.example;/*package whatever //do not write package name here */

import java.nio.ByteBuffer;

/*
 * Java program for RSA asymmetric cryptographic algorithm.
 * For demonstration, values are
 * relatively small compared to practical application
 */
public class GFG {
    public static double greatestCommonDivisor(double a, double h)
    {
        double temp; // temporary variable to store the remainder
        while (true) {
            temp = a % h; // calculate the remainder
            if (temp == 0)
                return h; // return the GCD when remainder is zero
            a = h;
            h = temp; // update a and h for the next iteration
        }
    }

    public static double extendedEuclidean(double a, double m) {
        double m0 = m;
        double y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1) {
            // q is quotient
            double q = a / m;

            double t = m;

            // m is remainder now, process same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update y and x
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }

    public static void main(String[] args)
    {
        GetInput inputGetter = new GetInput();

        System.out.print("First prime number\t");
        double p = inputGetter.returnMyInt(); // a prime number

        System.out.print("Second prime number\t");
        double q = inputGetter.returnMyInt(); // another prime number

        System.out.print("Message text\t");
        String msg = inputGetter.returnMyString(); // the message to be converted

        // Stores the first part of public key:
        double n = p * q;


        double e = 2; // public exponent
        double phi = (p - 1) * (q - 1); // Euler's totient function
        while (e < phi) {
            /*
             * e must be co-prime to phi and
             * smaller than phi.
             */
            if (greatestCommonDivisor(e, phi) == 1)
                break;
            else
                e++; // increment e until it satisfies the condition
        }

        double d = extendedEuclidean(e, phi); // private exponent

        byte[] bytes = msg.getBytes(); // get the ASCII byte array representation of the message

        double numMsg = ByteBuffer.wrap(bytes).getDouble(); // convert the byte array to a double

        System.out.println("Message data = " + msg);

        // Encryption c = (msg ^ e) % n
        double c = Math.pow(numMsg, e); // ciphertext
        c = c % n;
        System.out.println("Encrypted data = " + c);

        // Decryption m = (c ^ d) % n
        double m = Math.pow(c, d);
        m = m % n;

        byte[] bytesForDecryption = ByteBuffer.allocate(Double.BYTES).putDouble(c).array(); // convert the double to a byte array
        String msgForDecryption = new String(bytes); // convert the byte array to a string

        System.out.println("Original Message Sent = " + msgForDecryption);
    }
}

