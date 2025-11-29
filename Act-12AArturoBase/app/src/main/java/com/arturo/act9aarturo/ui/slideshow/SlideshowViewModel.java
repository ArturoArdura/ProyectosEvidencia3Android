package com.arturo.act9aarturo.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arturo.act9aarturo.R;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<Integer> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(R.string.text_leaderboard);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
}