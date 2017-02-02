package edu.cornell.tech.foundry.impulsivityohmage;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import org.researchstack.backbone.storage.database.sqlite.SqlCipherDatabaseHelper;
import org.researchstack.backbone.storage.database.sqlite.UpdatablePassphraseProvider;

import java.sql.SQLException;

import co.touchlab.squeaky.db.sqlcipher.SQLiteDatabaseImpl;
import co.touchlab.squeaky.table.TableUtils;

/**
 * Created by jameskizer on 2/1/17.
 */
public class ImpulsivityEncryptedDatabase extends SqlCipherDatabaseHelper {

    public ImpulsivityEncryptedDatabase(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version, UpdatablePassphraseProvider passphraseProvider)
    {
        super(context, name, cursorFactory, version, passphraseProvider);
    }

//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase)
//    {
//        super.onCreate(sqLiteDatabase);
//        try
//        {
//            TableUtils.createTables(new SQLiteDatabaseImpl(sqLiteDatabase), UploadRequest.class);
//        }
//        catch(SQLException e)
//        {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        super.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        // handle future db upgrades here
    }
}
