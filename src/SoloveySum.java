import parcs.*;
import java.lang.Math;
import java.util.Random;

public class SoloveySum implements AM {
    private static final int NODES = 4;

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1) { // If exponent is odd, multiply the base with result
                result = (result * base) % modulus;
            }
            exponent = exponent / 2; // Divide the exponent by 2
            base = (base * base) % modulus; // Square the base
        }
        return result;
    }

    // Calculate the Jacobian symbol (a/n)
    private static int jacobian(long a, long n) {
        int ans = 1;
        if (a < 0) {
            a = -a; // Make 'a' positive
            if (n % 4 == 3) {
                ans = -ans; // If 'n ≡ 3 (mod 4)', the result is negated
            }
        }
        while (a != 0) {
            while (a % 2 == 0) {
                a /= 2;
                if (n % 8 == 3 || n % 8 == 5) {
                    ans = -ans;
                }
            }
            // Swap 'a' and 'n'
            long temp = a;
            a = n;
            n = temp;

            if (a % 4 == 3 && n % 4 == 3) {
                ans = -ans;
            }
            a %= n;
        }
        if (n == 1) {
            return ans;
        } else {
            return 0;
        }
    }


    // Solovay-Strassen Primality Test
    public static boolean isPrime(long p, int iterations) {
        if (p < 2) return false; // 0 and 1 are not prime numbers
        if (p != 2 && p % 2 == 0) return false; // Even numbers are not prime

        Random rand = new Random();
        for (int i = 0; i < iterations; i++) {
            long a = rand.nextInt((int) Math.min(p - 1, Integer.MAX_VALUE)) + 1; // Generate 'a' where 1 <= a < p
            long jacobianValue = (p + jacobian(a, p)) % p;
            long mod = modularExponentiation(a, (p - 1) / 2, p);

            if (jacobianValue == 0 || mod != jacobianValue) {
                return false; // 'p' is not a prime
            }
        }
        return true; // 'p' is probably a prime
    }


    public void run(AMInfo info) {
        Node n = (Node) info.parent.readObject();
        System.out.println("[" + n.l + " " + n.r + "] Build started.");

        int totalNumbers = n.r - n.l + 1;
        int numbersPerNode = totalNumbers / NODES;
        int extra = totalNumbers % NODES;

        System.out.println("Div " + n.div + " " + "Extra " + extra);
        int start = n.l + n.div * numbersPerNode + Math.min(n.div, extra);
        int end = start + numbersPerNode - 1;
        if (n.div < extra) {
            end += 1;
        }

        long sum = 0L;
        for (int i = start; i <= end; i++) {
            long x = i; // Direct use of 'i', 'new Long(i)' is unnecessary
            if(isPrime(x, 10)) {
                System.out.println(x + " is Prime");
                sum += x;
            }
        }

        System.out.println("[" + n.l + " " + n.r + "] Build finished.");
        info.parent.write(sum);
    }
}