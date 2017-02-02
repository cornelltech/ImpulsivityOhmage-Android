package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;
import android.util.Log;

import org.researchstack.backbone.task.Task;
import org.researchstack.skin.TaskProvider;
import org.researchstack.skin.task.ConsentTask;
import org.researchstack.skin.task.SignInTask;
import org.researchstack.skin.task.SignUpTask;

import java.util.HashMap;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityTaskProvider extends TaskProvider  {

    private HashMap<String, Task> map = new HashMap<>();

    public ImpulsivityTaskProvider(Context context)
    {
//        put(TASK_ID_INITIAL, createInitialTask(context));
//        put(TASK_ID_CONSENT, ConsentTask.create(context, TASK_ID_CONSENT));
//        put(TASK_ID_SIGN_IN, new SignInTask(context));
//        put(TASK_ID_SIGN_UP, new SignUpTask(context));
    }

    @Override
    public Task get(String taskId)
    {
        Log.d("SampleTaskProvider", "Getting task "+taskId);
        return map.get(taskId);
    }

    @Override
    public void put(String id, Task task)
    {
        map.put(id, task);
    }

}
