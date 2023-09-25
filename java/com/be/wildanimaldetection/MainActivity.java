package com.be.wildanimaldetection;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.Activities.ui.about.IntroFragment;
import com.be.wildanimaldetection.Activities.ui.login.EmailVerifyFragment;
import com.be.wildanimaldetection.manager.DBManager;
import com.be.wildanimaldetection.manager.FragManager;
import com.be.wildanimaldetection.manager.SignInManager;

public class MainActivity extends AppCompatActivity {
    public static AppCompatActivity activity;
    public static DBManager dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper=new DBManager(this, null, null, 1);
        FragManager fragManager = new FragManager(getActivity());


//        Intent detect = new Intent(getActivity(), DetectionActivity.class);
//        getActivity().startActivity(detect);
//        getActivity().finish();
//        openHome();


        try {
            Thread logint= new Thread(){
                @Override
                public void run() {
                    super.run();

                    SignInManager signedManager = new SignInManager();
                    if(!signedManager.isSigned())
                    {
                        intro();
                        fragManager.fragTo(R.id.main_frag,new EmailVerifyFragment());
                    }
                    else {
                        intro();
                        openHome();
                    }
                }
            };

//
            logint.start();



        }catch (Exception eee){
            System.out.println("Error in login : "+eee.getMessage());
        }
    }


    public void openHome()
    {
        try {
            Intent home = new Intent(getActivity(), HomeActivity.class);
            getActivity().startActivity(home);
            getActivity().finish();
        }catch (Exception e)
        {
            System.out.println("Error in open home : "+e.getMessage());
        }

    }

    public  AppCompatActivity getActivity()
    {
        return MainActivity.activity=this;
    }

    public void intro() {

        FragManager fragManager1 = new FragManager(getActivity());
        fragManager1.fragTo(R.id.main_frag, new IntroFragment());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}