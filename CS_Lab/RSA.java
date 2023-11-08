package Cyber_Assignments;

import java.math.*;
import java.util.*;

class RSA {

    public static void main(String args[])
    {
        int p, q, n, phi, e, d = 0, i,plain_text;

        Scanner sc= new Scanner(System.in);
        System.out.print("Enter P : ");
        p=sc.nextInt();

        System.out.print("Enter q: ");
        q=sc.nextInt();

        System.out.print("Enter Plaintext: ");
        plain_text=sc.nextInt();


        n = p * q;

        phi = (p - 1) * (q - 1);


        for (e = 2; e < phi; e++) {

            if (gcd(e, phi) == 1) {
                break;
            }
        }
        System.out.println("The value of e = " + e);


        for (i = 0; ; i++) {
            int x = 1 + (i * phi);
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }
        System.out.println("The value of d = " + d);



        System.out.println("The message sent is : " + plain_text);
        double cipher_text;
        BigInteger msg;


        cipher_text = (Math.pow(plain_text, e)) % n;
        System.out.println("Encrypted message is : " + cipher_text);


        BigInteger N = BigInteger.valueOf(n);

        BigInteger C = BigDecimal.valueOf(cipher_text).toBigInteger();

        msg = (C.pow(d)).mod(N);
        System.out.println("Decrypted message is : " + msg);
    }

    static int gcd(int e, int phi) {
        if (e == 0)
            return phi;
        else
            return gcd(phi % e, e);
    }
}
