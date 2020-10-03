package droidefense.sdk;

import droidefense.analysis.base.PluginAnalysis;
import droidefense.sdk.enums.ProcessStatus;
import droidefense.sdk.model.base.DroidefenseProject;
import droidefense.sdk.util.ExecutionTimer;

/**
 * Created by sergio on 18/2/16.
 */
public abstract class AbstractStaticPlugin extends PluginAnalysis {

    public void analyze() {
        //initialize variables
        status = ProcessStatus.WAITING;
        this.timeStamp = new ExecutionTimer();
        this.positiveMatch = false;

        status = ProcessStatus.STARTED;
        //pre execute
        onPreExecute();
        status = ProcessStatus.EXECUTING;
        //execute
        onExecute();
        //post execute
        postExecute();
        status = ProcessStatus.FINISHED;
        //stop time counter
        this.timeStamp.stop();
    }

    protected abstract void onPreExecute();

    protected abstract void onExecute();

    protected abstract void postExecute();

    public DroidefenseProject getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(DroidefenseProject currentProject) {
        this.currentProject = currentProject;
    }
}
