package gojek.tech.museumsearch.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import gojek.tech.museumsearch.R;

public class HomeListAdapter extends ArrayAdapter<String> {
    private final String[] titles;
    private final String[] descs;
    private final Context context;

    public HomeListAdapter(Context context, String[] strings, String[] desc) {
        super(context, R.layout.list_layout, strings);
        this.context = context;
        this.titles = strings;
        this.descs = desc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_layout, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.text_title);
        TextView desc = (TextView) rowView.findViewById(R.id.text_desc);
        title.setText(titles[position]);
        desc.setText(descs[position]);

        return rowView;
    }
}
