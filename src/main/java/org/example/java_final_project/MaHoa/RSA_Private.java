package org.example.java_final_project.MaHoa;


import javax.crypto.Cipher;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSA_Private {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSA_Private() throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair pair = keyPairGen.generateKeyPair();
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String encrypt(String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes("UTF-8"));
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("Encrypted: " + encryptedString);
        return encryptedString;
    }

    public String decrypt(String encryptedMessage) throws Exception {
        System.out.println("Decrypting message: " + encryptedMessage);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedString = new String(decryptedBytes, "UTF-8");
        System.out.println("Decrypted: " + decryptedString);
        return decryptedString;
    }
}
