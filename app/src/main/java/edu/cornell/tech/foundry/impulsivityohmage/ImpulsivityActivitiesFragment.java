package edu.cornell.tech.foundry.impulsivityohmage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.storage.file.StorageAccessListener;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.utils.LogExt;
import org.researchstack.backbone.utils.ObservableUtils;
import org.researchstack.skin.DataProvider;
import org.researchstack.skin.ui.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import edu.cornell.tech.foundry.impulsivityohmage.ScheduleModels.CTFSchedule;
import edu.cornell.tech.foundry.impulsivityohmage.ScheduleModels.CTFScheduleItem;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityActivitiesFragment extends Fragment implements StorageAccessListener {

    private static final int REQUEST_TASK = 1492;
    private ActivityAdapter  adapter;
    private RecyclerView recyclerView;
    private Subscription subscription;

    private List<CTFScheduleItem> visibleItems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(org.researchstack.skin.R.layout.rss_fragment_activities, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(org.researchstack.skin.R.id.recycler_view);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        unsubscribe();
    }

    private void unsubscribe()
    {
        if(subscription != null)
        {
            subscription.unsubscribe();
        }
    }

    //protected

    protected List<CTFScheduleItem> getScheduledActivities(Context context, ImpulsivityDataProvider dataProvider) {

        // load from activities filename for now
        String scheduleFilename = context.getString(R.string.activities_filename);

        CTFSchedule schedule = dataProvider.loadSchedule(context, scheduleFilename);


        return dataProvider.loadScheduledItemsForSchedule(context, schedule);
    }

    private void setUpAdapter()
    {
        unsubscribe();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL_LIST,
                0,
                false));

        Observable.create(subscriber -> {

            ImpulsivityDataProvider dataProvider = (ImpulsivityDataProvider) DataProvider.getInstance();

            List<CTFScheduleItem> scheduledItems = this.getScheduledActivities(getActivity(), dataProvider);

            subscriber.onNext(scheduledItems);
        })
                .compose(ObservableUtils.applyDefault())
                .map(o -> (List<CTFScheduleItem>) o)
                .subscribe(scheduledActivities -> {

                    adapter = new ActivityAdapter(scheduledActivities);
                    recyclerView.setAdapter(adapter);

                    subscription = adapter.getPublishSubject().subscribe(scheduleItem -> {


                        ImpulsivityDataProvider dataProvider = (ImpulsivityDataProvider) DataProvider.getInstance();
                        Task newTask = dataProvider.loadRSTBTask(getContext(), scheduleItem);

                        if(newTask == null)
                        {
                            Toast.makeText(getActivity(),
                                    org.researchstack.skin.R.string.rss_local_error_load_task,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = ViewTaskActivity.newIntent(getContext(), newTask);
                        intent.putExtra("guid", scheduleItem.guid);

                        startActivityForResult(intent, REQUEST_TASK);
                    });
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_TASK)
        {
            LogExt.d(getClass(), "Received task result from task activity");

            TaskResult taskResult = (TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT);
            StorageAccess.getInstance().getAppDatabase().saveTaskResult(taskResult);

            ImpulsivityDataProvider dataProvider = (ImpulsivityDataProvider) DataProvider.getInstance();
            dataProvider.processTaskResult(getActivity(), taskResult);

            setUpAdapter();
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDataReady()
    {
        LogExt.i(getClass(), "onDataReady()");

        setUpAdapter();
    }

    @Override
    public void onDataFailed()
    {
        // Ignore
    }

    @Override
    public void onDataAuth()
    {
        // Ignore, activity handles auth
    }

    public static class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

        List<CTFScheduleItem> scheduledItems;

        PublishSubject<CTFScheduleItem> publishSubject = PublishSubject.create();

        public ActivityAdapter(List<CTFScheduleItem> scheduledItems)
        {
            super();

            this.scheduledItems = new ArrayList<>(scheduledItems);
        }

        public PublishSubject<CTFScheduleItem> getPublishSubject()
        {
            return publishSubject;
        }

        @Override
        public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(org.researchstack.skin.R.layout.rss_item_schedule, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityAdapter.ViewHolder holder, int position)
        {
            CTFScheduleItem scheduledActivity = scheduledItems.get(position);

            Resources res = holder.itemView.getResources();
            int tintColor = res.getColor(org.researchstack.skin.R.color.rss_recurring_color);

            holder.title.setText(Html.fromHtml("<b>" + scheduledActivity.title + "</b>"));
            holder.title.setTextColor(tintColor);

            Drawable drawable = holder.dailyIndicator.getDrawable();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, tintColor);
            holder.dailyIndicator.setImageDrawable(drawable);

            holder.itemView.setOnClickListener(v -> {
                LogExt.d(getClass(), "Item clicked: " + scheduledActivity.guid);
                publishSubject.onNext(scheduledActivity);
            });
        }

        @Override
        public int getItemCount()
        {
            return this.scheduledItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView dailyIndicator;
            AppCompatTextView title;

            public ViewHolder(View itemView)
            {
                super(itemView);
                dailyIndicator = (ImageView) itemView.findViewById(org.researchstack.skin.R.id.daily_indicator);
                title = (AppCompatTextView) itemView.findViewById(org.researchstack.skin.R.id.task_title);
            }
        }
    }

}
