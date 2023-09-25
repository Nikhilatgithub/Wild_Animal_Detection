package com.be.wildanimaldetection.adaptors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> list;

    public PageViewModel() {
        list = new MutableLiveData<>();
    }

    public void setData(String index) {
        list.setValue(index);
    }

    public LiveData <String> getData() {
        return list;
    }
}