package edu.cornell.tech.foundry.ohmclient;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Date;

/**
 * Created by jameskizer on 2/2/17.
 */
public abstract class OMHDataPointBuilder extends OMHDataPoint {

    public abstract String getDataPointID();
    public abstract Date getCreationDateTime();
    public abstract OMHSchema getSchema();

    @Nullable
    public abstract OMHAcquisitionProvenance getAcquisitionProvenance();

    public abstract JsonObject getBody();

    public JsonObject getSchemaJson() {
        OMHSchema schema = this.getSchema();
        JsonObject returnObject = new JsonObject();
        returnObject.add("name", new JsonPrimitive(schema.getName()));
        returnObject.add("namespace", new JsonPrimitive(schema.getNamespace()));
        returnObject.add("version", new JsonPrimitive(schema.getVersion()));
        return returnObject;
    }

    public JsonObject getAcquisitionProvenanceJson() {

        OMHAcquisitionProvenance acquisitionProvenance = this.getAcquisitionProvenance();

        if (acquisitionProvenance != null) {
            JsonObject returnObject = new JsonObject();

            String sourceName = acquisitionProvenance.getSourceName();
            if (sourceName != null) {
                returnObject.add("source_name", new JsonPrimitive(sourceName));
            }

            String sourceCreationDateTime = OMHDataPoint.stringFromDate(acquisitionProvenance.getSourceCreationDate());
            if (sourceCreationDateTime != null) {
                returnObject.add("source_name", new JsonPrimitive(sourceCreationDateTime));
            }

            String modality = acquisitionProvenance.getModalityString();
            if (modality != null) {
                returnObject.add("modality", new JsonPrimitive(modality));
            }

            return returnObject;
        }
        else {
            return null;
        }
    }

    public JsonObject getHeader() {
        JsonObject returnObject = new JsonObject();
        returnObject.add("id", new JsonPrimitive(this.getDataPointID()));
        returnObject.add("creation_date_time", new JsonPrimitive(OMHDataPoint.stringFromDate(this.getCreationDateTime())));
        returnObject.add("schema_id", this.getSchemaJson());

        JsonObject acquisitionProvenance = this.getAcquisitionProvenanceJson();
        if (acquisitionProvenance != null) {
            returnObject.add("acquisition_provenance", acquisitionProvenance);
        }

        return returnObject;
    }

    @Override
    public JsonObject toJson() {
        JsonObject returnObject = new JsonObject();
        returnObject.add("header", this.getHeader());
        returnObject.add("body", this.getBody());
        return returnObject;
    }
}
