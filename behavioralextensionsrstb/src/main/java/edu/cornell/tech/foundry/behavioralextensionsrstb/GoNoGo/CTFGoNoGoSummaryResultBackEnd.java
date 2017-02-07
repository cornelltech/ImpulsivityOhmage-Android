package edu.cornell.tech.foundry.behavioralextensionsrstb.GoNoGo;

import android.content.Context;

import edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting.CTFDelayDiscountingRaw;
import edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting.CTFDelayDiscountingRawOMHDatapoint;
import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.ohmclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 2/7/17.
 */

public class CTFGoNoGoSummaryResultBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        CTFGoNoGoSummary summary = (CTFGoNoGoSummary) intermediateResult;
        return new CTFGoNoGoSummaryOMHDatapoint(context, summary);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof CTFGoNoGoSummary) {
            return true;
        }
        else {
            return false;
        }
    }
}
