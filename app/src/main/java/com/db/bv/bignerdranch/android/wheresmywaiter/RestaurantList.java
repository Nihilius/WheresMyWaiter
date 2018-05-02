package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bwest on 4/29/2018.
 */



public class RestaurantList extends ArrayAdapter<Restaraunt> {
    private Activity context;
    List<Restaraunt> restaraunts;


    public RestaurantList(Activity context, List<Restaraunt> restarauntList) {
        super(context, R.layout.layout_restaraunt_list, restarauntList);

        this.context = context;
        this.restaraunts = restarauntList;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_restaraunt_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddress);

        TextView textViewLocation = (TextView) listViewItem.findViewById(R.id.textViewLocation);

        Restaraunt restaraunt = restaraunts.get(position);
        textViewName.setText(restaraunt.getName());
        textViewAddress.setText(restaraunt.getAddress() + ", " + restaraunt.getZip());
        textViewLocation.setText(restaraunt.getCity()+ ", " + restaraunt.getState());

        return listViewItem;
    }
}
