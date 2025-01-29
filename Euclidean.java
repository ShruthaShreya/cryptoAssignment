public class Euclidean {
    // Function to implement the Extended Euclidean Algorithm
    public static long[] extendedEuclidean(long a, long b) {
        if (b == 0) {
            // Base case: gcd is a, and coefficients x=1, y=0
            return new long[]{a, 1, 0};
        } else {
            // Recursive case
            long[] result = extendedEuclidean(b, a % b);
            long gcd = result[0];
            long x1 = result[1];
            long y1 = result[2];

            // Update x and y using results of recursion
            long x = y1;
            long y = x1 - (a / b) * y1;

            return new long[]{gcd, x, y};
        }
    }

    public static void main(String[] args) {
        long a = 30;
        long b = 20;

        long[] result = extendedEuclidean(a, b);
        long gcd = result[0], x = result[1], y = result[2];

        System.out.println("GCD of " + a + " and " + b + " is " + gcd);
        System.out.println("Coefficients x and y are: x = " + x + ", y = " + y);
        System.out.println("Verification: " + a + "*" + x + " + " + b + "*" + y + " = " + (a * x + b * y));
    }
}
