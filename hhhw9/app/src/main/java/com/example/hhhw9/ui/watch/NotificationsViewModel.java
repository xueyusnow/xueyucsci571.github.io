package com.example.hhhw9.ui.watch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    private ArrayList<String> watchlist;
    private MutableLiveData<String> mText;
    public void setText(ArrayList<String> list){
        this.watchlist=list;
    }
    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        String watch="";
//        for(String i:this.watchlist){
//            watch=watch+i+"\n";
//        }
        mText.setValue(watch);
    }

    public LiveData<String> getText() {
        return mText;
    }
}