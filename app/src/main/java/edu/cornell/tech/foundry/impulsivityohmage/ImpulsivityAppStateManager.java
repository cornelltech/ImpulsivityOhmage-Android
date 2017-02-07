package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.storage.file.SimpleFileAccess;

import edu.cornell.tech.foundry.ohmageomhsdk.OhmageOMHSDKCredentialStore;
import edu.cornell.tech.foundry.researchsuitetaskbuilder.RSTBStateHelper;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityAppStateManager extends SimpleFileAccess implements RSTBStateHelper, OhmageOMHSDKCredentialStore {

    private String pathName;

    public static ImpulsivityAppStateManager getInstance()
    {
        return (ImpulsivityAppStateManager) StorageAccess.getInstance().getFileAccess();
    }

    public ImpulsivityAppStateManager(String pathName)
    {
        this.pathName = pathName;
    }

    private String pathForRSTBKey(String key) {
        StringBuilder pathBuilder = new StringBuilder(this.pathName);
        pathBuilder.append("/rstb/");
        pathBuilder.append(key);

        return pathBuilder.toString();
    }

    private String pathForOSDKeKey(String key) {
        StringBuilder pathBuilder = new StringBuilder(this.pathName);
        pathBuilder.append("/osdk/");
        pathBuilder.append(key);

        return pathBuilder.toString();
    }

    @Override
    public byte[] valueInState(Context context, String key) {

        if (this.dataExists(context, this.pathForRSTBKey(key))) {
            return this.readData(context, this.pathForRSTBKey(key));
        }
        else {
            return null;
        }
    }


    @Override
    public void setValueInState(Context context, String key, byte[] value) {
        this.writeData(
                context, this.pathForRSTBKey(key),
                value
        );
    }

    @Override
    public byte[] get(Context context, String key) {
        if (this.dataExists(context, this.pathForOSDKeKey(key))) {
            return this.readData(context, this.pathForOSDKeKey(key));
        }
        else {
            return null;
        }
    }

    @Override
    public void set(Context context, String key, byte[] value) {
        this.writeData(
                context, this.pathForOSDKeKey(key),
                value
        );
    }

    @Override
    public void remove(Context context, String key) {
        if (this.dataExists(context, this.pathForOSDKeKey(key))) {
            this.clearData(context, this.pathForOSDKeKey(key));
        }
    }


}
