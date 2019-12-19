package com.example.notebook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adabter_list extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> recordList;
    //private Activity activity;

    public adabter_list(Activity activity, ArrayList<String> records) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recordList = records;
        // this.activity = activity;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_detail_row, null); // create layout from

        TextView ay = vi.findViewById(R.id.textView4);




        final String record = recordList.get(position);

        ay.setText(record+"");


        return vi;
    }

}