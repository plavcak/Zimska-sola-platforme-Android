package si.um.feri.hrambapodatkov.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "default.db";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder create = new StringBuilder();

        create.append( new ClientDAO().getCreateStatement());

        db.execSQL(create.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < newVersion) {
            StringBuilder create = new StringBuilder();

            create.append( new ClientDAO().getUpgradeStatements(oldVersion, newVersion));

            db.execSQL(create.toString());
        }
    }
}
