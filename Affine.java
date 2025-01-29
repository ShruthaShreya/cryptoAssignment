

import java.util.Scanner;

public class Affine {

    // Function to compute GCD
    static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to encrypt the message
    static String encryptMessage(char[] msg, int a, int b) {
        String cipher = "";
        
        // Normalize b to be within the range [0, 25]
        b = ((b % 26) + 26) % 26;  // Ensure that b is always in the range of [0, 25]
        
        for (int i = 0; i < msg.length; i++) {
            if (msg[i] != ' ') {
                int charPos = (msg[i] - 'a'); // 'a' for lowercase letters
                if (charPos < 0) {
                    charPos = (msg[i] - 'A'); // For uppercase letters
                }
                cipher += (char) ((((a * charPos + b) % 26) + 26) % 26 + (msg[i] >= 'a' ? 'a' : 'A'));
            } else {
                cipher += msg[i];
            }
        }
        
        return cipher;
    }

    // Function to decrypt the cipher text
    static String decryptCipher(String cipher, int a, int b) {
        String msg = "";
        int a_inv = 0;
        
        // Find a^-1 (the multiplicative inverse in the group of integers modulo m.)
        for (int i = 0; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                a_inv = i;
                break;
            }
        }
        
        // Normalize b to be within the range [0, 25]
        b = ((b % 26) + 26) % 26;  // Ensure that b is always in the range of [0, 25]

        // Decrypt the cipher text using the formula (a^-1 * (x - b)) % 26
        for (int i = 0; i < cipher.length(); i++) {
            if (cipher.charAt(i) != ' ') {
                int charPos = cipher.charAt(i) - (cipher.charAt(i) >= 'a' ? 'a' : 'A');
                msg += (char) ((a_inv * (charPos - b + 26) % 26) + (cipher.charAt(i) >= 'a' ? 'a' : 'A'));
            } else {
                msg += cipher.charAt(i);
            }
        }

        return msg;
    }

    // Driver code
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Taking input from the user for the message
        System.out.print("Enter the message to encrypt (Only letters and spaces allowed): ");
        String msg = scanner.nextLine().toLowerCase();  // Convert the input to lowercase to handle both cases.
        
        // Ensure the message has only letters and spaces
        if (!msg.matches("[a-z ]+")) {
            System.out.println("Invalid input! Please enter only letters and spaces.");
            return;
        }

        // Taking input for keys 'a' and 'b'
        System.out.print("Enter the key 'a' (must be coprime with 26): ");
        int a = scanner.nextInt(); // Input for 'a' key
        
        // Check if gcd(a, 26) is 1
        if (gcd(a, 26) != 1) {
            System.out.println("Error: The key 'a' must be coprime with 26! Please enter a valid 'a'.");
            return;
        }

        System.out.print("Enter the key 'b' (can be negative): ");
        int b = scanner.nextInt(); // Input for 'b' key

        // Calling encryption function
        String cipherText = encryptMessage(msg.toCharArray(), a, b);
        System.out.println("Encrypted Message is: " + cipherText);

        // Calling Decryption function
        String decryptedMessage = decryptCipher(cipherText, a, b);
        System.out.println("Decrypted Message is: " + decryptedMessage);

        scanner.close();
    }
}