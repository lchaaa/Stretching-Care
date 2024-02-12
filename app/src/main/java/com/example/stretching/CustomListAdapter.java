package com.example.stretching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<DataItem> {
    private final Context context;
    private final DataItem[] dataList;
    public CustomListAdapter(Context context, DataItem[] dataList) {
        super(context, 0, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);
        DataItem item = dataList[position];
        imageView.setImageResource(item.getImageResId());
        textView.setText(item.getDescription());

        return convertView;
    }
}