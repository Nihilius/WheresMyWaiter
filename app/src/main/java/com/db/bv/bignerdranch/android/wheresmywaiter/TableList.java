package com.db.bv.bignerdranch.android.wheresmywaiter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

       /* TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDownloads = (TextView) listViewItem.findViewById(R.id.textViewDownloads);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Artist artist = artists.get(position);
        textViewName.setText(artist.getArtistName());
        textViewDownloads.setText(Long.toString(artist.getArtistDownloads()));
        textViewGenre.setText(artist.getArtistGenre());*/

       //TODO: Hookup layout elements (Image should correspond to the IsPinged and HasMessage booleans)

        return listViewItem;
    }
}
