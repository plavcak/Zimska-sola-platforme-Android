package um.feri.uporabniskivmesniki;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import um.feri.uporabniskivmesniki.db.Database;
import um.feri.uporabniskivmesniki.model.Client;

public class SimpleListActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<Client>> {

    private ListView listView;
    private ListViewAdapter adapter;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        database = new Database(getApplicationContext());

        listView = (ListView) findViewById(android.R.id.list);

        List<Client> clients = new ArrayList<>();
        clients.add(new Client("Janez", "Novak"));
        clients.add(new Client("Janez", "Novak"));
        clients.add(new Client("Janez", "Novak"));

        adapter = new ListViewAdapter(getApplicationContext(), clients);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(adapter);


        getSupportLoaderManager().initLoader(2, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    @Override
    public void onLoaderReset(Loader<List<Client>> loader) {

    }

    @Override
    public void onLoadFinished(Loader<List<Client>> loader, List<Client> data) {
        adapter.setList(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<Client>> onCreateLoader(int id, Bundle args) {
        return new ClientLoader(getApplicationContext(), database);
    }
}
