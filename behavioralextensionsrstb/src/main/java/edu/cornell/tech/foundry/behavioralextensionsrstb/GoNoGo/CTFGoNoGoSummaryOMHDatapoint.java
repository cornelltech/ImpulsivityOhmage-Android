package edu.cornell.tech.foundry.behavioralextensionsrstb.GoNoGo;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting.CTFDelayDiscountingRaw;
import edu.cornell.tech.foundry.ohmclient.OMHAcquisitionProvenance;
import edu.cornell.tech.foundry.ohmclient.OMHDataPointBuilder;
import edu.cornell.tech.foundry.ohmclient.OMHSchema;

/**
 * Created by jameskizer on 2/6/17.
 */

public class CTFGoNoGoSummaryOMHDatapoint extends OMHDataPointBuilder {

    private CTFGoNoGoSummary summaryResult;
    private OMHAcquisitionProvenance acquisitionProvenance;

    public CTFGoNoGoSummaryOMHDatapoint(Context context, CTFGoNoGoSummary summaryResult) {
        this.summaryResult = summaryResult;
        this.acquisitionProvenance = new OMHAcquisitionProvenance(
                context.getPackageName(),
                summaryResult.getStartDate(),
                OMHAcquisitionProvenance.OMHAcquisitionProvenanceModality.SENSED
        );
    }

    @Override
    public String getDataPointID() {
        return this.summaryResult.getUuid().toString();
    }

    @Override
    public Date getCreationDateTime() {
        return this.summaryResult.getStartDate();
    }

    @Override
    public OMHSchema getSchema() {
        return new OMHSchema(
                "GoNoGoSummary",
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
    public JSONObject getBody() {

        Map<String, Object> map = new HashMap<>();
        map.put("total", this.summaryResult.getTotalSummary().toJson());
        map.put("firstThird", this.summaryResult.getTotalSummary().toJson());
        map.put("middleThird", this.summaryResult.getTotalSummary().toJson());
        map.put("lastThird", this.summaryResult.getTotalSummary().toJson());

        return new JSONObject(map);
    }

}
