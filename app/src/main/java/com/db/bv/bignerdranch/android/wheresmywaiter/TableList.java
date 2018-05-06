package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Owner on 5/1/2018.
 */

public class TableList extends ArrayAdapter<Table> {

    private Activity context;
    List<Table> tables;

    public TableList(Activity context, List<Table> tables){
        super(context, R.layout.layout_table_list);
        this.context = context;
        this.tables = tables;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_table_list, null, true);

        TextView textViewTableNumber = (TextView) listViewItem.findViewById(R.id.textViewName);
        ImageView imageViewIsPinged = (ImageView) listViewItem.findViewById(R.id.waiterIsPingedImage);
        ImageView imageViewNotPinged = (ImageView) listViewItem.findViewById(R.id.waiterIsNotPingedImage);
        ImageView imageViewHasMessage = (ImageView) listViewItem.findViewById(R.id.customerRequestImage);

        Table table = tables.get(position);
        textViewTableNumber.setText("Table " + table.getTableNumber());
        if (table.getIsPinged() == true){
            imageViewIsPinged.setVisibility(View.VISIBLE);
            imageViewNotPinged.setVisibility(View.INVISIBLE);
        } else {
            imageViewIsPinged.setVisibility(View.INVISIBLE);
            imageViewNotPinged.setVisibility(View.VISIBLE);
        }
        if (table.getHasMessage()==true){
            imageViewHasMessage.setEnabled(true);
        } else {
            imageViewHasMessage.setEnabled(false);
        }

        return listViewItem;
    }
}
