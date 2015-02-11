package um.feri.uporabniskivmesniki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import um.feri.uporabniskivmesniki.model.Client;

public class ListViewAdapter extends ListAdapter<Client> {

    public ListViewAdapter(Context context, List<Client> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.view_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView)view.findViewById(R.id.tvFirstName);

            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();

        Client client = getTypedItem(position);

        viewHolder.tvName.setText(client.getFirstName() + " " + client.getLastName());

        return view;
    }

    static class ViewHolder {
        TextView tvName;
    }
}
