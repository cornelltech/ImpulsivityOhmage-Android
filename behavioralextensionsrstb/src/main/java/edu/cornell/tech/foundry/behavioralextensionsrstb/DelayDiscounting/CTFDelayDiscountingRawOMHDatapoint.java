package edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public JSONObject getBody() {

        Map<String, Object> map = new HashMap<>();
        map.put("variable_label", this.rawResult.getVariableLabel());
        map.put("now_array", this.rawResult.getNowArray());
        map.put("later_array", this.rawResult.getLaterArray());
        map.put("choice_array", this.rawResult.getChoiceArray());
        map.put("times", this.rawResult.getTimes());

        return new JSONObject(map);
    }
}
