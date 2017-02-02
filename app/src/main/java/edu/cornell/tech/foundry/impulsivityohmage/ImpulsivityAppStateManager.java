package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.storage.file.SimpleFileAccess;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBStateHelper;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityAppStateManager extends SimpleFileAccess implements RSTBStateHelper {

    private String pathName;

    public static ImpulsivityAppStateManager getInstance()
    {
        return (ImpulsivityAppStateManager) StorageAccess.getInstance().getFileAccess();
    }

    public ImpulsivityAppStateManager(String pathName)
    {
        this.pathName = pathName;
    }

    private String pathForKey(String key) {
        StringBuilder pathBuilder = new StringBuilder(this.pathName);
        pathBuilder.append('/');
        pathBuilder.append(key);

        return pathBuilder.toString();
    }

    @Override
    public byte[] valueInState(Context context, String key) {

        if (this.dataExists(context, this.pathForKey(key))) {
            return this.readData(context, this.pathForKey(key));
        }
        else {
            return null;
        }
    }


    @Override
    public void setValueInState(Context context, String key, byte[] value) {
        this.writeData(
                context, this.pathForKey(key),
                value
        );
    }

}
