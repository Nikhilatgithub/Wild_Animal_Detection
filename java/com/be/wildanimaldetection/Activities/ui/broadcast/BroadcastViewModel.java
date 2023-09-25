package com.be.wildanimaldetection.Activities.ui.broadcast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BroadcastViewModel extends ViewModel {

    private MutableLiveData<String> list;

    public BroadcastViewModel() {
        list = new MutableLiveData<>();
    }

    public void setData(String index) {
        list.setValue(index);
    }

    public LiveData <String> getData() {
        return list;
    }
}