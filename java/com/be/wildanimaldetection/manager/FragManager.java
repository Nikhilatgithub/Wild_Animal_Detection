package com.be.wildanimaldetection.manager;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



public class FragManager
{
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public FragManager(AppCompatActivity activity) {
        setManager(activity);
        setTransaction(getManager().beginTransaction());
    }

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(AppCompatActivity activity) {
        //activity.getFragmentManager();
        this.manager = activity.getSupportFragmentManager();
    }

    public FragmentTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(FragmentTransaction transaction) {
        this.transaction = transaction;
    }

    public void fragTo(int id,Fragment fragment)
    {
        try {
            getTransaction().replace(id, fragment);
            getTransaction().commit();
        }
        catch (Exception e)
        {

        }

    }

    public void fragToBackTrace(int id,Fragment fragment)
    {
        try {
            getTransaction().replace(id, fragment);
            getTransaction().addToBackStack(null).commit();
        }
        catch (Exception e)
        {

        }

    }
}
