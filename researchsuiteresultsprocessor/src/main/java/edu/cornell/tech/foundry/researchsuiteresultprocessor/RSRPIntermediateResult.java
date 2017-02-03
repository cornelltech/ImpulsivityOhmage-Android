package edu.cornell.tech.foundry.researchsuiteresultprocessor;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jameskizer on 2/2/17.
 */
public class RSRPIntermediateResult {

    private String type;
    private UUID uuid;
    private Date startDate;
    private Date endDate;

    public RSRPIntermediateResult(String type, UUID uuid, Date startDate, Date endDate) {
        this.type = type;
        this.uuid = uuid;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
