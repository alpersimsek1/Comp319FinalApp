package com.example.alpersimsek.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alpersimsek.myapp.Notes.Note;
import com.example.alpersimsek.myapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by alpersimsek on 20.04.2018.
 */

public class NoteAdapter extends ArrayAdapter<Note> implements View.OnClickListener {

    Context context;

    @Override
    public void onClick(View view) {
        int location = (Integer) view.getTag();
        Object item = getItem(location);
        Note note = (Note) item;
    }


    public NoteAdapter(ArrayList<Note> notes, Context context){
        super(context,0,notes);
    }


    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        Note note = getItem(position);


        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_list, parent, false);

        }
//        TextView title =  convertView.findViewById(R.id.custom_title);
        TextView text =  convertView.findViewById(R.id.custom_title);

        text.setText(note.getText());

        return convertView;

    }
}
