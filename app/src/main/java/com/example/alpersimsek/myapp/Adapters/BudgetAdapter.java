package com.example.alpersimsek.myapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alpersimsek.myapp.Budget.Budget;
import com.example.alpersimsek.myapp.HowManyDays.Event;
import com.example.alpersimsek.myapp.R;

import java.util.ArrayList;

/**
 * Created by alpersimsek on 27.04.2018.
 */

public class BudgetAdapter extends ArrayAdapter<Budget> {


    public BudgetAdapter(ArrayList<Budget> budgets, Context context){
        super(context,0,budgets);
    }
    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        Budget budget = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.budget_list, parent, false);

        }
        TextView title =  convertView.findViewById(R.id.budgetTitle);
        TextView amount = convertView.findViewById(R.id.budgetAmount);

        title.setText(budget.getTitle());
        amount.setText(String.valueOf(budget.getAmount()));

        return convertView;

    }
}
