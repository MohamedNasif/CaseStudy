/**
 * Copyright (C) 2018 Ryszard Wiśniewski <brut.alll@gmail.com>
 * Copyright (C) 2018 Connor Tumbleson <connor.tumbleson@gmail.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package brut.directory;

import brut.common.BrutException;
import brut.util.BrutIO;
import brut.util.OS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class DirUtil {
    public static void copyToDir(Directory in, Directory out)
            throws DirectoryException {
        for (String fileName : in.getFiles(true)) {
            copyToDir(in, out, fileName);
        }
    }

    public static void copyToDir(Directory in, Directory out,
                                 String[] fileNames) throws DirectoryException {
        for (int i = 0; i < fileNames.length; i++) {
            copyToDir(in, out, fileNames[i]);
        }
    }

    public static void copyToDir(Directory in, Directory out, String fileName)
            throws DirectoryException {
        try {
            if (in.containsDir(fileName)) {
                // TODO: remove before copying
                in.getDir(fileName).copyToDir(out.createDir(fileName));
            } else {
                BrutIO.copyAndClose(in.getFileInput(fileName),
                        out.getFileOutput(fileName));
            }
        } catch (IOException ex) {
            throw new DirectoryException(
                    "Error copying file: " + fileName, ex);
        }
    }

    public static void copyToDir(Directory in, File out)
            throws DirectoryException {
        for (String fileName : in.getFiles(true)) {
            copyToDir(in, out, fileName);
        }
    }

    public static void copyToDir(Directory in, File out, String[] fileNames)
            throws DirectoryException {
        for (int i = 0; i < fileNames.length; i++) {
            copyToDir(in, out, fileNames[i]);
        }
    }

    public static void copyToDir(Directory in, File out, String fileName)
            throws DirectoryException {
        try {
            if (in.containsDir(fileName)) {
                OS.rmdir(new File(out, fileName));
                in.getDir(fileName).copyToDir(new File(out, fileName));
            } else {
                if (fileName.equals("res") && !in.containsFile(fileName)) {
                    return;
                }
                File outFile = new File(out, fileName);
                outFile.getParentFile().mkdirs();
                BrutIO.copyAndClose(in.getFileInput(fileName),
                        new FileOutputStream(outFile));
            }
        } catch (IOException ex) {
            throw new DirectoryException(
                    "Error copying file: " + fileName, ex);
        } catch (BrutException ex) {
            throw new DirectoryException(
                    "Error copying file: " + fileName, ex);
        }
    }
}
