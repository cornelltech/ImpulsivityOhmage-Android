package edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting;

import android.content.Context;

import edu.cornell.tech.foundry.ohmageomhbackend.ORBEIntermediateResultTransformer.spi.ORBEIntermediateResultTransformer;
import edu.cornell.tech.foundry.ohmclient.OMHDataPoint;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 2/4/17.
 */
public class CTFDelayDiscountingRawResultBackEnd implements ORBEIntermediateResultTransformer {
    @Override
    public OMHDataPoint transform(Context context, RSRPIntermediateResult intermediateResult) {
        CTFDelayDiscountingRaw rawResult = (CTFDelayDiscountingRaw) intermediateResult;
        return new CTFDelayDiscountingRawOMHDatapoint(context, rawResult);
    }

    @Override
    public boolean canTransform(RSRPIntermediateResult intermediateResult) {
        if( intermediateResult instanceof CTFDelayDiscountingRaw ) {
            return true;
        }
        else {
            return false;
        }
    }
}
