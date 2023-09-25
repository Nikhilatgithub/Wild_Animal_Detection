package com.be.wildanimaldetection.Activities.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmailVerifyViewModel extends ViewModel {
    private MutableLiveData<String> email,pass,msgg;

    public EmailVerifyViewModel() {
        email = new MutableLiveData<>();
        pass  = new MutableLiveData<>();
        msgg  = new MutableLiveData<>();
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }
    public LiveData<String> getMsgg() {
        return msgg;
    }

    public void setMsgg(String msgg) {
        this.msgg.setValue(msgg);
    }
    public LiveData<String> getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.setValue(pass);
    }
}