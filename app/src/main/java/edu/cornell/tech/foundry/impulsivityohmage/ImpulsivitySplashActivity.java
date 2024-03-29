package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Intent;
import android.os.Bundle;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.ui.PinCodeActivity;
import org.researchstack.backbone.utils.ObservableUtils;
import org.researchstack.skin.AppPrefs;
import org.researchstack.skin.DataProvider;
import org.researchstack.skin.notification.TaskAlertReceiver;
import org.researchstack.skin.ui.MainActivity;

/**
 * Created by jameskizer on 2/1/17.
 */

public class ImpulsivitySplashActivity extends PinCodeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDataReady()
    {
        super.onDataReady();
        // Init all notifications
        sendBroadcast(new Intent(TaskAlertReceiver.ALERT_CREATE_ALL));

        DataProvider.getInstance()
                .initialize(this)
                .compose(ObservableUtils.applyDefault())
                .subscribe(response -> {


                    boolean skipped = AppPrefs.getInstance(this).skippedOnboarding();
                    boolean signedIn =  DataProvider.getInstance().isSignedIn(this);

                    //we want to show the main activity if we are logged in
                    //or the user has skipped onboarding
                    if(skipped || signedIn)
                    {
                        launchMainActivity();
                    }
                    else
                    {
                        launchOnboardingActivity();
                    }

                    finish();
                });
    }

    @Override
    public void onDataAuth()
    {
        if(StorageAccess.getInstance().hasPinCode(this))
        {
            super.onDataAuth();
        }
        else // allow them through to onboarding if no pincode
        {
            onDataReady();
        }
    }

    @Override
    public void onDataFailed()
    {
        super.onDataFailed();
        finish();
    }

    private void launchOnboardingActivity()
    {
        startActivity(new Intent(this, ImpulsivityOnboardingActivity.class));
    }

    private void launchMainActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
    }

}
