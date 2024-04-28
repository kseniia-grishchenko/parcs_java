import parcs.*;
import java.util.Random;

public class SoloveySum implements AM {
    private static final int NODES = 4;

    private static long modulo(long base, long exponent, long mod) {
        long result = 1;
        base = base % mod;
        while (exponent > 0) {
            if ((exponent % 2) == 1)
                result = (result * base) % mod;
            exponent = exponent / 2; // Replacing bit-shifting with explicit division
            base = (base * base) % mod;
        }
        return result;
    }

    private static long calculateJacobian(long a, long n) {
        if (n <= 0 || n % 2 == 0) return 0;
        long ans = 1;
        if (a < 0) {
            a = -a;
            if (n % 4 == 3) ans = -ans;
        }
        while (a != 0) {
            while (a % 2 == 0) {
                a = a / 2; // Similarly replacing all >>= operations
                if (n % 8 == 3 || n % 8 == 5) ans = -ans;
            }
            long temp = a;
            a = n;
            n = temp;
            if (a % 4 == 3 && n % 4 == 3) ans = -ans;
            a %= n;
            if (a > n / 2) a -= n;
        }
        return (n == 1) ? ans : 0;
    }

    private static boolean soloveyStrassen(long p, int iteration) {
        if (p < 2) return false;
        if (p != 2 && p % 2 == 0) return false;
        Random rand = new Random();
        for (int i = 0; i < iteration; i++) {
            long a = Math.abs(rand.nextLong()) % (p - 1) + 1;
            long jacobian = (p + calculateJacobian(a, p)) % p;
            long mod = modulo(a, (p - 1) / 2, p);
            if (jacobian == 0 || mod != jacobian) return false;
        }
        return true;
    }

    public void run(AMInfo info) {
        Node n = (Node) info.parent.readObject();
        System.out.println("[" + n.l + " " + n.r + "] Build started.");
        long sum = 0L;
        int start = n.l;
        while (start % NODES != n.div) start++;
        for (int i = start; i <= n.r; i += NODES) {
            if (soloveyStrassen(i, 10)) sum += i;
        }
        System.out.println("[" + n.l + " " + n.r + "] Build finished.");
        info.parent.write(sum);
    }
}
