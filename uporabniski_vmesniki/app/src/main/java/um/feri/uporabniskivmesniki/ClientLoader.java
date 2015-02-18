package um.feri.uporabniskivmesniki;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import um.feri.uporabniskivmesniki.db.ClientDAO;
import um.feri.uporabniskivmesniki.db.Database;
import um.feri.uporabniskivmesniki.model.Client;

public class ClientLoader extends AsyncTaskLoader<List<Client>> {

    List<Client> clients;
    ClientDAO dao;
    Database database;

    public ClientLoader(Context context, Database database) {
        super(context);
        dao = new ClientDAO();
        this.database = database;
    }

    @Override
    public List<Client> loadInBackground() {

        if (clients == null) {
            clients = new ArrayList<Client>();
        }

        SQLiteDatabase db = database.getWritableDatabase();

        List<Client> ret = dao.getAll(null, db);

        db.close();

        return ret;
    }

    @Override
    public void deliverResult(List<Client> apps) {
        if (isReset()) {
            if (apps != null) {

            }
        }
        clients = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }
    }

    @Override protected void onStartLoading() {
        if (clients != null) {
            deliverResult(clients);
        }

        if (takeContentChanged() || clients == null) {
            forceLoad();
        }
    }

    @Override protected void onStopLoading() {
        cancelLoad();
    }

    @Override public void onCanceled(List<Client> apps) {
        super.onCanceled(apps);
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (clients != null) {
            clients = null;
        }
    }
}
