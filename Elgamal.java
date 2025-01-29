import java.util.Scanner;

public class Elgamal {
    // Function to perform modular exponentiation (base^exp % mod)
    public static long modularExponentiation(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if (exp % 2 == 1) { // If exp is odd, multiply base with result
                result = (result * base) % mod;
            }
            exp = exp >> 1; // Divide exp by 2
            base = (base * base) % mod;
        }
        return result;
    }

    // Function to encrypt a message
    public static long[] elgamalEncrypt(long p, long e1, long e2, long r, long m) {
        long c1 = modularExponentiation(e1, r, p);
        long c2 = (m * modularExponentiation(e2, r, p)) % p;
        return new long[]{c1, c2};
    }

    // Function to decrypt a ciphertext
    public static long elgamalDecrypt(long p, long c1, long c2, long d) {
        // Compute the shared secret: c1^(p-1-d) mod p
        long sharedSecret = modularExponentiation(c1, p - 1 - d, p);
        // Decrypt the message: m = (c2 * sharedSecret) mod p
        return (c2 * sharedSecret) % p;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Key generation
        System.out.print("Enter a prime number (p): ");
        long p = sc.nextLong();

        System.out.print("Enter e1 (primitive root modulo p): ");
        long e1 = sc.nextLong();

        System.out.print("Enter d (private key): ");
        long d = sc.nextLong();

        long e2 = modularExponentiation(e1, d, p); // Public key: e2 = e1^d mod p

        // Display the keys
        System.out.println("\nPublic Key: (e1=" + e1 + ", e2=" + e2 + ", p=" + p + ")");
        System.out.println("Private Key: d=" + d);

        // Encryption
        System.out.print("\nEnter the message to encrypt (m): ");
        long plaintext = sc.nextLong();

        System.out.print("Enter r (random number): ");
        long r = sc.nextLong();

        long[] cipher = elgamalEncrypt(p, e1, e2, r, plaintext);
        System.out.println("\nEncrypted: c1=" + cipher[0] + ", c2=" + cipher[1]);

        // Decryption
        long decryptedMessage = elgamalDecrypt(p, cipher[0], cipher[1], d);
        System.out.println("Decrypted Message: " + decryptedMessage);

        sc.close();
    }
}
