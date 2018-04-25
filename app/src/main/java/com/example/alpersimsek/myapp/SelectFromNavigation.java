package com.example.alpersimsek.myapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.alpersimsek.myapp.Login.User;

/**
 * Created by alpersimsek on 25.04.2018.
 */

public class SelectFromNavigation {
    public static final String USERID= "com.example.alpersimsek.myapp.userid";

    BottomNavigationView nav;
    Context context;
    User user;
    public SelectFromNavigation(BottomNavigationView nav, Context context, User user){
        this.nav = nav;
        this.context = context;
        this.user = user;
    }

    public void select(){
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_budget: toEverywhere(context, BudgetActivity.class, user); break;
                    case R.id.action_event: toEverywhere(context, HowManyDaysActivity.class, user); break;
                    case R.id.action_run: toEverywhere(context,RunActivity.class, user); break;
                }
                return true;
            }
        });
    }


    public void toEverywhere(Context context, Class cla, User dummyUser) {
        Intent intent = new Intent(context, cla);
        intent.putExtra(dummyUser.getUid(),USERID);
        context.startActivity(intent);
    }



}
