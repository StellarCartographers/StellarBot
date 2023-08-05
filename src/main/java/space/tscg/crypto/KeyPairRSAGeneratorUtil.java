package space.tscg.crypto;

// Not actually used in production but kept for reference
public class KeyPairRSAGeneratorUtil
{
//    public static void main(String[] args) throws Exception {
//        createKeys();
//        loadPrivateKey();
//        loadPublicKey();
//    }
//
//    private static void createKeys() throws NoSuchAlgorithmException, IOException {
//
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
//        kpg.initialize(2048);
//        KeyPair kp = kpg.generateKeyPair();
//        PrivateKey aPrivate = kp.getPrivate();
//        PublicKey aPublic = kp.getPublic();
//
//        try (FileOutputStream outPrivate = new FileOutputStream("key.private")) {
//            outPrivate.write(aPrivate.getEncoded());
//        }
//
//        try (FileOutputStream outPublic = new FileOutputStream("key.public")) {
//            outPublic.write(aPublic.getEncoded());
//        }
//    }
//
//    private static PrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//
//        File privateKeyFile = new File("key.private");
//        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
//
//        KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
//        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//        PrivateKey privateKey = privateKeyFactory.generatePrivate(privateKeySpec);
//        return privateKey;
//    }
//
//    private static PublicKey loadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        File publicKeyFile = new File("key.public");
//        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
//
//        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
//        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
//        PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);
//        return publicKey;
//    }
}
