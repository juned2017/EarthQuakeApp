package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by junedahmed on 2/18/18.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{
    private static final String LOCATION_SEPARATOR="of";

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
        String primaryLocation;
        String locationOffset;
        String originalLocation=earthquake.getLocation();
        double magnitudeDouble=earthquake.getMagnitude();


        DecimalFormat formatter=new DecimalFormat("0.0");
        String outPut=formatter.format(magnitudeDouble);

        TextView magnitude=(TextView)listItemView.findViewById(R.id.magnitude);

        //fetching the background from the textview , which is a GradientDrawble

        GradientDrawable magnitudeCircle=(GradientDrawable)magnitude.getBackground();

        int magnitudeColor=getMagnitudeColor(earthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

        magnitude.setText(outPut);


        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView=(TextView)listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.loacation_offset);
        locationOffsetView.setText(locationOffset);


        Date dateObject=new Date(earthquake.getTime());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        String formattedDate=formatDate(dateObject);

        TextView time=(TextView)listItemView.findViewById(R.id.time);
        String timeFormatted=formatTime(dateObject);



        return listItemView;
    }

    //helper method
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor=(int)Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId=R.color.magnitude1;
                break;

            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
//        Remember that color resource IDs just point to the resource we defined, but not the value of the color. For example, R.layout.earthquake_list_item is a reference to tell us where the layout is located. It’s just a number, not the full XML layout.
//
//        You can call ContextCompat getColor() to convert the color resource ID into an actual integer color value, and return the result as the return value of the getMagnitudeColor() helper method.
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    //       * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
//            */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
