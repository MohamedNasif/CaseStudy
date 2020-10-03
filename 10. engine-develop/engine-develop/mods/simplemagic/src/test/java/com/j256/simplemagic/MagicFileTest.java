package com.j256.simplemagic;

import com.j256.simplemagic.ContentInfoUtil.ErrorCallBack;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

@Ignore("For now we have to skip this, sigh")
public class MagicFileTest {

    @Test
    public void testMagicFileParse() throws IOException {
        new ContentInfoUtil("/magic.gz", new ErrorCallBack() {
            @Override
            public void error(String line, String details, Exception e) {
                throw new RuntimeException("Got this error: '" + details + "', on line: " + line, e);
            }
        });
    }
}
