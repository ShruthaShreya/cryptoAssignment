import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Rsa{
    // Function to implement the Extended Euclidean Algorithm
    public static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        }
        BigInteger[] result = extendedEuclidean(b, a.mod(b));
        BigInteger gcd = result[0];
        BigInteger x = result[2];
        BigInteger y = result[1].subtract(a.divide(b).multiply(result[2]));
        return new BigInteger[]{gcd, x, y};
    }

    // Function to find modular inverse
    public static BigInteger modInverse(BigInteger e, BigInteger phi) {
        BigInteger[] result = extendedEuclidean(e, phi);
        if (!result[0].equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("No modular inverse exists for the given e and phi.");
        }
        return result[1].mod(phi); // Ensure positive result
    }

    // Function to encrypt a plaintext message
    public static BigInteger[] rsaEncrypt(String plaintext, BigInteger e, BigInteger n) {
        BigInteger[] ciphertext = new BigInteger[plaintext.length()];
        for (int i = 0; i < plaintext.length(); i++) {
            ciphertext[i] = BigInteger.valueOf(plaintext.charAt(i)).modPow(e, n);
        }
        return ciphertext;
    }

    // Function to decrypt a ciphertext
    public static String rsaDecrypt(BigInteger[] ciphertext, BigInteger d, BigInteger n) {
        StringBuilder decryptedText = new StringBuilder();
        for (BigInteger cipherChar : ciphertext) {
            decryptedText.append((char) cipherChar.modPow(d, n).intValue());
        }
        return decryptedText.toString();
    }

    public static void main(String[] args) {
        // Step 1: Select two prime numbers
        BigInteger p = BigInteger.valueOf(61);
        BigInteger q = BigInteger.valueOf(53);
        BigInteger n = p.multiply(q); // Compute n
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); // Compute φ(n)

        // Step 2: Choose public key 'e'
        BigInteger e = BigInteger.valueOf(17); // e must be coprime with phi
        if (!extendedEuclidean(e, phi)[0].equals(BigInteger.ONE)) {
            throw new IllegalArgumentException("'e' is not coprime with phi. Choose a different 'e'.");
        }

        // Step 3: Compute private key 'd'
        BigInteger d = modInverse(e, phi);

        // Display keys
        System.out.println("Public Key: (e=" + e + ", n=" + n + ")");
        System.out.println("Private Key: (d=" + d + ", n=" + n + ")");

        // Step 4: Encrypt and decrypt a word
        String plaintext = "Money make man looks beautiful";
        System.out.println("Plaintext: " + plaintext);

        // Encryption
        BigInteger[] ciphertext = rsaEncrypt(plaintext, e, n);
        System.out.print("Encrypted Text: ");
        for (BigInteger c : ciphertext) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Decryption
        String decryptedText = rsaDecrypt(ciphertext, d, n);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
