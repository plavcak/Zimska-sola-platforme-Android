package um.feri.uporabniskivmesniki;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter {
	protected List<T> list;
	protected final Context context;
	
	public ListAdapter(Context context, List<T> list) {
		this.list = list;
		this.context = context;
	}
	
	public T getTypedItem(int position) {
		if(position >= getList().size()) return null;
		return list.get(position);
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		if(position >= getList().size()) return null;
		return list.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		if(position > getList().size()) return -1;
		return position;
	}

	public Context getContext() {
		return context;
	}

	public List<T> getList() {
		if(list == null) list = new ArrayList<T>();
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}

