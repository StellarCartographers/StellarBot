package space.tscg.util.crypto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

// Not actually used in production but kept for reference
public class KeyPairRSAGeneratorUtil
{
    public static void main(String[] args) throws Exception {
        createKeys();
    }
    
    private static void createKeys() throws NoSuchAlgorithmException, IOException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // We need to use a very large keysize due to FDev's access token being 802 bytes.
        kpg.initialize(8192);
        KeyPair kp = kpg.generateKeyPair();
        PrivateKey aPrivate = kp.getPrivate();
        PublicKey aPublic = kp.getPublic();

        try (FileOutputStream outPrivate = new FileOutputStream("key2.private")) {
            outPrivate.write(aPrivate.getEncoded());
        }

        try (FileOutputStream outPublic = new FileOutputStream("key2.public")) {
            outPublic.write(aPublic.getEncoded());
        }
    }
}
