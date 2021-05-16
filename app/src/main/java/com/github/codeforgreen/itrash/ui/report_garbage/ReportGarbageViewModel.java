package com.github.codeforgreen.itrash.ui.report_garbage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportGarbageViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public
    ReportGarbageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Report Garbage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
