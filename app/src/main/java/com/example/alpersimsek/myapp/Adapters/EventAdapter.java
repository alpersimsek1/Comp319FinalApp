package com.example.alpersimsek.myapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alpersimsek.myapp.HowManyDays.Event;
import com.example.alpersimsek.myapp.Notes.Note;
import com.example.alpersimsek.myapp.R;

import java.util.ArrayList;

/**
 * Created by alpersimsek on 25.04.2018.
 */

public class EventAdapter extends ArrayAdapter<Event> implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        int location = (Integer) view.getTag();
        Object item = getItem(location);
        Event event = (Event) item;
    }


    public EventAdapter(ArrayList<Event> events, Context context){
        super(context,0,events);
    }


    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        Event event = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list, parent, false);

        }
        TextView title =  convertView.findViewById(R.id.eventTitle);
        TextView date = convertView.findViewById(R.id.eventDate);

        title.setText(event.getTitle());
        date.setText(event.daysLeft());

        return convertView;

    }
}
