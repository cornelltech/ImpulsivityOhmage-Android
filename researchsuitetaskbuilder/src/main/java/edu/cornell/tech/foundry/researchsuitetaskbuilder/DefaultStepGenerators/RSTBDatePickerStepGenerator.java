package edu.cornell.tech.foundry.researchsuitetaskbuilder.DefaultStepGenerators;

import com.google.gson.JsonObject;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;

import java.util.Arrays;

import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBTaskBuilderHelper;

/**
 * Created by jameskizer on 1/21/17.
 */
public class RSTBDatePickerStepGenerator extends RSTBQuestionStepGenerator {
    public RSTBDatePickerStepGenerator() {
        super();
        this.supportedTypes = Arrays.asList(
                "datePicker"
        );
    }

    public AnswerFormat generateAnswerFormat(RSTBTaskBuilderHelper helper, String type, JsonObject jsonObject) {

        return new DateAnswerFormat(AnswerFormat.DateAnswerStyle.Date);

    }
}
