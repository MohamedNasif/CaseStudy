package droidefense.worker.base;


import droidefense.sdk.model.base.DroidefenseProject;
import droidefense.sdk.model.io.LocalApkFile;
import droidefense.sdk.util.ExecutionTimer;

import java.io.Serializable;

/**
 * Created by r00t on 24/10/15.
 */
public abstract class AbstractFileParser implements Serializable {

    protected transient LocalApkFile apk;
    protected transient DroidefenseProject currentProject;
    protected ExecutionTimer timestamp;

    public AbstractFileParser(LocalApkFile apk, DroidefenseProject currentProject) {
        this.apk = apk;
        this.currentProject = currentProject;
    }

    public void parse() {
        timestamp = new ExecutionTimer();
        parserCode();
        timestamp.stop();
    }

    public abstract void parserCode();

    public DroidefenseProject getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(DroidefenseProject currentProject) {
        this.currentProject = currentProject;
    }
}
