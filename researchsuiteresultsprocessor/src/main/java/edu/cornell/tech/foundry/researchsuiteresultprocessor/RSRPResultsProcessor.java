package edu.cornell.tech.foundry.researchsuiteresultprocessor;

import android.content.Context;
import android.util.Log;

import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.result.TaskResult;

import java.util.List;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPFrontEndServiceProvider.RSRPFrontEndService;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPFrontEndServiceProvider.spi.RSRPFrontEnd;

/**
 * Created by jameskizer on 2/3/17.
 */
public class RSRPResultsProcessor {

    private static final String TAG = "RSTBTaskBuilder";
    private Context context;
//    private RSRPFrontEndService frontEndService;


//    public RSRPResultsProcessor(Context context, List<RSRPFrontEnd> frontEnds) {
//        this.frontEndService = new RSRPFrontEndService(frontEnds);
//    }

//    public RSRPResultsProcessor(Context context, List<RSRPFrontEnd> frontEnds) {
//        this.frontEndService = new RSRPFrontEndService(frontEnds);
//    }

    public void processResult(TaskResult taskResult, List<RSRPResultTransform> resultTransforms) {
//        List<RSRPIntermediateResult> intermediateResults = this.frontEndService.processResult(taskResult, resultTransforms);
        List<RSRPIntermediateResult> intermediateResults = RSRPFrontEndService.getInstance().processResult(taskResult, resultTransforms);


        Log.d(TAG, "Processed results");

    }

}
