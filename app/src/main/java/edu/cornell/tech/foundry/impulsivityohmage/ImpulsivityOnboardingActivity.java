package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.AppCompatButton;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.ui.PinCodeActivity;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.skin.AppPrefs;
import org.researchstack.skin.step.PassCodeCreationStep;
import org.researchstack.skin.task.OnboardingTask;
import org.researchstack.skin.ui.ConsentTaskActivity;
import org.researchstack.skin.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.ohmageomhsdkrs.CTFLogInStep;
import edu.cornell.tech.foundry.ohmageomhsdkrs.CTFLogInStepLayout;
import edu.cornell.tech.foundry.ohmageomhsdkrs.CTFOhmageLogInStepLayout;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityOnboardingActivity extends PinCodeActivity {

    //    public static final int REQUEST_CODE_SIGN_UP  = 21473;
    public static final int REQUEST_CODE_SIGN_IN  = 31473;

    public static final int REQUEST_CODE_PASSCODE = 41473;

    public static final String LOG_IN_STEP_IDENTIFIER = "login step identifier";
    public static final String LOG_IN_TASK_IDENTIFIER = "login task identifier";
    public static final String PASS_CODE_TASK_IDENTIFIER = "passcode task identifier";

    private AppCompatButton log_in_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //load login screen here
        super.setContentView(R.layout.impulsivity_onboarding_activity);

        this.log_in_button = (AppCompatButton) findViewById(R.id.log_in_button);
        this.log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInClicked(view);
            }
        });


    }

    @Override
    public void onDataAuth()
    {
        if(StorageAccess.getInstance().hasPinCode(this))
        {
            super.onDataAuth();
        }
        else // allow onboarding if no pincode
        {
            onDataReady();
        }
    }

    private void showPassCodeStep() {
        PassCodeCreationStep passCodeStep = new PassCodeCreationStep(OnboardingTask.SignUpPassCodeCreationStepIdentifier,
                org.researchstack.skin.R.string.rss_passcode);

        OrderedTask task = new OrderedTask(PASS_CODE_TASK_IDENTIFIER, passCodeStep);
        startActivityForResult(ConsentTaskActivity.newIntent(this, task),
                REQUEST_CODE_PASSCODE);

    }

    //pass code MUST be set when we launch this
    private void showLogInStep() {
        CTFLogInStep logInStep = new CTFLogInStep(
                ImpulsivityOnboardingActivity.LOG_IN_STEP_IDENTIFIER,
                "Log In",
                "Please log in",
                CTFOhmageLogInStepLayout.class
        );
        logInStep.setForgotPasswordButtonTitle("Skip Log In");
        logInStep.setOptional(false);

        OrderedTask task = new OrderedTask(ImpulsivityOnboardingActivity.LOG_IN_TASK_IDENTIFIER, logInStep);
        startActivityForResult(ConsentTaskActivity.newIntent(this, task),
                REQUEST_CODE_SIGN_IN);
    }

    public void logInClicked(View view)
    {

        boolean hasPasscode = StorageAccess.getInstance().hasPinCode(this);
        if(hasPasscode) {
            showLogInStep();
        }
        else {
            showPassCodeStep();
        }
//        hidePager();
//
//        //if we get here, we either are not logged in or we have not yet skipped
//        //need to show log in screen
//        //potentially also show passcode step
//
//        List<Step> logInSteps = new ArrayList<>();
//
//        CTFLogInStep logInStep = new CTFLogInStep(
//                ImpulsivityOnboardingActivity.LOG_IN_STEP_IDENTIFIER,
//                "Log In",
//                "Please log in",
//                CTFOhmageLogInStepLayout.class
//                );
//        logInStep.setForgotPasswordButtonTitle("Skip Log In");
//        logInStep.setOptional(false);
//
//        logInSteps.add(logInStep);
//
//
//        boolean hasPasscode = StorageAccess.getInstance().hasPinCode(this);
//        if(! hasPasscode)
//        {
//            PassCodeCreationStep passCodeStep = new PassCodeCreationStep(OnboardingTask.SignUpPassCodeCreationStepIdentifier,
//                    org.researchstack.skin.R.string.rss_passcode);
//
////            TextAnswerFormat answerFormat = new TextAnswerFormat();
////
////            QuestionStep qs = new QuestionStep("externalId", "Participant ID", answerFormat);
////            qs.setPlaceholder("Enter Participant ID");
////
////            ConfirmationTextAnswerFormat confirmationFormat = new ConfirmationTextAnswerFormat();
////            ConfirmationStep cs = new ConfirmationStep("confirmExternalId", "Confirm Participant ID", confirmationFormat, "externalId");
////            cs.setPlaceholder("Confirm Participant ID");
//
//            logInSteps.add(passCodeStep);
//
//        }
//
//        OrderedTask task = new OrderedTask(ImpulsivityOnboardingActivity.LOG_IN_TASK_IDENTIFIER, logInSteps);
//        startActivityForResult(ConsentTaskActivity.newIntent(this, task),
//                REQUEST_CODE_SIGN_IN);
    }

    private void skipToMainActivity()
    {
        AppPrefs.getInstance(this).setSkippedOnboarding(true);
        startMainActivity();
    }

    private void startMainActivity()
    {
        // Onboarding completion is checked in splash activity. The check allows us to pass through
        // to MainActivity even if we haven't signed in. We want to set this true in every case so
        // the user is really only forced through Onboarding once. If they leave the study, they must
        // re-enroll in Settings, which starts OnboardingActivty.
        AppPrefs.getInstance(this).setOnboardingComplete(true);

        // Start MainActivity w/ clear_top and single_top flags. MainActivity may
        // already be on the activity-task. We want to re-use that activity instead
        // of creating a new instance and have two instance active.
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE_PASSCODE && resultCode == RESULT_OK)
        {
            TaskResult result = (TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT);

            StepResult passcodeResult = result.getStepResult(OnboardingTask.SignUpPassCodeCreationStepIdentifier);
            if (passcodeResult != null) {
                String passcode = (String)passcodeResult.getResult();
                StorageAccess.getInstance().createPinCode(this, passcode);
                showLogInStep();
            }


        }
        else if(requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK) {
            TaskResult result = (TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT);

            Boolean isLoggedIn = (Boolean) result.getStepResult(ImpulsivityOnboardingActivity.LOG_IN_STEP_IDENTIFIER)
                    .getResultForIdentifier(CTFLogInStepLayout.LoggedInResultIdentifier);

            if(isLoggedIn.booleanValue()) {
                AppPrefs.getInstance(this).setSkippedOnboarding(false);
            }

            skipToMainActivity();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}