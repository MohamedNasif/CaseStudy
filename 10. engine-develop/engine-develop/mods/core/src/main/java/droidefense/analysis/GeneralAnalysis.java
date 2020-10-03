package droidefense.analysis;

import droidefense.analysis.base.AbstractAndroidAnalysis;
import droidefense.analysis.base.AnalysisFactory;
import droidefense.exception.UnknownAnalyzerException;
import droidefense.log4j.Log;
import droidefense.log4j.LoggerType;

/**
 * Created by sergio on 4/9/16.
 */
public final class GeneralAnalysis extends AbstractAndroidAnalysis {

    @Override
    protected boolean analyze() {

        executionSuccessful = false;
        Log.write(LoggerType.TRACE, "\n\n --- Droidefense: sample scan started ---\n\n");

        AbstractAndroidAnalysis analyzer;
        try {

            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.UNPACK);
            currentProject.analyze(analyzer);

            if (currentProject.isCorrectUnpacked()) {

                analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.DECODE);
                currentProject.analyze(analyzer);

                if (currentProject.isCorrectDecoded()) {

                    //STATIC ANALYSIS
                    analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.STATIC_ANALYSIS);
                    currentProject.analyze(analyzer);

                    if (currentProject.isStaticAnalysisDone()) {

                        //RUN STATIC ANALYSIS PLUGINS
                        analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.STATIC_ANALYSIS_PLUGIN);
                        currentProject.analyze(analyzer);

                        //RUN PRIVACY SCAN
                        analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.PRIVACY_ANALYSIS);
                        currentProject.analyze(analyzer);

                        //RUN DYNAMIC ANALYSIS
                        analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.DYNAMIC_ANALYSIS);
                        currentProject.analyze(analyzer);

                        if (currentProject.isDynamicAnalysisDone()) {

                            //RUN DYNAMIC ANALYSIS PLUGINS
                            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.DYNAMIC_PLUGIN_ANALYSIS);
                            currentProject.analyze(analyzer);

                            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.PSOCUT_ANALYSIS);
                            currentProject.analyze(analyzer);

                            //RUN RULE ENGINE
                            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.RULE_ENGINE_ANALYSIS);
                            currentProject.analyze(analyzer);

                            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.MACHINE_LEARNING_ANALYSIS);
                            currentProject.analyze(analyzer);

                            analyzer = AnalysisFactory.getAnalyzer(AnalysisFactory.EVENT_ANALYSIS);
                            currentProject.analyze(analyzer);

                        } else {
                            //dynamic plugin failed
                            Log.write(LoggerType.ERROR, "Error executing dynamic analysis plugins and contextual scans");
                        }

                    } else {
                        //dynamic failed
                        Log.write(LoggerType.ERROR, "Error executing dynamic analysis");
                    }

                } else {
                    //decoded error
                    Log.write(LoggerType.ERROR, "Error decoding sample");
                }
            } else {
                //unpack error
                Log.write(LoggerType.ERROR, "Error unpacking sample");
            }
            this.timeStamp.stop();
            return true;
        } catch (UnknownAnalyzerException e) {
            Log.write(LoggerType.FATAL, "An error ocurred while running general scan", e.getLocalizedMessage());
            this.timeStamp.stop();
            this.errorList.add(e);
            return false;
        }
    }

    @Override
    public String getName() {
        return "Droidefense engine";
    }
}
