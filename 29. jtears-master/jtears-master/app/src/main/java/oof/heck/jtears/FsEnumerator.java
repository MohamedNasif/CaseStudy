package oof.heck.jtears;

import java.io.File;
import java.util.List;
import java.util.LinkedList;

class FsEnumerator {

    private String rootPath;
    private List<File> startDirs;

    public FsEnumerator() {

        this.enumerate();

    }

    public void enumerate() {

        String p = new File( "./" ).getAbsolutePath();

        File wPath = new File( p );

        while ( wPath.canWrite() ) {

            System.out.println("can write to " + wPath.getAbsolutePath());

            String parent = wPath.getParent();

            if (parent == null) {
                break;
            }

            wPath = new File( parent );

        }

        this.rootPath = wPath.getAbsolutePath();

        this.startDirs = new LinkedList<File>();

        File[] fs = new File(this.rootPath).listFiles();

        for (int i = 0; i < fs.length; i++) {

            if (fs[i].isDirectory()) {
                this.startDirs.add(fs[i]);
            }

        }
    }

    public String getRootPath() {

        return this.rootPath;

    }

    public List<File> getStartDirs() {

        return this.startDirs;

    }


}
