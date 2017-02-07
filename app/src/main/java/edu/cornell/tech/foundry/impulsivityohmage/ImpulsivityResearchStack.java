package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import org.researchstack.backbone.storage.database.AppDatabase;
import org.researchstack.backbone.storage.database.sqlite.SqlCipherDatabaseHelper;
import org.researchstack.backbone.storage.database.sqlite.UpdatablePassphraseProvider;
import org.researchstack.backbone.storage.file.EncryptionProvider;
import org.researchstack.backbone.storage.file.FileAccess;
import org.researchstack.backbone.storage.file.PinCodeConfig;
import org.researchstack.backbone.storage.file.aes.AesProvider;
import org.researchstack.skin.AppPrefs;
import org.researchstack.skin.DataProvider;
import org.researchstack.skin.PermissionRequestManager;
import org.researchstack.skin.ResearchStack;
import org.researchstack.skin.ResourceManager;
import org.researchstack.skin.TaskProvider;
import org.researchstack.skin.UiManager;
import org.researchstack.skin.notification.NotificationConfig;
import org.researchstack.skin.notification.SimpleNotificationConfig;

import edu.cornell.tech.foundry.ohmageomhsdk.OhmageOMHManager;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityResearchStack extends ResearchStack  {

    public static void init(Context context, ResearchStack concreteResearchStack)
    {
        OhmageOMHManager.config(
                "https://ohmage.unicornucopia.org/dsu",
                "edu.cornell.tech.foundry.ios.ResearchSuiteSDKExample",
                "nzVlBVmrSHIrxr0SW9XdWp8yjtVov2NnVu7ezA7F"
        );

        ResearchStack.init(context, concreteResearchStack);

    }

    @Override
    protected AppDatabase createAppDatabaseImplementation(Context context)
    {
        SQLiteDatabase.loadLibs(context);
        return new ImpulsivityEncryptedDatabase(context,
                SqlCipherDatabaseHelper.DEFAULT_NAME,
                null,
                SqlCipherDatabaseHelper.DEFAULT_VERSION,
                new UpdatablePassphraseProvider());
    }

    @Override
    protected FileAccess createFileAccessImplementation(Context context)
    {
        String pathName = context.getString(R.string.rstb_state_helper_path);
        return new ImpulsivityAppStateManager(pathName);
    }

    @Override
    protected PinCodeConfig getPinCodeConfig(Context context)
    {
        long autoLockTime = AppPrefs.getInstance(context).getAutoLockTime();
        return new PinCodeConfig(autoLockTime);
    }

    @Override
    protected EncryptionProvider getEncryptionProvider(Context context)
    {
        return new AesProvider();
    }

    @Override
    protected ResourceManager createResourceManagerImplementation(Context context)
    {
        return new ImpulsivityResourceManager();
    }

    @Override
    protected UiManager createUiManagerImplementation(Context context)
    {
        return new ImpulsivityUIManager();
    }

    @Override
    protected DataProvider createDataProviderImplementation(Context context)
    {
        return new ImpulsivityDataProvider();
    }

    @Override
    protected TaskProvider createTaskProviderImplementation(Context context)
    {

        return new ImpulsivityTaskProvider(context);
    }

    @Override
    protected NotificationConfig createNotificationConfigImplementation(Context context)
    {
        return new SimpleNotificationConfig();
    }

    @Override
    protected PermissionRequestManager createPermissionRequestManagerImplementation(Context context)
    {
        return new ImpulsivityPermissionResultManager();
    }
}
