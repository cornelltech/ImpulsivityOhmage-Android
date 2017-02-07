package edu.cornell.tech.foundry.behavioralextensionsrstb.BART;

import android.content.Context;

import edu.cornell.tech.foundry.behavioralextensionsrstb.GoNoGo.CTFGoNoGoSummary;
import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.ohmclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 2/7/17.
 */

public class CTFBARTSummaryResultBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        CTFBARTSummary summary = (CTFBARTSummary) intermediateResult;
        return new CTFBARTSummaryOMHDatapoint(context, summary);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof CTFBARTSummary) {
            return true;
        }
        else {
            return false;
        }
    }
}
