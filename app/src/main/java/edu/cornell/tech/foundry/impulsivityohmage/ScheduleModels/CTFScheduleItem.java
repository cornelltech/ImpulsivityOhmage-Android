package edu.cornell.tech.foundry.impulsivityohmage.ScheduleModels;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by jameskizer on 2/1/17.
 */
public class CTFScheduleItem {
    public String type;
    public String identifier;
    public String title;
    public String guid;

    public JsonObject activity;
    public List<CTFResultTransform> resultTransforms;

    public CTFScheduleItem() {

    }

}
