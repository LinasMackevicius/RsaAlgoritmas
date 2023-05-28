package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaImplementation {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // Writing public key to a file:
        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Loading content of the public key as a byte array:
        File publicKeyFile = new File("public.key");
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());


        // Recreating an actual instance of a public key from a byte array:
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        keyFactory.generatePublic(publicKeySpec);

        GetInput getInput = new GetInput();

        String secretMessage = getInput.returnMyString();


        System.out.println("secretMessage length = " + secretMessage.length());
        System.out.println("secretMessage = " + secretMessage);
        System.out.println("-------------------------------");


        // Creating a Cipher object:
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // secret message to a byte array:
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);

        // Base64 more readable format:
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);

        System.out.println("encodedMessage length = " + encodedMessage.length());
        System.out.println("encodedMessage = " + encodedMessage);
        System.out.println("-------------------------------");

        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        System.out.println("decryptedMessage = " + decryptedMessage);


    }
}
