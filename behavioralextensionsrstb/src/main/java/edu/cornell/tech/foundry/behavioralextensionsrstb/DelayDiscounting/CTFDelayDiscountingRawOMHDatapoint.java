package edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import java.util.Date;

import edu.cornell.tech.foundry.ohmclient.OMHAcquisitionProvenance;
import edu.cornell.tech.foundry.ohmclient.OMHDataPointBuilder;
import edu.cornell.tech.foundry.ohmclient.OMHSchema;

/**
 * Created by jameskizer on 2/4/17.
 */
public class CTFDelayDiscountingRawOMHDatapoint extends OMHDataPointBuilder {

    private CTFDelayDiscountingRaw rawResult;
    private OMHAcquisitionProvenance acquisitionProvenance;

    public CTFDelayDiscountingRawOMHDatapoint(Context context, CTFDelayDiscountingRaw rawResult) {
        this.rawResult = rawResult;
        this.acquisitionProvenance = new OMHAcquisitionProvenance(
                context.getPackageName(),
                rawResult.getStartDate(),
                OMHAcquisitionProvenance.OMHAcquisitionProvenanceModality.SENSED
        );
    }

    @Override
    public String getDataPointID() {
        return this.rawResult.getUuid().toString();
    }

    @Override
    public Date getCreationDateTime() {
        return this.rawResult.getStartDate();
    }

    @Override
    public OMHSchema getSchema() {
        return new OMHSchema(
                "DelayDiscountingRaw",
                "Cornell",
                "1.0"
        );
    }

    @Nullable
    @Override
    public OMHAcquisitionProvenance getAcquisitionProvenance() {
        return this.acquisitionProvenance;
    }

    @Override
    public JsonObject getBody() {
        return null;
    }
}
