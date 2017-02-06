package edu.cornell.tech.foundry.behavioralextensionsrstb.DelayDiscounting;

import edu.cornell.tech.foundry.behavioralextensions.DelayDiscounting.CTFDelayDiscountingResult;
import edu.cornell.tech.foundry.behavioralextensions.DelayDiscounting.CTFDelayDiscountingTrial;
import edu.cornell.tech.foundry.behavioralextensions.DelayDiscounting.CTFDelayDiscountingTrialResult;
import edu.cornell.tech.foundry.researchsuiteresultprocessor.RSRPIntermediateResult;

/**
 * Created by jameskizer on 2/3/17.
 */
public class CTFDelayDiscountingRaw extends RSRPIntermediateResult {

    private String variableLabel;
    private Double[] nowArray;
    private Double[] laterArray;
    private Double[] choiceArray;
    private Double[] times;

    public static String type = "DelayDiscountingRaw";

    public CTFDelayDiscountingRaw(CTFDelayDiscountingResult result) {
        super(type);

        this.variableLabel = result.getIdentifier();

        CTFDelayDiscountingTrialResult[] trialResults = result.getTrialResults();
        if (trialResults == null) {
            return;
        }

        this.nowArray = new Double[trialResults.length];
        this.laterArray = new Double[trialResults.length];
        this.choiceArray = new Double[trialResults.length];
        this.times = new Double[trialResults.length];

        for(int i=0; i<trialResults.length; i++) {
            CTFDelayDiscountingTrialResult trialResult = trialResults[i];
            CTFDelayDiscountingTrial trial = trialResult.getTrial();
            this.nowArray[i] = trial.getNow();
            this.laterArray[i] = trial.getLater();
            this.choiceArray[i] = trialResult.getChoiceValue();
            this.times[i] = trialResult.getResponseTime();
         }

    }

    public String getVariableLabel() {
        return variableLabel;
    }

    public Double[] getNowArray() {
        return nowArray;
    }

    public Double[] getLaterArray() {
        return laterArray;
    }

    public Double[] getChoiceArray() {
        return choiceArray;
    }

    public Double[] getTimes() {
        return times;
    }
}
