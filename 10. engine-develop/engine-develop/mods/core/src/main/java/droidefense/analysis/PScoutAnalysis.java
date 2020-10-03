package droidefense.analysis;

import droidefense.analysis.base.AbstractAndroidAnalysis;
import droidefense.emulator.handlers.PscoutHandler;
import droidefense.handler.base.AbstractHandler;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;

/**
 * Created by sergio on 25/3/16.
 */
public final class PScoutAnalysis extends AbstractAndroidAnalysis {

    @Override
    protected boolean analyze() {

        Log.write(LoggerType.TRACE, "\n\n --- Running Droidefense psocut analysis ---\n\n");
        AbstractHandler handler;

        //run pscout apimodel
        handler = new PscoutHandler(currentProject, currentProject.getNormalControlFlowMap().getNodeList());
        executionSuccessful = handler.doTheJob();

        //stop timer
        stop();
        return executionSuccessful;
    }

    @Override
    public String getName() {
        return "PSCout analysis";
    }
}
