package com.github.codeforgreen.itrash.ui.recycling;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecyclingViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RecyclingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recycling fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
