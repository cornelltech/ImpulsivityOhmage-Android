package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;
import android.support.annotation.Nullable;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.task.Task;
import org.researchstack.skin.DataProvider;
import org.researchstack.skin.DataResponse;
import org.researchstack.skin.ResourceManager;
import org.researchstack.skin.model.SchedulesAndTasksModel;
import org.researchstack.skin.model.User;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.impulsivityohmage.ScheduleModels.CTFSchedule;
import edu.cornell.tech.foundry.impulsivityohmage.ScheduleModels.CTFScheduleItem;
import rx.Observable;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityDataProvider extends DataProvider {

    //TODO: Implement this
    @Override
    public Observable<DataResponse> initialize(Context context) {


        return Observable.defer(() -> {
            return Observable.just(new DataResponse(true, null));
        });
    }

    @Override
    public Observable<DataResponse> signUp(Context context, String email, String username, String password) {
        return null;
    }

    @Override
    public Observable<DataResponse> signIn(Context context, String username, String password) {
        return null;
    }

    @Override
    public Observable<DataResponse> signOut(Context context) {
        return null;
    }

    @Override
    public Observable<DataResponse> resendEmailVerification(Context context, String email) {
        return null;
    }

    @Override
    public boolean isSignedUp(Context context) {
        return false;
    }

    @Override
    public boolean isSignedIn(Context context) {
        return false;
    }

    @Override
    public boolean isConsented(Context context) {
        return false;
    }

    @Override
    public Observable<DataResponse> withdrawConsent(Context context, String reason) {
        return null;
    }

    @Override
    public void uploadConsent(Context context, TaskResult consentResult) {

       //

    }

    @Override
    public void saveConsent(Context context, TaskResult consentResult) {

        //test

    }

    @Override
    public User getUser(Context context) {
        return null;
    }

    @Override
    public String getUserSharingScope(Context context) {
        return null;
    }

    @Override
    public void setUserSharingScope(Context context, String scope) {

        //

    }

    @Override
    public String getUserEmail(Context context) {
        return null;
    }

    @Override
    public void uploadTaskResult(Context context, TaskResult taskResult) {

        //

    }

    @Override
    public SchedulesAndTasksModel loadTasksAndSchedules(Context context) {
        return null;
    }


    //TODO: Integrate RSTB here!!!

    public Task loadRSTBTask(Context context, CTFScheduleItem scheduleItem) {
        return null;
    }


    @Override
    public Task loadTask(Context context, SchedulesAndTasksModel.TaskScheduleModel task) {
        return null;
    }

    @Override
    public void processInitialTaskResult(Context context, TaskResult taskResult) {

        //

    }

    @Override
    public Observable<DataResponse> forgotPassword(Context context, String email) {
        return null;
    }

    @Nullable
    public CTFSchedule loadSchedule(Context context, String scheduleFilename) {
        ImpulsivityResourceManager resourceManager = (ImpulsivityResourceManager) ResourceManager.getInstance();
        CTFSchedule schedule = resourceManager.getSchedule(scheduleFilename).create(context);

        return schedule;
    }

    private boolean shouldShowScheduleItem(CTFScheduleItem item) {
        return true;
    }

    public List<CTFScheduleItem> loadScheduledItemsForSchedule(Context context, CTFSchedule schedule) {



        List<CTFScheduleItem> scheduleItems = new ArrayList();

        if (schedule != null) {
            for (CTFScheduleItem item: schedule.items) {

                if(this.shouldShowScheduleItem(item)) {
                    scheduleItems.add(item);
                }

            }

        }

        return scheduleItems;

    }
}
