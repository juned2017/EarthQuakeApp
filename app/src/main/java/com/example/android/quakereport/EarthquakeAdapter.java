package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by junedahmed on 2/18/18.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

    public EarthquakeAdapter(@NonNull Context context, ArrayList<Earthquake>earthquakes) {
        super(context, 0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item,parent,false);
        }
        Earthquake earthquake=getItem(position);

        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);
        magnitude.setText(earthquake.getMagnitude());

        TextView location=(TextView)listItemView.findViewById(R.id.loacation);
        location.setText(earthquake.getLocation());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(earthquake.getDate());




        return listItemView;
    }
}
