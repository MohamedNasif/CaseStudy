package droidefense.handler;


import droidefense.handler.base.AbstractHandler;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;
import droidefense.sdk.model.io.AbstractHashedFile;
import droidefense.sdk.model.signature.Signature;
import droidefense.sdk.model.signature.SignatureMap;
import droidefense.worker.loader.SignatureModelLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sergio on 16/2/16.
 */
public class SignatureHandler extends AbstractHandler {

    //Class vars
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_SIGNATURE_SIZE = 40;
    private static boolean loaded;
    private static SignatureMap model;
    private static SignatureHandler instance;

    //instance vars
    private String description;
    private String expectedFiletype;
    private AbstractHashedFile file;
    private boolean valid;
    private String nameExtension;

    private SignatureHandler() {
        if (!loaded) {
            SignatureModelLoader loader = new SignatureModelLoader();
            loader.load();
            model = loader.getModel();
            loaded = true;
        }
        valid = false;
    }

    public static SignatureHandler getInstance() {
        if (instance == null) {
            instance = new SignatureHandler();
        }
        return instance;
    }

    @Override
    public boolean doTheJob() {
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream in;
        int n;
        try {
            in = file.getStream();
            n = in.read(buffer, 0, BUFFER_SIZE);
            int m = n;
            while ((m < MAX_SIGNATURE_SIZE) && (n > 0)) {
                n = in.read(buffer, m, BUFFER_SIZE - m);
                m += n;
            }
            in.close();
            Signature s = model.checkSignature(file.getExtension(), buffer);
            if (s != null) {
                expectedFiletype = s.getExtension();
                description = s.getFiletypeInfo();
                valid = expectedFiletype.equalsIgnoreCase(nameExtension);
            }
        } catch (IOException e) {
            Log.write(LoggerType.ERROR, "Droidefense could not 'do the job'", e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    public AbstractHashedFile getUpdatedResource() {
        return file;
    }

    public AbstractHashedFile getFile() {
        return file;
    }

    public void setFile(AbstractHashedFile AbstractHashedFile) {
        this.file = AbstractHashedFile;
    }

    public void updateDescription() {
        if (valid) {
            Log.write(LoggerType.DEBUG, "File " + getFile().getName() + " identified as " + description);
            getFile().setExtension(nameExtension);
            getFile().setDescription(description);
            getFile().setExtensionFromHeader(expectedFiletype);
            getFile().setSignatureMatches();
        } else {
            getFile().setExtension(nameExtension);
            getFile().setDescription("unknown");
            getFile().setExtensionFromHeader("unknown");

            Log.write(LoggerType.DEBUG, "File NOT identified: " + getFile().getName());
            try {
                System.err.println(new String(getFile().getContent()));
            } catch (IOException e) {
            }
        }
    }

    public String getNameExtension() {
        return nameExtension;
    }

    public void setNameExtension(String nameExtension) {
        this.nameExtension = nameExtension;
    }

    public boolean isSignatureFound() {
        return this.valid;
    }
}
