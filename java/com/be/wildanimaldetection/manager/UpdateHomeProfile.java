package com.be.wildanimaldetection.manager;

import android.view.View;
import android.widget.TextView;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.R;
import com.google.android.material.navigation.NavigationView;

public class UpdateHomeProfile {
    private TextView email;
    private NavigationView navigationView;

    public UpdateHomeProfile() {
        navigationView= HomeActivity.navigationView;
        View view =navigationView.getHeaderView(0);
        email=view.findViewById(R.id.home_nav_email);

    }


    public void setEmail(String email) {
        this.email.setText(email);;
    }

}
