package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Owner on 4/21/2018.
 */

public class RestarauntAdapter extends BaseAdapter {
    Context context;
    List<Restaraunt> restarauntList;
    LayoutInflater inflater;

    public RestarauntAdapter(Context applicationContext, List<Restaraunt> restarauntList){
        this.context = applicationContext;
        this.restarauntList = restarauntList;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return restarauntList.size();
    }

    @Override
    public Object getItem(int i) {return null;}

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Restaraunt restaraunt = restarauntList.get(i);
        view = inflater.inflate(R.layout.restaraunt_listview, null);
        TextView name = (TextView) view.findViewById(R.id.restaraunt_name);
        name.setText(restaraunt.getName());
        TextView info = (TextView) view.findViewById(R.id.restaraunt_info);
        info.setText(restaraunt.getAddress() + ", " + restaraunt.getState()
                        + ", " + restaraunt.getZipCode());
        return view;
    }
}
