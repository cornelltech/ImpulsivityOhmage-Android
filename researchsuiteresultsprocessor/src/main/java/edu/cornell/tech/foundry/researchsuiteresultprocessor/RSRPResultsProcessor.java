package edu.cornell.tech.foundry.researchsuiteresultprocessor;

import android.content.Context;
import android.util.Log;

import org.researchstack.backbone.ResourcePathManager;
import org.researchstack.backbone.result.TaskResult;

import java.util.List;

import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPFrontEndServiceProvider.RSRPFrontEndService;

/**
 * Created by jameskizer on 2/3/17.
 */
public class RSRPResultsProcessor {

    private static final String TAG = "RSTBTaskBuilder";
    private Context context;
    private RSRPFrontEndService frontEnd = RSRPFrontEndService.getInstance();


    public RSRPResultsProcessor(Context context) {

    }

    public void processResult(TaskResult taskResult, List<RSRPResultTransform> resultTransforms) {
        List<RSRPIntermediateResult> intermediateResults = frontEnd.processResult(taskResult, resultTransforms);


        Log.d(TAG, "Processed results");

    }

}
