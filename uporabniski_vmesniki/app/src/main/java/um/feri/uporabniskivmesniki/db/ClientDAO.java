package um.feri.uporabniskivmesniki.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import um.feri.uporabniskivmesniki.model.Client;

public class ClientDAO {

	public String getTableName() {
        return "client";
    }

	protected ContentValues getContentValues(Client object) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", object.getId());
        contentValues.put("firstName", object.getFirstName());
        contentValues.put("lastName", object.getLastName());

        return contentValues;
    }

	protected Client getObject(Cursor cursor) {
        Client client = new Client();
        client.setId(cursor.getLong(cursor.getColumnIndex("id")));
        client.setFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
        client.setLastName(cursor.getString(cursor.getColumnIndex("lastName")));
        return client;
    }
	
	public void emptyTable(SQLiteDatabase db) {
		db.execSQL("DELETE FROM "+getTableName());
	}

	protected List<String> getUpgradeStatements(int oldVersion, int newVersion) {
		List<String> ret=new ArrayList<String>();
		ret.add(getCreateStatement());
		return ret;
	}
	
	public String getCreateStatement() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName TEXT, " +
                "lastName TEXT );";
	}

	public boolean delete(long id,SQLiteDatabase db) {
	    int result = db.delete(getTableName(), "id=?", getWhereArgs(id));
	    return result == 1;
	}
	
	public void deleteAll(SQLiteDatabase db) {
	    db.delete(getTableName(), null, null);
	}

    public Client insert(Client object, SQLiteDatabase db) throws SQLException {
        long ret = db.insert(getTableName(), null, getContentValues(object));
        if(ret == -1) return null;
        object.setId(ret);
        return object;
    }

    public boolean update(Client object, SQLiteDatabase db) {
        int result = db.update(getTableName(), getContentValues(object), "id=?", getWhereArgs(object.getId()));
        return result == 1;
    }

    public long count(SQLiteDatabase db) {
    	Cursor cursor = db.query(getTableName(), new String[] {"id"}, null, null, null, null, null);
    	int count = cursor.getCount();
    	cursor.close();
    	return count;
    }

    public Client get(long id,SQLiteDatabase db) throws SQLException {
        Client object = null;
        Cursor cursor = db.query(getTableName(), null, "id=?", getWhereArgs(id), null, null, null);
        if(cursor.moveToNext()) {
        	object = getObject(cursor);
        }
    	cursor.close();
        return object;
    }

    public List<Client> getAll(String orderBy, SQLiteDatabase db) throws SQLException {
        Cursor cursor = db.query(getTableName(), null, null, null, null, null, orderBy);
        List<Client> list = new ArrayList<Client>();

        while(cursor.moveToNext()) {
        	list.add(getObject(cursor));
        }
        cursor.close();

        return list;
    }

    protected String[] getWhereArgs(long id) {
    	return new String[] {String.valueOf(id)};
    }

    protected String[] getWhereArgs(String... ids) {
    	String[] ret=new String[ids.length];
    	for (int i = 0; i < ids.length; i++) {
			ret[i]=ids[i];
		}
    	return ret;
    }
}
