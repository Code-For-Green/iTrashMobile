package com.github.codeforgreen.itrash.ui.garbage_cans;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GarbageCansViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public GarbageCansViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nearby garbage cans fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
