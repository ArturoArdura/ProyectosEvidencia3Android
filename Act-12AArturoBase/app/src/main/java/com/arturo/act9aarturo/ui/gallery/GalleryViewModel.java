package com.arturo.act9aarturo.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arturo.act9aarturo.R;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<Integer> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(R.string.text_rewards);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
}