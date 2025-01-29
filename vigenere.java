public class vigenere {
    // Function to repeat the key to match the length of the text
    public static String repeatKey(String text, String key) {
        key = key.toUpperCase();
        StringBuilder repeatedKey = new StringBuilder();
        int keyIndex = 0;

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) { // Repeat key only for letters
                repeatedKey.append(key.charAt(keyIndex % key.length()));
                keyIndex++;
            } else {
                repeatedKey.append(ch); // Keep non-alphabetic characters unchanged
            }
        }
        return repeatedKey.toString();
    }

    // Function to encrypt plaintext using the Vigenère Cipher
    public static String vigenereEncrypt(String plaintext, String key) {
        String repeatedKey = repeatKey(plaintext, key);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char pChar = plaintext.charAt(i);
            char kChar = repeatedKey.charAt(i);

            if (Character.isLetter(pChar)) {
                char base = Character.isUpperCase(pChar) ? 'A' : 'a';
                char kBase = 'A'; // Key is always uppercase
                char encryptedChar = (char) ((pChar - base + (kChar - kBase)) % 26 + base);
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(pChar); // Keep non-alphabetic characters unchanged
            }
        }
        return encryptedText.toString();
    }

    // Function to decrypt ciphertext using the Vigenère Cipher
    public static String vigenereDecrypt(String ciphertext, String key) {
        String repeatedKey = repeatKey(ciphertext, key);
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            char cChar = ciphertext.charAt(i);
            char kChar = repeatedKey.charAt(i);

            if (Character.isLetter(cChar)) {
                char base = Character.isUpperCase(cChar) ? 'A' : 'a';
                char kBase = 'A'; // Key is always uppercase
                char decryptedChar = (char) ((cChar - base - (kChar - kBase) + 26) % 26 + base);
                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(cChar); // Keep non-alphabetic characters unchanged
            }
        }
        return decryptedText.toString();
    }

    public static void main(String[] args) {
        String plaintext = "Do not judge student by their backlogs ";
        String key = "KEY";

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Key: " + key);

        // Encryption
        String ciphertext = vigenereEncrypt(plaintext, key);
        System.out.println("Encrypted Text: " + ciphertext);

        // Decryption
        String decryptedText = vigenereDecrypt(ciphertext, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
